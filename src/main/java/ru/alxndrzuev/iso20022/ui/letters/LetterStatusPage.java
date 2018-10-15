package ru.alxndrzuev.iso20022.ui.letters;

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
import ru.alxndrzuev.iso20022.documents.letters.LettersService;
import ru.alxndrzuev.iso20022.ui.BasePage;
import ru.alxndrzuev.iso20022.utils.XmlFormatter;

@Route("letters/status")
@Slf4j
@Push
public class LetterStatusPage extends BasePage {

    @Autowired
    private LettersService lettersService;

    @Autowired
    private XmlFormatter xmlFormatter;

    private static final long LETTER_STATUS_UPDATE_RATE = 10000l;
    private static final int LETTER_STATUS_UPDATE_RETRY_COUNT = 10;

    private TextField messageIdTextField;

    private Button sendRequest;

    private TextArea letterStatusesArea;

    public LetterStatusPage() {
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

        Tab letterStatusTab = new Tab("Letter statuses");
        letterStatusesArea = new TextArea();
        letterStatusesArea.setReadOnly(true);
        letterStatusesArea.setWidth("100%");
        pages.add(letterStatusesArea);
        tabs.add(letterStatusTab);
        tabsToPages.put(letterStatusTab, letterStatusesArea);

        addListeners();
    }

    protected void addListeners() {
        super.addListeners();

        sendRequest.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            new Thread(() -> updateLettersResult(messageIdTextField.getValue(), getUI().get())).start();
        });
    }

    @SneakyThrows
    private void updateLettersResult(String messageId, UI ui) {
        for (int i = 0; i < LETTER_STATUS_UPDATE_RETRY_COUNT; i++) {
            try {
                ResponseEntity<String> responseEntity = lettersService.getLetterStatus(messageId, null);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ui.access(() -> {
                        letterStatusesArea.setValue(generateResult(responseEntity.getStatusCode().value(), responseEntity.getHeaders().entrySet(), xmlFormatter.format(responseEntity.getBody())));
                    });
                    break;
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
}