package ru.alxndrzuev.iso20022.ui.payments;

import com.google.common.collect.Maps;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentPacket;
import ru.alxndrzuev.iso20022.utils.DateUtils;

import java.util.Date;
import java.util.Map;

@Slf4j
public class PaymentPacketComponent extends VerticalLayout {

    private TextField packetIdTextField;
    private DatePicker executionDateDatePicker;
    private TextField debtorCountryTextField;
    private TextField debtorInnTextField;
    private TextField debtorNameTextField;
    private TextField debtorAccountTextField;
    private TextField debtorAccountCurrencyTextField;
    private TextField debtorBankBicTextField;
    private TextField debtorBankNameTextField;
    private TextField debtorBankCorrAccountTextField;

    private Button addPaymentButton;
    private VerticalLayout paymentsLayout;
    Map<Integer, PaymentInstructionComponent> paymentInstructionComponents = Maps.newHashMap();

    public PaymentPacketComponent(String messageId) {
        setMargin(false);
        setPadding(false);
        setSpacing(false);

        FormLayout fieldsLayout = new FormLayout();
        fieldsLayout.setSizeFull();

        packetIdTextField = new TextField();
        packetIdTextField.setValue(messageId);
        packetIdTextField.setSizeFull();
        debtorCountryTextField = new TextField();
        debtorCountryTextField.setValue("RU");
        debtorCountryTextField.setSizeFull();
        debtorInnTextField = new TextField();
        debtorInnTextField.setSizeFull();
        debtorNameTextField = new TextField();
        debtorNameTextField.setSizeFull();
        debtorAccountTextField = new TextField();
        debtorAccountTextField.setSizeFull();
        executionDateDatePicker = new DatePicker();
        executionDateDatePicker.setSizeFull();
        executionDateDatePicker.setValue(DateUtils.toLocalDate(new Date()));
        debtorAccountCurrencyTextField = new TextField();
        debtorAccountCurrencyTextField.setValue("RUB");
        debtorAccountCurrencyTextField.setSizeFull();
        debtorBankBicTextField = new TextField();
        debtorBankBicTextField.setSizeFull();
        debtorBankNameTextField = new TextField();
        debtorBankNameTextField.setSizeFull();
        debtorBankCorrAccountTextField = new TextField();
        debtorBankCorrAccountTextField.setSizeFull();

        paymentsLayout = new VerticalLayout();
        paymentsLayout.setSizeFull();
        paymentsLayout.setPadding(false);
        paymentsLayout.setMargin(false);
        paymentInstructionComponents.put(1, new PaymentInstructionComponent(messageId, 1, paymentInstructionComponents));

        addPaymentButton = new Button("Add payment");
        addPaymentButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            Integer instructionId = paymentInstructionComponents.values().stream()
                    .mapToInt(PaymentInstructionComponent::getInstructionId)
                    .max().orElse(0) + 1;
            paymentInstructionComponents.put(instructionId, new PaymentInstructionComponent(messageId, instructionId, paymentInstructionComponents));
            repaintPaymentsLayout();
        });

        fieldsLayout.addFormItem(packetIdTextField, "Packet id");
        fieldsLayout.addFormItem(executionDateDatePicker, "Execution date");
        fieldsLayout.addFormItem(debtorInnTextField, "Inn");
        fieldsLayout.addFormItem(debtorNameTextField, "Name");
        fieldsLayout.addFormItem(debtorCountryTextField, "Country code");
        fieldsLayout.addFormItem(debtorAccountTextField, "Account");
        fieldsLayout.addFormItem(debtorAccountCurrencyTextField, "Account currency");
        fieldsLayout.addFormItem(debtorBankNameTextField, "Bank name");
        fieldsLayout.addFormItem(debtorBankBicTextField, "BIC");
        fieldsLayout.addFormItem(debtorBankCorrAccountTextField, "Bank corr account");
        add(fieldsLayout);
        add(paymentsLayout);
        add(addPaymentButton);

        repaintPaymentsLayout();
    }

    public PaymentPacket buildPaymentPacket() {
        PaymentPacket paymentPacket = new PaymentPacket();
        paymentPacket.setPacketId(packetIdTextField.getValue());
        paymentPacket.setExecutionDate(DateUtils.toDate(executionDateDatePicker.getValue()));
        paymentPacket.setDebtorInn(debtorInnTextField.getValue());
        paymentPacket.setDebtorName(debtorNameTextField.getValue());
        paymentPacket.setDebtorAccount(debtorAccountTextField.getValue());
        paymentPacket.setDebtorAccountCurrency(debtorAccountCurrencyTextField.getValue());
        paymentPacket.setDebtorCountry(debtorCountryTextField.getValue());
        paymentPacket.setDebtorBankBic(debtorBankBicTextField.getValue());
        paymentPacket.setDebtorBankCorrAccount(debtorBankCorrAccountTextField.getValue());
        paymentPacket.setDebtorBankName(debtorBankNameTextField.getValue());
        for (PaymentInstructionComponent paymentInstructionComponent : paymentInstructionComponents.values()) {
            paymentPacket.getPayments().add(paymentInstructionComponent.buildPaymentInstruction());
        }
        return paymentPacket;
    }

    private void repaintPaymentsLayout() {
        paymentsLayout.removeAll();
        for (PaymentInstructionComponent paymentInstructionComponent : paymentInstructionComponents.values()) {
            paymentsLayout.add(paymentInstructionComponent);
        }
    }
}
