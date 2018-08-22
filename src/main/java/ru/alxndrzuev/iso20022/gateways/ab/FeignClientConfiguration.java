package ru.alxndrzuev.iso20022.gateways.ab;

import feign.Client;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class FeignClientConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("643223", "123456");
    }

    @Bean
    public Client client() throws NoSuchAlgorithmException, KeyManagementException {
        return new Client.Default(new NaiveSSLSocketFactory("grampus-int.alfabank.ru"),
                new NaiveHostnameVerifier("grampus-int.alfabank.ru"));
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            @SneakyThrows
            public Exception decode(String methodKey, Response response) {
                return new ResponseException(response, IOUtils.toString(response.body().asReader()));
            }
        };
    }
}
