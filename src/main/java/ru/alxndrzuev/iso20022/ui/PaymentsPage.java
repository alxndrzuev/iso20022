package ru.alxndrzuev.iso20022.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route("payments")
public class PaymentsPage extends BasePage {
    public PaymentsPage() {
        main.add(new Label("Payments page"));
    }
}