package ru.alxndrzuev.iso20022.ui.payments;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import ru.alxndrzuev.iso20022.documents.payments.builder.PaymentRequestMessageBuilder;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentMessage;
import ru.alxndrzuev.iso20022.gateways.ab.AbTestGateway;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

import java.util.UUID;

@Route("payments")
@Slf4j
public class PaymentsPage extends BasePage {
    @Autowired
    private PaymentRequestMessageBuilder paymentRequestMessageBuilder;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private AbTestGateway gateway;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long STATEMENT_UPDATE_RATE = 10000l;
    private static final int STATEMENT_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;
    private TextField agentNameTextField;
    private TextField agentInnTextField;

    private Button generateRequest;
    private Button signRequest;
    private Button sendRequest;

    private TextArea paymentStatusesArea;

    public PaymentsPage() {
        FormLayout formLayout = new FormLayout();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setValue(UUID.randomUUID().toString().substring(10));
        agentNameTextField = new TextField();
        agentInnTextField = new TextField();

        formLayout.addFormItem(messageIdTextField, "Message id");
        formLayout.addFormItem(agentNameTextField, "Agent name");
        formLayout.addFormItem(agentInnTextField, "Agent inn");

        fieldLayout.add(new PaymentInstructionComponent());

        generateRequest = new Button("Generate request");
        signRequest = new Button("Sign request");
        sendRequest = new Button("Send request");
        HorizontalLayout buttonsLayout = new HorizontalLayout(generateRequest, signRequest, sendRequest);
        fieldLayout.add(buttonsLayout);

        Tab statementTab = new Tab("Payment statuses");
        paymentStatusesArea = new TextArea();
        paymentStatusesArea.setReadOnly(true);
        paymentStatusesArea.setWidth("100%");
        paymentStatusesArea.setVisible(false);
        tabs.add(statementTab);
        pages.add(paymentStatusesArea);
        tabsToPages.put(statementTab, paymentStatusesArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        generateRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            PaymentMessage requestMessage = getPaymentMessage();
            String requestString = paymentRequestMessageBuilder.buildRequest(requestMessage);
            requestTextArea.setValue(requestString);
        });

        signRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            PaymentMessage requestMessage = getPaymentMessage();
            String requestString = paymentRequestMessageBuilder.buildRequest(requestMessage);
            String signedRequestString = cryptoService.signRequest(requestString);
            requestTextArea.setValue(signedRequestString);
        });

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            PaymentMessage requestMessage = getPaymentMessage();

            String requestString = paymentRequestMessageBuilder.buildRequest(requestMessage);
            String signedRequestString = cryptoService.signRequest(requestString);
            requestTextArea.setValue(signedRequestString);
            try {
                ResponseEntity<String> responseEntity = gateway.getStatement(signedRequestString);
                responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    new Thread(() -> updatePaymentsResult(requestMessage.getMessageId(), getUI().get())).start();
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
        });
    }

    @SneakyThrows
    private void updatePaymentsResult(String messageId, UI ui) {
        for (int i = 0; i < STATEMENT_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = gateway.getStatementResult(messageId);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        paymentStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody())));
                    });
                    break;
                } else {
                    ui.access(() -> {
                        paymentStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    });
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            Thread.sleep(STATEMENT_UPDATE_RATE);
        }

    }

    private PaymentMessage getPaymentMessage() {
        PaymentMessage requestMessage = new PaymentMessage();
        requestMessage.setMessageId(messageIdTextField.getValue());

        return requestMessage;
    }
}