package ru.alxndrzuev.iso20022.gateways.ab;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NaiveHostnameVerifier implements HostnameVerifier {
    private final Set<String> naivelyTrustedHostnames;
    private final HostnameVerifier hostnameVerifier =
            HttpsURLConnection.getDefaultHostnameVerifier();

    public NaiveHostnameVerifier(String... naivelyTrustedHostnames) {
        this.naivelyTrustedHostnames =
                Collections.unmodifiableSet(new HashSet<>(Arrays.asList(naivelyTrustedHostnames)));
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return naivelyTrustedHostnames.contains(hostname) ||
                hostnameVerifier.verify(hostname, session);
    }
}