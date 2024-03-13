package com.amzmall.project.config.jwt;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class AwsCognitoRSAProvider implements RSAKeyProvider {

    private final URL kidStoreUrl;
    private final JwkProvider provider;

    public AwsCognitoRSAProvider() {
        String url = "https://cognito-idp.ap-northeast-2.amazonaws.com/ap-northeast-2_qD0HzA3fK/.well-known/jwks.json";
        try {
            kidStoreUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("잘못된 URL=%s", url));
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