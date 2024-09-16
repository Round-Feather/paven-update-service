package com.roundfeather.paven.core.update.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roundfeather.paven.core.update.api.ConfigClient;
import com.roundfeather.paven.core.update.config.DatastoreConfig;
import com.roundfeather.paven.core.update.config.UserInfoConfig;
import com.roundfeather.paven.core.update.model.MinimalUser;
import com.roundfeather.paven.model.config.Tenant;
import com.roundfeather.paven.utils.events.clients.EventClient;
import com.roundfeather.persistence.utils.datastore.DatastoreNamespace;
import com.roundfeather.persistence.utils.datastore.DatastoreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class UserUpdateService {
    public static final String TENANT_HEADER_KEY = "tenant";
    public static final String SPACE_HEADER_KEY = "spaceId";
    public static final String NAMESPACE_HEADER_KEY = "namespace";
    public static final String PLATFORM_HEADER_KEY = "platform";
    public static final String OS_HEADER_KEY = "os";
    public static final String USER_INFO_HEADER_KEY = "X-Apigateway-Api-Userinfo";

    @Inject
    ObjectMapper objectMapper;

    @Inject
    EventClient eventClient;

    @Inject
    DatastoreRepository<MinimalUser> repository;

    @Inject
    UserInfoConfig userInfoConfig;
    @Inject
    DatastoreConfig datastoreConfig;

    @RestClient
    ConfigClient configClient;

    public void updateAllUsers() {
        repository.list(DatastoreNamespace.of(datastoreConfig.namespace())).stream()
                        .filter(mu -> mu.getDeletedUserId() == null)
                        .forEach(this::updateUser);
    }

    public Long updateUser(MinimalUser mu) {
        try {
            mu.setIss(userInfoConfig.iss());
            mu.setAud(userInfoConfig.aud());

            String userInfoJson = objectMapper.writeValueAsString(mu);
            String userInfoHeader = new String(Base64.getEncoder().encode(userInfoJson.getBytes()), StandardCharsets.UTF_8);

            Tenant tenant = configClient.getTenant(mu.getEmail());

            Map<String, String> headers = new HashMap<>(Map.of(
                    TENANT_HEADER_KEY, tenant.getTenantId(),
                    SPACE_HEADER_KEY, tenant.getSpaceId(),
                    NAMESPACE_HEADER_KEY, tenant.getNameSpace(),
                    PLATFORM_HEADER_KEY, "web",
                    OS_HEADER_KEY, "desktop",
                    USER_INFO_HEADER_KEY, userInfoHeader
            ));

            eventClient.publish("userMonthly", headers, mu.getUserKey());

            return mu.getUserKey();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
