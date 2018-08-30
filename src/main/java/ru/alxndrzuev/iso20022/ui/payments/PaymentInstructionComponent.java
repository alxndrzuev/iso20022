package ru.alxndrzuev.iso20022.ui.payments;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentInstruction;
import ru.alxndrzuev.iso20022.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class PaymentInstructionComponent extends Div {

    private TextField instructionIdTextField;
    private TextField documentIdTextField;
    private ComboBox<PaymentInstruction.PaymentUrgency> urgencyComboBox;
    private TextField priorityTextField;
    private TextField uinTextField;
    private TextField descriptionTextField;
    private DatePicker documentDateDatePicker;
    private TextField amountTextField;
    private TextField creditorBankBicTextField;
    private TextField creditorBankNameTextField;
    private TextField creditorBankCorrAccountTextField;
    private TextField creditorNameTextField;
    private TextField creditorCountryTextField;
    private TextField creditorInnTextField;
    private TextField creditorAccountTextField;
    private TextField creditorAccountCurrencyTextField;

    private Button removeInstructionButton;

    private String messageId;
    private int instructionId;
    private Map components;

    public PaymentInstructionComponent(String messageId, int instructionId, Map components) {
        this.instructionId = instructionId;
        this.components = components;
        this.messageId = messageId;

        getStyle().set("width", "calc(100% - 4px)");
        getStyle().set("background-color", "var(--lumo-shade-5pct)");
        getStyle().set("padding", "2px");

        instructionIdTextField = new TextField();
        instructionIdTextField.setReadOnly(true);
        instructionIdTextField.setSizeFull();
        instructionIdTextField.setValue(messageId + "_" + String.valueOf(instructionId));
        Label label = new Label("Instruction id");
        label.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        FormLayout fieldsLayout = new FormLayout();
        fieldsLayout.setSizeFull();

        documentIdTextField = new TextField();
        documentIdTextField.setSizeFull();
        documentIdTextField.setValue(String.valueOf(instructionId));
        urgencyComboBox = new ComboBox<>();
        urgencyComboBox.setItems(PaymentInstruction.PaymentUrgency.values());
        urgencyComboBox.setItemLabelGenerator(PaymentInstruction.PaymentUrgency::toString);
        urgencyComboBox.setValue(PaymentInstruction.PaymentUrgency.NURG);
        urgencyComboBox.setSizeFull();
        priorityTextField = new TextField();
        priorityTextField.setValue("5");
        priorityTextField.setSizeFull();
        uinTextField = new TextField();
        uinTextField.setValue("0");
        uinTextField.setSizeFull();
        descriptionTextField = new TextField();
        descriptionTextField.setSizeFull();
        documentDateDatePicker = new DatePicker();
        documentDateDatePicker.setSizeFull();
        documentDateDatePicker.setValue(DateUtils.toLocalDate(new Date()));
        amountTextField = new TextField();
        amountTextField.setSizeFull();
        creditorBankBicTextField = new TextField();
        creditorBankBicTextField.setSizeFull();
        creditorBankNameTextField = new TextField();
        creditorBankNameTextField.setSizeFull();
        creditorBankCorrAccountTextField = new TextField();
        creditorBankCorrAccountTextField.setSizeFull();
        creditorNameTextField = new TextField();
        creditorNameTextField.setSizeFull();
        creditorCountryTextField = new TextField();
        creditorCountryTextField.setSizeFull();
        creditorInnTextField = new TextField();
        creditorInnTextField.setSizeFull();
        creditorAccountTextField = new TextField();
        creditorAccountTextField.setSizeFull();
        creditorAccountCurrencyTextField = new TextField();
        creditorAccountCurrencyTextField.setSizeFull();

        removeInstructionButton = new Button("Remove instruction");
        removeInstructionButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                setVisible(false);
                components.remove(instructionId);
            }
        });
        fieldsLayout.addFormItem(instructionIdTextField, label);
        fieldsLayout.add(removeInstructionButton);
        fieldsLayout.addFormItem(documentIdTextField, "Document id");
        fieldsLayout.addFormItem(urgencyComboBox, "Urgency");
        fieldsLayout.addFormItem(priorityTextField, "Priority");
        fieldsLayout.addFormItem(uinTextField, "Uin");
        fieldsLayout.addFormItem(descriptionTextField, "Description");
        fieldsLayout.addFormItem(documentDateDatePicker, "Document date");
        fieldsLayout.addFormItem(amountTextField, "Amount");
        fieldsLayout.addFormItem(creditorBankNameTextField, "Bank name");
        fieldsLayout.addFormItem(creditorBankBicTextField, "BIC");
        fieldsLayout.addFormItem(creditorBankCorrAccountTextField, "Bank corr account");
        fieldsLayout.addFormItem(creditorNameTextField, "Creditor name");
        fieldsLayout.addFormItem(creditorCountryTextField, "Creditor country");
        fieldsLayout.addFormItem(creditorInnTextField, "Creditor inn");
        fieldsLayout.addFormItem(creditorAccountTextField, "Creditor account");
        fieldsLayout.addFormItem(creditorAccountCurrencyTextField, "Creditor account currency");

        add(fieldsLayout);
    }

    public PaymentInstruction buildPaymentInstruction() {
        PaymentInstruction paymentInstruction = new PaymentInstruction();
        paymentInstruction.setInstructionId(instructionIdTextField.getValue());
        paymentInstruction.setDocumentId(documentIdTextField.getValue());
        paymentInstruction.setUrgency(urgencyComboBox.getValue());
        paymentInstruction.setPriority(priorityTextField.getValue());
        paymentInstruction.setUin(uinTextField.getValue());
        paymentInstruction.setDescription(descriptionTextField.getValue());
        paymentInstruction.setDocumentDate(DateUtils.toDate(documentDateDatePicker.getValue()));
        paymentInstruction.setAmount(new BigDecimal(amountTextField.getValue()));
        paymentInstruction.setCreditorBankBic(creditorBankBicTextField.getValue());
        paymentInstruction.setCreditorBankName(creditorBankNameTextField.getValue());
        paymentInstruction.setCreditorBankCorrAccount(creditorBankCorrAccountTextField.getValue());
        paymentInstruction.setCreditorName(creditorNameTextField.getValue());
        paymentInstruction.setCreditorCountry(creditorCountryTextField.getValue());
        paymentInstruction.setCreditorInn(creditorInnTextField.getValue());
        paymentInstruction.setCreditorAccount(creditorAccountTextField.getValue());
        paymentInstruction.setCreditorAccountCurrency(creditorAccountCurrencyTextField.getValue());
        return paymentInstruction;
    }

    public int getInstructionId() {
        return instructionId;
    }
}
