package ru.alxndrzuev.iso20022.gateways.ab;

import feign.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseException extends RuntimeException {
    private Response response;
    private String responseBody;
}
