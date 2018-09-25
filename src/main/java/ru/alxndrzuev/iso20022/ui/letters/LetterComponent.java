package ru.alxndrzuev.iso20022.ui.letters;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import ru.alxndrzuev.iso20022.documents.letters.model.Letter;

import java.util.Map;

public class LetterComponent extends Div {

    private TextField letterIdTextField;
    private ComboBox<Letter.LetterCategory> categoryComboBox;
    private TextField senderNameTextField;
    private TextField senderInnTextField;
    private TextField senderAccountTextField;
    private TextField receiverNameTextField;
    private TextField receiverBicTextField;
    private TextField subjectTextField;
    private TextArea bodyTextArea;

    private Button removeInstructionButton;

    private int letterId;

    public LetterComponent(String messageId, int letterId, Map components) {
        this.letterId = letterId;

        getStyle().set("width", "calc(100% - 4px)");
        getStyle().set("background-color", "var(--lumo-shade-5pct)");
        getStyle().set("padding", "2px");

        letterIdTextField = new TextField();
        letterIdTextField.setReadOnly(true);
        letterIdTextField.setSizeFull();
        letterIdTextField.setValue(messageId + "_" + String.valueOf(letterId));
        Label label = new Label("Letter id");
        label.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        FormLayout fieldsLayout = new FormLayout();
        fieldsLayout.setSizeFull();

        categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(Letter.LetterCategory.values());
        categoryComboBox.setItemLabelGenerator(Letter.LetterCategory::toString);
        categoryComboBox.setValue(Letter.LetterCategory.OTHR);
        categoryComboBox.setSizeFull();
        senderNameTextField = new TextField();
        senderNameTextField.setSizeFull();
        senderInnTextField = new TextField();
        senderInnTextField.setSizeFull();
        senderAccountTextField = new TextField();
        senderAccountTextField.setSizeFull();
        receiverNameTextField = new TextField();
        receiverNameTextField.setSizeFull();
        receiverBicTextField = new TextField();
        receiverBicTextField.setSizeFull();
        subjectTextField = new TextField("Subject");
        subjectTextField.setSizeFull();
        bodyTextArea = new TextArea("Body");
        bodyTextArea.setSizeFull();

        removeInstructionButton = new Button("Remove letter");
        removeInstructionButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            setVisible(false);
            components.remove(letterId);
        });
        fieldsLayout.addFormItem(letterIdTextField, label);
        fieldsLayout.add(removeInstructionButton);
        fieldsLayout.addFormItem(senderNameTextField, "Sender name");
        fieldsLayout.addFormItem(senderInnTextField, "Sender inn");
        fieldsLayout.addFormItem(senderAccountTextField, "Sender account");
        fieldsLayout.addFormItem(receiverNameTextField, "Receiver name");
        fieldsLayout.addFormItem(receiverBicTextField, "Receiver bic");
        fieldsLayout.addFormItem(categoryComboBox, "Letter category");
        add(fieldsLayout);
        add(subjectTextField);
        add(bodyTextArea);
    }

    public Letter buildLetter() {
        Letter letter = new Letter();
        letter.setId(letterIdTextField.getValue());
        letter.setSenderName(senderNameTextField.getValue());
        letter.setSenderInn(senderInnTextField.getValue());
        letter.setSenderAccount(senderAccountTextField.getValue());
        letter.setReceiverName(receiverNameTextField.getValue());
        letter.setReceiverBic(receiverBicTextField.getValue());
        letter.setSubject(subjectTextField.getValue());
        letter.setCategory(categoryComboBox.getValue());
        letter.setBody(bodyTextArea.getValue());
        return letter;
    }

    public int getLetterId() {
        return letterId;
    }
}
