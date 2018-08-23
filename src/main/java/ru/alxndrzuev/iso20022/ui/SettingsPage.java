package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.model.Certificate;
import ru.alxndrzuev.iso20022.model.Dialect;
import ru.alxndrzuev.iso20022.utils.PropertiesPersister;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Push
@Route("settings")
@Slf4j
public class SettingsPage extends BasePage {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private PropertiesPersister propertiesPersister;

    private TextField loginTextField;
    private PasswordField passwordTextField;
    private ComboBox<Certificate> firstCerificateComboBox;
    private ComboBox<Certificate> secondCerificateComboBox;
    private ComboBox<Dialect> dialectComboBox;
    private TextField baseUrlTextField;

    private Button save;

    public SettingsPage() {
        main.removeAll();
        main.setPadding(true);

        VerticalLayout vl = new VerticalLayout();
        vl.getStyle().set("overflow", "auto");
        vl.setPadding(false);
        vl.setMargin(false);

        vl.add(buildSecurityLayout());
        vl.add(buildNetworkLayout());
        vl.add(buildCommonLayout());
        main.add(vl);
        main.add(buildFooterLayout());
    }

    private VerticalLayout buildSecurityLayout() {
        VerticalLayout securityLayout = new VerticalLayout();
        securityLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        Label label = new Label("Signatures");
        label.getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        securityLayout.add(label);

        FormLayout fl = new FormLayout();
        fl.setSizeFull();
        securityLayout.add(fl);
        firstCerificateComboBox = new ComboBox("First signature certificate");
        firstCerificateComboBox.setItemLabelGenerator(Certificate::getName);
        secondCerificateComboBox = new ComboBox("Second signature certificate");
        secondCerificateComboBox.setItemLabelGenerator(Certificate::getName);
        fl.add(firstCerificateComboBox, secondCerificateComboBox);
        return securityLayout;
    }

    private VerticalLayout buildNetworkLayout() {
        VerticalLayout networkLayout = new VerticalLayout();
        networkLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        Span text = new Span("Network");
        text.getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        networkLayout.add(text);

        FormLayout fl = new FormLayout();
        fl.setSizeFull();
        networkLayout.add(fl);
        baseUrlTextField = new TextField("Base url");
        loginTextField = new TextField("Login");
        passwordTextField = new PasswordField("Password");
        fl.add(baseUrlTextField, loginTextField, passwordTextField);
        return networkLayout;
    }

    private VerticalLayout buildCommonLayout() {
        VerticalLayout commonLayout = new VerticalLayout();
        commonLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        Span text = new Span("Common");
        text.getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        commonLayout.add(text);

        FormLayout fl = new FormLayout();
        fl.setSizeFull();
        commonLayout.add(fl);
        dialectComboBox = new ComboBox("Dialect");
        dialectComboBox.setItems(Dialect.values());
        dialectComboBox.setItemLabelGenerator(Dialect::toString);
        fl.add(dialectComboBox);
        return commonLayout;
    }

    private VerticalLayout buildFooterLayout() {
        VerticalLayout footerLayout = new VerticalLayout();

        save = new Button("Save");
        footerLayout.add(save);
        footerLayout.setHorizontalComponentAlignment(Alignment.END, save);
        return footerLayout;
    }

    @PostConstruct
    public void init() {
        List<Certificate> certificates = cryptoService.getAvailableCertificates();
        firstCerificateComboBox.setItems(certificates);
        secondCerificateComboBox.setItems(certificates);

        Properties props = propertiesPersister.load();
        if (props.getProperty("login") != null) {
            loginTextField.setValue(props.getProperty("login"));
        }
        if (props.getProperty("password") != null) {
            passwordTextField.setValue(props.getProperty("password"));
        }
        if (props.getProperty("base_url") != null) {
            baseUrlTextField.setValue(props.getProperty("base_url"));
        }
        if (props.getProperty("dialect") != null) {
            dialectComboBox.setValue(Dialect.valueOf(props.getProperty("dialect")));
        }
        firstCerificateComboBox.setValue(certificates.stream()
                .filter(certificate -> certificate.getId().equals(props.getProperty("first_certificate_alias")))
                .findFirst()
                .orElse(null));
        secondCerificateComboBox.setValue(certificates.stream()
                .filter(certificate -> certificate.getId().equals(props.getProperty("second_certificate_alias")))
                .findFirst()
                .orElse(null));

        save.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            try {
                Properties properties = new Properties();
                if (firstCerificateComboBox.getValue() != null) {
                    properties.setProperty("first_certificate_alias", firstCerificateComboBox.getValue().getId());
                }
                if (secondCerificateComboBox.getValue() != null) {
                    properties.setProperty("second_certificate_alias", secondCerificateComboBox.getValue().getId());
                }
                properties.setProperty("login", loginTextField.getValue());
                properties.setProperty("password", passwordTextField.getValue());
                properties.setProperty("base_url", baseUrlTextField.getValue());
                if (dialectComboBox.getValue() != null) {
                    properties.setProperty("dialect", dialectComboBox.getValue().name());
                }
                propertiesPersister.persist(properties);
                Notification.show("Configurations successfully saved", 5000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Could not save configuration", 5000, Notification.Position.MIDDLE);
            }
        });
    }
}