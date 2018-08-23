package ru.alxndrzuev.iso20022.model;

public enum Dialect {
    ALFA_BANK("Alfa-Bank");

    private String caption;

    Dialect(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return caption;
    }
}
