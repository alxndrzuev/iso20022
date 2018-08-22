package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private TextField messageIdTextField;
    private TextField requestIdTextField;
    private TextField accountTextField;
    private TextField organizationNameTextField;
    private DatePicker dateFromDatePicker;
    private DatePicker dateToDatePicker;

    private Button generateRequest;
    private Button signRequest;
    private Button sendRequest;

    private Map<Tab, Component> tabsToPages;
    private Set<Component> pagesShown;
    private Tabs tabs;
    private TextArea requestTextArea;
    private TextArea responseTextArea;
    private TextArea statementTextArea;

    public StatementsPage() {
        FormLayout formLayout = new FormLayout();
        main.add(formLayout);

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
        main.add(buttonsLayout);

        Tab requestTab = new Tab("Запрос");
        requestTextArea = new TextArea();
        requestTextArea.setReadOnly(true);
        requestTextArea.setWidth("100%");
        requestTextArea.setValue("");
        Tab responseTab = new Tab("Ответ");
        responseTextArea = new TextArea();
        responseTextArea.setReadOnly(true);
        responseTextArea.setWidth("100%");
        responseTextArea.setVisible(false);
        responseTextArea.setValue("");
        Tab statementTab = new Tab("Выписка");
        statementTextArea = new TextArea();
        statementTextArea.setReadOnly(true);
        statementTextArea.setWidth("100%");
        statementTextArea.setVisible(false);
        statementTextArea.setValue("");
        tabs = new Tabs(requestTab, responseTab, statementTab);
        Div pages = new Div(requestTextArea, responseTextArea, statementTextArea);
        pages.setWidth("100%");
        tabsToPages = new HashMap<>();
        tabsToPages.put(requestTab, requestTextArea);
        tabsToPages.put(responseTab, responseTextArea);
        tabsToPages.put(statementTab, statementTextArea);
        pagesShown = Stream.of(requestTextArea)
                .collect(Collectors.toSet());

        main.add(tabs);
        main.add(pages);

        addListeners();
    }

    private void addListeners() {
        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        generateRequest.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                StatementRequestMessage requestMessage = getRequestMessage();

                String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
                requestTextArea.setValue(requestString);
            }
        });

        signRequest.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                StatementRequestMessage requestMessage = getRequestMessage();

                String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
                requestTextArea.setValue(cryptoService.signRequest(requestString));
            }
        });

        sendRequest.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            @SneakyThrows
            public void onComponentEvent(ClickEvent<Button> event) {
                StatementRequestMessage requestMessage = getRequestMessage();

                String requestString = statementRequestMessageBuilder.buildRequest(requestMessage);
                requestString = cryptoService.signRequest(requestString);
                requestTextArea.setValue(requestString);
                try {
                    ResponseEntity responseEntity = gateway.getStatement(requestString);
                    StringBuilder sb = new StringBuilder();
                    sb.append(responseEntity.getStatusCode().value()).append(" ").append(responseEntity.getStatusCode().getReasonPhrase());
                    sb.append("\n\n");
                    responseEntity.getHeaders().entrySet().stream().forEach(entry -> {
                        sb.append(entry.getKey());
                        sb.append(" : ");
                        sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
                        sb.append("\n");
                    });
                    sb.append("\n");
                    if (responseEntity.getBody() != null) {
                        sb.append(responseEntity.getBody().toString());
                    }
                    responseTextArea.setValue(sb.toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            updateStatementResult(requestMessage.getMessageId(), getUI().get());
                        }
                    }).start();
                } catch (ResponseException responseException) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(responseException.getResponse().status()).append(" ").append(HttpStatus.valueOf(responseException.getResponse().status()).getReasonPhrase());
                    sb.append("\n\n");
                    responseException.getResponse().headers().entrySet().stream().forEach(entry -> {
                        sb.append(entry.getKey());
                        sb.append(" : ");
                        sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
                        sb.append("\n");
                    });
                    sb.append("\n");
                    if (responseException.getResponseBody() != null) {
                        sb.append(responseException.getResponseBody());
                    }
                    responseTextArea.setValue(sb.toString());
                } catch (Exception e) {
                    log.error("Exception:", e);
                }
            }
        });
    }

    @SneakyThrows
    public void updateStatementResult(String messageId, UI ui) {
        for (int i = 0; i < 10; i++) {
            try {
                ResponseEntity responseEntity = gateway.getStatementResult(messageId);
                StringBuilder sb = new StringBuilder();
                sb.append(responseEntity.getStatusCode().value()).append(" ").append(responseEntity.getStatusCode().getReasonPhrase());
                sb.append("\n\n");
                responseEntity.getHeaders().entrySet().stream().forEach(entry -> {
                    sb.append(entry.getKey());
                    sb.append(" : ");
                    sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
                    sb.append("\n");
                });
                sb.append("\n");
                if (responseEntity.getBody() != null) {
                    sb.append(responseEntity.getBody().toString());
                }
                ui.access(() -> {
                    statementTextArea.setValue(sb.toString());
                });
            } catch (ResponseException responseException) {
                StringBuilder sb = new StringBuilder();
                sb.append(responseException.getResponse().status()).append(" ").append(HttpStatus.valueOf(responseException.getResponse().status()).getReasonPhrase());
                sb.append("\n\n");
                responseException.getResponse().headers().entrySet().stream().forEach(entry -> {
                    sb.append(entry.getKey());
                    sb.append(" : ");
                    sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
                    sb.append("\n");
                });
                sb.append("\n");
                if (responseException.getResponseBody() != null) {
                    sb.append(responseException.getResponseBody());
                }
                ui.access(() -> {
                    statementTextArea.setValue(sb.toString());
                });
                break;
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            Thread.sleep(10000l);
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