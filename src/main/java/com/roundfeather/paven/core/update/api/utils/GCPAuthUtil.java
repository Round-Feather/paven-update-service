package com.roundfeather.paven.core.update.api.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;

import java.io.IOException;

public class GCPAuthUtil {

    public static String getAuthToken() {
        try {
            IdTokenProvider tokenProvider = (IdTokenProvider) GoogleCredentials.getApplicationDefault();

            IdTokenCredentials tokenCredential = IdTokenCredentials.newBuilder()
                    .setIdTokenProvider(tokenProvider)
                    .build();

            tokenCredential.refresh();
            return tokenCredential.getAccessToken().getTokenValue();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
