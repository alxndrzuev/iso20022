package ru.alxndrzuev.iso20022.ui.letters;

import com.google.common.collect.Maps;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.alxndrzuev.iso20022.documents.letters.LettersService;
import ru.alxndrzuev.iso20022.documents.letters.model.LetterMessage;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

import java.util.Map;
import java.util.UUID;

@Route("letters/new")
@Slf4j
@Push
public class NewLetterPage extends BasePage {

    @Autowired
    private LettersService lettersService;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long LETTER_STATUS_UPDATE_RATE = 10000l;
    private static final int LETTER_STATUS_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;
    private TextField agentNameTextField;
    private TextField agentInnTextField;

    private Button addLetterButton;
    private VerticalLayout lettersLayout;
    private Map<Integer, LetterComponent> letterComponents = Maps.newHashMap();

    private Button generateRequest;
    private Button signRequest;
    private Button sendRequest;

    private TextArea letterStatusesArea;

    public NewLetterPage() {
        FormLayout formLayout = new FormLayout();
        fieldLayout.add(formLayout);

        messageIdTextField = new TextField();
        messageIdTextField.setValue(UUID.randomUUID().toString().substring(10));
        messageIdTextField.setReadOnly(true);
        messageIdTextField.setSizeFull();
        agentNameTextField = new TextField();
        agentNameTextField.setSizeFull();
        agentInnTextField = new TextField();
        agentInnTextField.setSizeFull();

        lettersLayout = new VerticalLayout();
        lettersLayout.setSizeFull();
        lettersLayout.setPadding(false);
        lettersLayout.setMargin(false);
        letterComponents.put(1, new LetterComponent(messageIdTextField.getValue(), 1, letterComponents));

        addLetterButton = new Button("Add letter");
        addLetterButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            Integer instructionId = letterComponents.values().stream()
                    .mapToInt(LetterComponent::getLetterId)
                    .max().orElse(0) + 1;
            letterComponents.put(instructionId, new LetterComponent(messageIdTextField.getValue(), instructionId, letterComponents));
            repaintLettersLayout();
        });

        formLayout.addFormItem(messageIdTextField, "Message id");
        formLayout.addFormItem(agentNameTextField, "Agent name");
        formLayout.addFormItem(agentInnTextField, "Agent inn");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100px", 1), new FormLayout.ResponsiveStep("100px", 2));
        fieldLayout.add(lettersLayout);
        fieldLayout.add(addLetterButton);

        generateRequest = new Button("Generate request");
        signRequest = new Button("Sign request");
        sendRequest = new Button("Send request");
        HorizontalLayout buttonsLayout = new HorizontalLayout(generateRequest, signRequest, sendRequest);
        fieldLayout.add(buttonsLayout);

        Tab paymentStatusTab = new Tab("Letter statuses");
        letterStatusesArea = new TextArea();
        letterStatusesArea.setReadOnly(true);
        letterStatusesArea.setWidth("100%");
        letterStatusesArea.setVisible(false);
        tabs.add(paymentStatusTab);
        pages.add(letterStatusesArea);
        tabsToPages.put(paymentStatusTab, letterStatusesArea);
        addListeners();
        repaintLettersLayout();
    }

    protected void addListeners() {
        super.addListeners();

        generateRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(lettersService.generateRequest(getLetterMessage(), false));
        });

        signRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            requestTextArea.setValue(lettersService.generateRequest(getLetterMessage(), true));
        });

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            LetterMessage letterMessage = getLetterMessage();

            requestTextArea.setValue(lettersService.generateRequest(getLetterMessage(), true));
            try {
                ResponseEntity<String> responseEntity = lettersService.sendRequest(requestTextArea.getValue());
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    new Thread(() -> updateLettersResult(letterMessage.getMessageId(), getUI().get())).start();
                } else {
                    responseTextArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
        });
    }

    @SneakyThrows
    private void updateLettersResult(String messageId, UI ui) {
        for (int i = 0; i < LETTER_STATUS_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = lettersService.getLetterStatus(messageId, null);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        letterStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    });
                    sendAttachments();
                } else {
                    ui.access(() -> {
                        letterStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), responseEntity.getBody()));
                    });
                }
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            Thread.sleep(LETTER_STATUS_UPDATE_RATE);
        }
    }

    private LetterMessage getLetterMessage() {
        LetterMessage letterMessage = new LetterMessage();
        letterMessage.setMessageId(messageIdTextField.getValue());
        letterMessage.setAgentName(agentNameTextField.getValue());
        letterMessage.setAgentInn(agentInnTextField.getValue());
        for (LetterComponent letterComponent : letterComponents.values()) {
            letterMessage.getLetters().add(letterComponent.buildLetter());
        }
        return letterMessage;
    }

    private void repaintLettersLayout() {
        lettersLayout.removeAll();
        for (LetterComponent letterComponent : letterComponents.values()) {
            lettersLayout.add(letterComponent);
        }
    }

    private void sendAttachments() {
        for (LetterComponent letterComponent : letterComponents.values()) {
            try {
                MultiFileMemoryBuffer buffer = letterComponent.getFiles();
                for (String fileName : buffer.getFiles()) {
                    ResponseEntity<String> attachmentResponse = lettersService.addAttachment(fileName, IOUtils.toByteArray(buffer.getInputStream(fileName)), messageIdTextField.getValue() + "_" + letterComponent.getLetterId());
                    log.info("Attachment response:{}", attachmentResponse.toString());
                }
            } catch (Exception e) {
                log.error("Cannot send attachments. Exception:", e);
            }
        }
    }
}