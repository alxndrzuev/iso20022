package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BasePage extends HorizontalLayout {

    protected VerticalLayout menu;
    protected VerticalLayout main;
    protected VerticalLayout fieldLayout;

    protected Map<Tab, Component> tabsToPages;
    protected Set<Component> pagesShown;
    protected Tabs tabs;
    protected TextArea requestTextArea;
    protected TextArea responseTextArea;
    protected Div pages;

    public BasePage() {
        setSpacing(false);
        setMargin(false);
        setPadding(false);

        add(buildMenu());
        add(buildMainLayout());
    }

    protected void addListeners() {
        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
    }

    protected String generateResult(int httpStatusCode, Set<Map.Entry<String, Collection<String>>> headers, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpStatusCode).append(" ").append(HttpStatus.valueOf(httpStatusCode).getReasonPhrase());
        sb.append("\n\n");
        headers.stream().forEach(entry -> {
            sb.append(entry.getKey());
            sb.append(" : ");
            sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
            sb.append("\n");
        });
        sb.append("\n");
        if (body != null) {
            sb.append(body);
        }
        return sb.toString();
    }

    protected String generateResultt(int httpStatusCode, Set<Map.Entry<String, List<String>>> headers, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpStatusCode).append(" ").append(HttpStatus.valueOf(httpStatusCode).getReasonPhrase());
        sb.append("\n\n");
        headers.stream().forEach(entry -> {
            sb.append(entry.getKey());
            sb.append(" : ");
            sb.append(entry.getValue().stream().collect(Collectors.joining(",")));
            sb.append("\n");
        });
        sb.append("\n");
        if (body != null) {
            sb.append(body);
        }
        return sb.toString();
    }

    private VerticalLayout buildMenu() {
        menu = new VerticalLayout();
        menu.setWidth("300px");
        menu.setSpacing(false);
        menu.setMargin(false);
        menu.setPadding(false);
        menu.add(buildMenuButton("Выписка", "statements"));
        menu.add(buildMenuButton("Платежи", "payments"));
        menu.add(buildMenuButton("Настройки", "settings"));
        return menu;
    }

    private VerticalLayout buildMainLayout() {
        main = new VerticalLayout();
        main.setMargin(false);
        main.setPadding(false);
        main.setSpacing(false);
        add(menu, main);

        fieldLayout = new VerticalLayout();
        main.add(fieldLayout);

        Tab requestTab = new Tab("Запрос");
        requestTextArea = new TextArea();
        requestTextArea.setReadOnly(true);
        requestTextArea.setWidth("100%");
        requestTextArea.setValue("");
        Tab responseTab = new Tab("Ответ");
        responseTextArea = new TextArea();
        responseTextArea.setReadOnly(true);
        responseTextArea.setWidth("100%");
        responseTextArea.setVisible(false);
        responseTextArea.setValue("");

        tabs = new Tabs(requestTab, responseTab);
        pages = new Div(requestTextArea, responseTextArea);
        pages.setWidth("100%");
        tabsToPages = new HashMap<>();
        tabsToPages.put(requestTab, requestTextArea);
        tabsToPages.put(responseTab, responseTextArea);
        pagesShown = Stream.of(requestTextArea)
                .collect(Collectors.toSet());
        main.add(tabs);
        main.add(pages);
        return main;
    }

    private Button buildMenuButton(String name, String location) {
        Button button = new Button(name);
        button.setWidth("100%");
        button.addClickListener(clickEvent -> {
            UI.getCurrent().navigate(location);
        });
        return button;
    }
}
