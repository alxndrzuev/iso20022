package ru.alxndrzuev.iso20022.ui.payments;

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
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentMessage;
import ru.alxndrzuev.iso20022.documents.payments.PaymentsService;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

import java.util.UUID;

@Route("payments/new")
@Slf4j
@Push
public class NewPaymentPage extends BasePage {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long PAYMENT_STATUS_UPDATE_RATE = 10000l;
    private static final int PAYMENT_STATUS_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;
    private TextField agentNameTextField;
    private TextField agentInnTextField;

    private Button generateRequest;
    private Button signRequest;
    private Button sendRequest;

    private TextArea paymentStatusesArea;
    private PaymentPacketComponent paymentPacketComponent;

    public NewPaymentPage() {
        FormLayout formLayout = new FormLayout();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setValue(UUID.randomUUID().toString().substring(10));
        messageIdTextField.setSizeFull();
        agentNameTextField = new TextField();
        agentNameTextField.setSizeFull();
        agentInnTextField = new TextField();
        agentInnTextField.setSizeFull();

        formLayout.addFormItem(messageIdTextField, "Message id");
        formLayout.addFormItem(agentNameTextField, "Agent name");
        formLayout.addFormItem(agentInnTextField, "Agent inn");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100px", 1), new FormLayout.ResponsiveStep("100px", 2));
        paymentPacketComponent = new PaymentPacketComponent(messageIdTextField.getValue());
        fieldLayout.add(paymentPacketComponent);

        generateRequest = new Button("Generate request");
        signRequest = new Button("Sign request");
        sendRequest = new Button("Send request");
        HorizontalLayout buttonsLayout = new HorizontalLayout(generateRequest, signRequest, sendRequest);
        fieldLayout.add(buttonsLayout);

        Tab paymentStatusTab = new Tab("Payment statuses");
        paymentStatusesArea = new TextArea();
        paymentStatusesArea.setReadOnly(true);
        paymentStatusesArea.setWidth("100%");
        paymentStatusesArea.setVisible(false);
        tabs.add(paymentStatusTab);
        pages.add(paymentStatusesArea);
        tabsToPages.put(paymentStatusTab, paymentStatusesArea);
        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        generateRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(paymentsService.generateRequest(getPaymentMessage(), false));
        });

        signRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(paymentsService.generateRequest(getPaymentMessage(), true));
        });

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            PaymentMessage requestMessage = getPaymentMessage();

            requestTextArea.setValue(paymentsService.generateRequest(getPaymentMessage(), true));
            try {
                ResponseEntity<String> responseEntity = paymentsService.sendRequest(requestTextArea.getValue());
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody())));
                    new Thread(() -> updatePaymentsResult(requestMessage.getMessageId(), getUI().get())).start();
                } else {
                    responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
        });
    }

    @SneakyThrows
    private void updatePaymentsResult(String messageId, UI ui) {
        for (int i = 0; i < PAYMENT_STATUS_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = paymentsService.getPaymentStatus(messageId, null);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        paymentStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody())));
                    });
                } else {
                    ui.access(() -> {
                        paymentStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    });
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            Thread.sleep(PAYMENT_STATUS_UPDATE_RATE);
        }

    }

    private PaymentMessage getPaymentMessage() {
        PaymentMessage requestMessage = new PaymentMessage();
        requestMessage.setMessageId(messageIdTextField.getValue());
        requestMessage.setAgentInn(agentInnTextField.getValue());
        requestMessage.setAgentName(agentNameTextField.getValue());
        requestMessage.getPaymentPackets().add(paymentPacketComponent.buildPaymentPacket());
        return requestMessage;
    }
}