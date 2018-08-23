package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.gateways.ab.ABGateway;
import ru.alxndrzuev.iso20022.gateways.ab.ResponseException;
import ru.alxndrzuev.iso20022.statement.builder.StatementRequestMessageBuilder;
import ru.alxndrzuev.iso20022.statement.model.StatementRequest;
import ru.alxndrzuev.iso20022.statement.model.StatementRequestMessage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Push
@Route("statements")
@Slf4j
public class StatementsPage extends BasePage {

    @Autowired
    private StatementRequestMessageBuilder statementRequestMessageBuilder;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ABGateway gateway;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long STATEMENT_UPDATE_RATE = 10000l;
    private static final int STATEMENT_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;
    private TextField requestIdTextField;
    private TextField accountTextField;
    private TextField organizationNameTextField;
    private DatePicker dateFromDatePicker;
    private DatePicker dateToDatePicker;

    private Button generateRequest;
    private Button signRequest;
    private Button sendRequest;

    private TextArea statementTextArea;

    public StatementsPage() {
        FormLayout formLayout = new FormLayout();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setWidth("40");
        messageIdTextField.setValue(UUID.randomUUID().toString().substring(1));
        requestIdTextField = new TextField();
        requestIdTextField.setWidth("40");
        requestIdTextField.setValue("1");
        accountTextField = new TextField();
        accountTextField.setWidth("40");
        organizationNameTextField = new TextField();
        organizationNameTextField.setWidth("40");
        dateFromDatePicker = new DatePicker();
        dateToDatePicker = new DatePicker();

        formLayout.addFormItem(messageIdTextField, "Id сообщения");
        formLayout.addFormItem(requestIdTextField, "Id запроса");
        formLayout.addFormItem(accountTextField, "Номер счета");
        formLayout.addFormItem(organizationNameTextField, "Название организации");
        formLayout.addFormItem(dateFromDatePicker, "С");
        formLayout.addFormItem(dateToDatePicker, "По");

        generateRequest = new Button("Сгенерировать запрос");
        signRequest = new Button("Подписать запрос");
        sendRequest = new Button("Отправить запрос");
        HorizontalLayout buttonsLayout = new HorizontalLayout(generateRequest, signRequest, sendRequest);
        fieldLayout.add(buttonsLayout);

        Tab statementTab = new Tab("Выписка");
        statementTextArea = new TextArea();
        statementTextArea.setReadOnly(true);
        statementTextArea.setWidth("100%");
        statementTextArea.setVisible(false);
        statementTextArea.setValue("");
        tabs.add(statementTab);
        pages.add(statementTextArea);
        tabsToPages.put(statementTab, statementTextArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        generateRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            StatementRequestMessage requestMessage = getRequestMessage();

            String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
            requestTextArea.setValue(requestString);
        });

        signRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            StatementRequestMessage requestMessage = getRequestMessage();

            String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
            String signedRequestString = cryptoService.signRequest(requestString);
            requestTextArea.setValue(xmlFormatter.format(signedRequestString));
        });

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            StatementRequestMessage requestMessage = getRequestMessage();

            String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
            requestString = cryptoService.signRequest(requestString);
            requestTextArea.setValue(xmlFormatter.format(requestString));
            try {
                ResponseEntity responseEntity = gateway.getStatement(requestString);
                responseTextArea.setValue(generateResultt(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody() != null ? responseEntity.getBody().toString() : null));
                new Thread(() -> updateStatementResult(requestMessage.getMessageId(), getUI().get())).start();
            } catch (ResponseException responseException) {
                responseTextArea.setValue(generateResult(responseException.getResponse().status(), responseException.getResponse().headers().entrySet(), responseException.getResponseBody()));
            } catch (Exception e) {
                log.error("Exception:", e);
            }
        });
    }

    @SneakyThrows
    private void updateStatementResult(String messageId, UI ui) {
        for (int i = 0; i < STATEMENT_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity responseEntity = gateway.getStatementResult(messageId);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        statementTextArea.setValue(generateResultt(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody().toString())));
                    });
                    break;
                } else {
                    ui.access(() -> {
                        statementTextArea.setValue(generateResultt(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody().toString()));
                    });
                }
            } catch (ResponseException responseException) {
                ui.access(() -> {
                    statementTextArea.setValue(generateResult(responseException.getResponse().status(), responseException.getResponse().headers().entrySet(), responseException.getResponseBody()));
                });
                break;
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            Thread.sleep(STATEMENT_UPDATE_RATE);
        }

    }

    private StatementRequestMessage getRequestMessage() {
        StatementRequestMessage requestMessage = new StatementRequestMessage();
        requestMessage.setMessageId(messageIdTextField.getValue());

        StatementRequest request = new StatementRequest();
        request.setRequestId(requestIdTextField.getValue());
        request.setAccount(accountTextField.getValue());
        request.setOrganizationName(organizationNameTextField.getValue());
        request.setDateFrom(Date.from(dateFromDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        request.setDateTo(Date.from(dateToDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        requestMessage.getRequests().add(request);
        return requestMessage;
    }
}