package ru.alxndrzuev.iso20022.ui.statements;

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
import ru.alxndrzuev.iso20022.documents.statements.StatementsService;
import ru.alxndrzuev.iso20022.documents.statements.model.StatementRequest;
import ru.alxndrzuev.iso20022.documents.statements.model.StatementRequestMessage;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.DateUtils;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

import java.util.UUID;

@Push
@Route("statements/new")
@Slf4j
public class NewStatementPage extends BasePage {
    @Autowired
    private StatementsService statementsService;

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

    public NewStatementPage() {
        FormLayout formLayout = new FormLayout();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setValue(UUID.randomUUID().toString().substring(10));
        messageIdTextField.setSizeFull();
        requestIdTextField = new TextField();
        requestIdTextField.setValue("1");
        requestIdTextField.setSizeFull();
        accountTextField = new TextField();
        accountTextField.setSizeFull();
        organizationNameTextField = new TextField();
        organizationNameTextField.setSizeFull();
        dateFromDatePicker = new DatePicker();
        dateFromDatePicker.setSizeFull();
        dateToDatePicker = new DatePicker();
        dateToDatePicker.setSizeFull();

        formLayout.addFormItem(messageIdTextField, "Message id");
        formLayout.addFormItem(requestIdTextField, "Request id");
        formLayout.addFormItem(accountTextField, "Account");
        formLayout.addFormItem(organizationNameTextField, "Organization name");
        formLayout.addFormItem(dateFromDatePicker, "From");
        formLayout.addFormItem(dateToDatePicker, "To");

        generateRequest = new Button("Generate request");
        signRequest = new Button("Sign request");
        sendRequest = new Button("Send request");
        HorizontalLayout buttonsLayout = new HorizontalLayout(generateRequest, signRequest, sendRequest);
        fieldLayout.add(buttonsLayout);

        Tab statementTab = new Tab("Statement");
        statementTextArea = new TextArea();
        statementTextArea.setReadOnly(true);
        statementTextArea.setWidth("100%");
        statementTextArea.setVisible(false);
        tabs.add(statementTab);
        pages.add(statementTextArea);
        tabsToPages.put(statementTab, statementTextArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        generateRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(statementsService.generateRequest(getRequestMessage(), false));
        });

        signRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(statementsService.generateRequest(getRequestMessage(), true));
        });

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            StatementRequestMessage requestMessage = getRequestMessage();
            String request = statementsService.generateRequest(getRequestMessage(), true);
            requestTextArea.setValue(request);
            try {
                ResponseEntity<String> responseEntity = statementsService.sendRequest(request);
                responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    new Thread(() -> updateStatementResult(requestMessage.getMessageId(), getUI().get())).start();
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
        });
    }

    @SneakyThrows
    private void updateStatementResult(String messageId, UI ui) {
        for (int i = 0; i < STATEMENT_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = statementsService.getStatementResult(messageId);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        statementTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody())));
                    });
                    break;
                } else {
                    ui.access(() -> {
                        statementTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    });
                }
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
        request.setDateFrom(DateUtils.toDate(dateFromDatePicker.getValue()));
        request.setDateTo(DateUtils.toDate(dateToDatePicker.getValue()));
        requestMessage.getRequests().add(request);
        return requestMessage;
    }
}