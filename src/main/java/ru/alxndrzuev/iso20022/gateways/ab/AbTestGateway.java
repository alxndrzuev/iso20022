package ru.alxndrzuev.iso20022.gateways.ab;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.alxndrzuev.iso20022.configuration.properties.ApplicationProperties;
import ru.alxndrzuev.iso20022.documents.payments.PaymentsGateway;
import ru.alxndrzuev.iso20022.documents.statements.StatementsGateway;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;

@Slf4j
public class AbTestGateway implements PaymentsGateway, StatementsGateway {

    @Autowired
    private ApplicationProperties applicationProperties;

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setSSLContext(getSSLContext())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);
    }

    @Override
    public ResponseEntity<String> getStatement(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        addAuthorizationHeader(headers);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(applicationProperties.getBaseUrl() + "/Statements", HttpMethod.POST, request, String.class);
    }

    @Override
    public ResponseEntity<String> getStatementResult(String messageId) {
        HttpHeaders headers = new HttpHeaders();
        addAuthorizationHeader(headers);
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(applicationProperties.getBaseUrl() + "/Statements/" + messageId, HttpMethod.GET, request, String.class);
    }

    @Override
    public ResponseEntity<String> createPayment(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        addAuthorizationHeader(headers);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(applicationProperties.getBaseUrl() + "/Payments", HttpMethod.POST, request, String.class);
    }

    @Override
    public ResponseEntity<String> getPaymentStatus(String messageId) {
        HttpHeaders headers = new HttpHeaders();
        addAuthorizationHeader(headers);
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(applicationProperties.getBaseUrl() + "/Payments/" + messageId, HttpMethod.GET, request, String.class);
    }

    private void addAuthorizationHeader(HttpHeaders headers) {
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String((applicationProperties.getLogin() + ":" + applicationProperties.getPassword()).getBytes()));
    }

    private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
    };

    @SneakyThrows
    private SSLContext getSSLContext() {
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, UNQUESTIONING_TRUST_MANAGER, null);
        return sc;
    }
}
