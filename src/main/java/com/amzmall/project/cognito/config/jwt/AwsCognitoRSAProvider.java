package com.amzmall.project.cognito.config.jwt;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;

public class AwsCognitoRSAProvider implements RSAKeyProvider {

    @Value("${COGNITO_RSA_URL}")
    private String cognitoRsaUrl;
    private final URL kidStoreUrl;
    private final JwkProvider provider;

    public AwsCognitoRSAProvider() {
        try {
            kidStoreUrl = new URL(cognitoRsaUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("잘못된 URL=%s", cognitoRsaUrl));
        }
        provider = new JwkProviderBuilder(kidStoreUrl).build();
    }


    @Override
    public RSAPublicKey getPublicKeyById(String kid) {
        try {
            return (RSAPublicKey) provider.get(kid).getPublicKey();
        } catch (JwkException e) {
            throw new IllegalArgumentException(String.format("kidStoreUrl=%s에서 JWT kid=%s을(를) 가져오지 못했습니다.", kid, kidStoreUrl));
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
