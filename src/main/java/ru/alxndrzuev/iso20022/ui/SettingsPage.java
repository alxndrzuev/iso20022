package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
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
import ru.alxndrzuev.iso20022.configuration.properties.ApplicationProperties;
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

    @Autowired
    private ApplicationProperties applicationProperties;

    private TextField loginTextField;
    private PasswordField passwordTextField;
    private ComboBox<Certificate> firstCerificateComboBox;
    private ComboBox<Certificate> secondCerificateComboBox;
    private ComboBox<Dialect> dialectComboBox;
    private TextField baseUrlTextField;

    private Button save;

    public SettingsPage() {
        setSizeFull();

        main.removeAll();
        main.setPadding(true);
        main.getStyle().set("padding-bottom", "0px");

        VerticalLayout vl = new VerticalLayout();
        vl.getStyle().set("overflow", "auto");
        vl.setPadding(false);
        vl.setMargin(false);

        vl.add(buildSecurityLayout());
        vl.add(buildNetworkLayout());
        vl.add(buildCommonLayout());
        main.add(vl);
        VerticalLayout fl = buildFooterLayout();
        main.add(fl);
        main.expand(fl);
    }

    private Div buildSecurityLayout() {
        Div securityLayout = new Div();
        securityLayout.getStyle().set("width", "calc(100% - 20px)");
        securityLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        securityLayout.getStyle().set("padding", "10px");
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

    private Div buildNetworkLayout() {
        Div networkLayout = new Div();
        networkLayout.getStyle().set("width", "calc(100% - 20px)");
        networkLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        networkLayout.getStyle().set("padding", "10px");
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

    private Div buildCommonLayout() {
        Div commonLayout = new Div();
        commonLayout.getStyle().set("width", "calc(100% - 20px)");
        commonLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        commonLayout.getStyle().set("padding", "10px");
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

        if (applicationProperties.getLogin() != null) {
            loginTextField.setValue(applicationProperties.getLogin());
        }
        if (applicationProperties.getPassword() != null) {
            passwordTextField.setValue(applicationProperties.getPassword());
        }
        if (applicationProperties.getBaseUrl() != null) {
            baseUrlTextField.setValue(applicationProperties.getBaseUrl());
        }
        dialectComboBox.setValue(applicationProperties.getDialect());
        firstCerificateComboBox.setValue(certificates.stream()
                .filter(certificate -> certificate.getId().equals(applicationProperties.getFirstCertificateAlias()))
                .findFirst()
                .orElse(null));
        secondCerificateComboBox.setValue(certificates.stream()
                .filter(certificate -> certificate.getId().equals(applicationProperties.getSecondCertificateAlias()))
                .findFirst()
                .orElse(null));

        save.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            try {
                Properties properties = new Properties();
                if (firstCerificateComboBox.getValue() != null) {
                    properties.setProperty(PropertiesPersister.FIRST_CERTIFICATE_ALIAS_PROPERTY, firstCerificateComboBox.getValue().getId());
                }
                if (secondCerificateComboBox.getValue() != null) {
                    properties.setProperty(PropertiesPersister.SECOND_CERTIFICATE_ALIAS_PROPERTY, secondCerificateComboBox.getValue().getId());
                }
                properties.setProperty(PropertiesPersister.LOGIN_PROPERTY, loginTextField.getValue());
                properties.setProperty(PropertiesPersister.PASSWORD_PROPERTY, passwordTextField.getValue());
                properties.setProperty(PropertiesPersister.BASE_URL_PROPERTY, baseUrlTextField.getValue());
                if (dialectComboBox.getValue() != null) {
                    properties.setProperty(PropertiesPersister.DIALECT_PROPERTY, dialectComboBox.getValue().name());
                }
                propertiesPersister.persist(properties);
                Notification.show("Configurations successfully saved", 3000, Notification.Position.TOP_END);
            } catch (Exception e) {
                Notification.show("Could not save configuration", 3000, Notification.Position.TOP_END);
            }
        });
    }
}