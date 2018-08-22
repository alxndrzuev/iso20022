package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BasePage extends HorizontalLayout {

    protected VerticalLayout menu;
    protected VerticalLayout main;

    public BasePage() {
        menu = new VerticalLayout();
        menu.setWidth("300px");
        Button statementsButton = new Button("Выписка");
        statementsButton.addClickListener(clickEvent -> {
            UI.getCurrent().navigate("statements");
        });
        menu.add(statementsButton);
        Button paymentsButton = new Button("Платежи");
        paymentsButton.addClickListener(clickEvent -> {
            UI.getCurrent().navigate("payments");
        });
        menu.add(paymentsButton);
        main = new VerticalLayout();
        add(menu, main);
    }
}
