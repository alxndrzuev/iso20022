package ru.alxndrzuev.iso20022.crypto;

import lombok.SneakyThrows;
import ru.alxndrzuev.iso20022.model.Certificate;

import java.util.List;

public interface CryptoService {
    String signRequest(String request, String element);

    List<Certificate> getAvailableCertificates();
}
