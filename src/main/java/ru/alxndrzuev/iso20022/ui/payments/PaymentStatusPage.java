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
import ru.alxndrzuev.iso20022.documents.payments.PaymentsGateway;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

@Route("payment/status")
@Slf4j
@Push
public class PaymentStatusPage extends BasePage {

    @Autowired
    private PaymentsGateway paymentsGateway;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long PAYMENT_STATUS_UPDATE_RATE = 10000l;
    private static final int PAYMENT_STATUS_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;

    private Button sendRequest;

    private TextArea paymentStatusesArea;

    public PaymentStatusPage() {
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

        Tab paymentStatusTab = new Tab("Payment statuses");
        paymentStatusesArea = new TextArea();
        paymentStatusesArea.setReadOnly(true);
        paymentStatusesArea.setWidth("100%");
        pages.add(paymentStatusesArea);
        tabs.add(paymentStatusTab);
        tabsToPages.put(paymentStatusTab, paymentStatusesArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            new Thread(() -> updatePaymentsResult(messageIdTextField.getValue(), getUI().get())).start();
        });
    }

    @SneakyThrows
    private void updatePaymentsResult(String messageId, UI ui) {
        for (int i = 0; i < PAYMENT_STATUS_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = paymentsGateway.getPaymentStatus(messageId);
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
            Thread.sleep(PAYMENT_STATUS_UPDATE_RATE);
        }

    }
}