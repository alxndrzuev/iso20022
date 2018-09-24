package ru.alxndrzuev.iso20022.ui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
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
    private Grid<Certificate> cerificateGrid;
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
        VerticalLayout fl = buildFooterLayout();

        main.add(vl);
        main.add(fl);
        main.expand(fl);
    }

    private Div buildSecurityLayout() {
        Div securityLayout = buildSettingsLayout("Signatures");

        FormLayout fl = new FormLayout();
        fl.setSizeFull();
        securityLayout.add(fl);
        cerificateGrid = new Grid<>();
        cerificateGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        Grid.Column ownerColumn = cerificateGrid.addColumn((ValueProvider<Certificate, String>) certificate -> certificate.getSubjectName() != null ? certificate.getSubjectName() : "").setHeader("Owner");
        Grid.Column organizationNameColumn = cerificateGrid.addColumn((ValueProvider<Certificate, String>) certificate -> certificate.getOrganizationName() != null ? certificate.getOrganizationName() : "").setHeader("Organization name");
        Grid.Column passwordColumn = cerificateGrid.addComponentColumn(certificate -> {
            if (certificate.isNeedPassword()) {
                PasswordField pf = new PasswordField();
                if (certificate.getPassword() != null) {
                    pf.setValue(certificate.getPassword());
                }
                pf.setWidth("90%");
                pf.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<PasswordField, String>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<PasswordField, String> event) {
                        certificate.setPassword(pf.getValue());
                    }
                });
                return pf;
            } else {
                return new Label("Password is not needed");
            }
        }).setHeader("Password");
        organizationNameColumn.setFlexGrow(5);
        fl.add(cerificateGrid);
        return securityLayout;
    }

    private Div buildNetworkLayout() {
        Div networkLayout = buildSettingsLayout("Network");

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
        Div commonLayout = buildSettingsLayout("Common");

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
        cerificateGrid.setItems(certificates);
        if (certificates.size() < 10) {
            cerificateGrid.setHeightByRows(true);
            cerificateGrid.setHeight(String.valueOf(certificates.size()));
        }
        cerificateGrid.asMultiSelect().setValue(Sets.newHashSet(applicationProperties.getCertificates()));
        for (Certificate selectedCertificate : cerificateGrid.getSelectedItems()) {
            for (Certificate certificate : applicationProperties.getCertificates()) {
                if (selectedCertificate.equals(certificate)) {
                    selectedCertificate.setPassword(certificate.getPassword());
                }
            }
        }

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

        save.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            try {
                applicationProperties.setCertificates(Lists.newArrayList(cerificateGrid.getSelectedItems()));
                applicationProperties.setLogin(loginTextField.getValue());
                applicationProperties.setPassword(passwordTextField.getValue());
                applicationProperties.setBaseUrl(baseUrlTextField.getValue());
                applicationProperties.setDialect(dialectComboBox.getValue());
                propertiesPersister.persist(new ApplicationProperties(applicationProperties));
                Notification.show("Configurations successfully saved", 3000, Notification.Position.TOP_END);
            } catch (Exception e) {
                log.error("Could not save configuration. Exception:", e);
                Notification.show("Could not save configuration", 3000, Notification.Position.TOP_END);
            }
        });
    }

    private Div buildSettingsLayout(String name) {
        Div commonLayout = new Div();
        commonLayout.getStyle().set("width", "calc(100% - 20px)");
        commonLayout.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        commonLayout.getStyle().set("padding", "10px");
        Span text = new Span(name);
        text.getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        commonLayout.add(text);
        return commonLayout;
    }
}