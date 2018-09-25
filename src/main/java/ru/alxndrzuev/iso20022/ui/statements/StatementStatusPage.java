package ru.alxndrzuev.iso20022.ui.statements;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

@Push
@Route("statements/status")
@Slf4j
public class StatementStatusPage extends BasePage {
    private static final long STATEMENT_UPDATE_RATE = 10000l;
    private static final int STATEMENT_UPDATE_RETRY_COUNT = 10;

    @Autowired
    private StatementsService statementsService;

    @Autowired
    private XmlFormatter xmlFormatter;

    private TextField messageIdTextField;
    private Button sendRequest;
    private TextArea statementTextArea;

    public StatementStatusPage() {
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setSizeFull();

        formLayout.addFormItem(messageIdTextField, "Message id");

        sendRequest = new Button("Send request");
        HorizontalLayout buttonsLayout = new HorizontalLayout(sendRequest);
        fieldLayout.add(buttonsLayout);

        pages.removeAll();
        tabs.removeAll();

        Tab statementTab = new Tab("Statement");
        statementTextArea = new TextArea();
        statementTextArea.setReadOnly(true);
        statementTextArea.setWidth("100%");
        tabs.add(statementTab);
        pages.add(statementTextArea);
        tabsToPages.put(statementTab, statementTextArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            new Thread(() -> updateStatementResult(messageIdTextField.getValue(), getUI().get())).start();
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
}