package ru.alxndrzuev.iso20022.ui.payments;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentInstruction;

public class PaymentInstructionComponent extends FormLayout {

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

    public PaymentInstructionComponent() {
        instructionIdTextField = new TextField();
        instructionIdTextField.setSizeFull();
        documentIdTextField = new TextField();
        documentIdTextField.setSizeFull();
        urgencyComboBox = new ComboBox<>();
        urgencyComboBox.setItems(PaymentInstruction.PaymentUrgency.values());
        urgencyComboBox.setItemLabelGenerator(PaymentInstruction.PaymentUrgency::toString);
        urgencyComboBox.setSizeFull();
        priorityTextField = new TextField();
        priorityTextField.setSizeFull();
        uinTextField = new TextField();
        uinTextField.setSizeFull();
        descriptionTextField = new TextField();
        descriptionTextField.setSizeFull();
        documentDateDatePicker = new DatePicker();
        documentDateDatePicker.setSizeFull();
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

        addFormItem(instructionIdTextField, "Instruction id");
        addFormItem(documentIdTextField, "Document id");
        addFormItem(urgencyComboBox, "Urgency");
        addFormItem(priorityTextField, "Priority");
        addFormItem(uinTextField, "Uin");
        addFormItem(descriptionTextField, "Description");
        addFormItem(documentDateDatePicker, "Document date");
        addFormItem(amountTextField, "Amount");
        addFormItem(creditorBankNameTextField, "Bank name");
        addFormItem(creditorBankBicTextField, "BIC");
        addFormItem(creditorBankCorrAccountTextField, "Bank corr account");
        addFormItem(creditorNameTextField, "Creditor name");
        addFormItem(creditorCountryTextField, "Creditor country");
        addFormItem(creditorInnTextField, "Creditor inn");
        addFormItem(creditorAccountTextField, "Creditor account");
        addFormItem(creditorAccountCurrencyTextField, "Creditor account currency");
    }

    public PaymentInstruction buildPaymentInstruction() {
        return new PaymentInstruction();
    }
}
