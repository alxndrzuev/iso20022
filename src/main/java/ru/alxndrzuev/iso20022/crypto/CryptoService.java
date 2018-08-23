package ru.alxndrzuev.iso20022.crypto;

import ru.alxndrzuev.iso20022.model.Certificate;

import java.util.List;

public interface CryptoService {
    String signRequest(String request);

    List<Certificate> getAvailableCertificates();
}
