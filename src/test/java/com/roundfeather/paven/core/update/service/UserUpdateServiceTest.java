package com.roundfeather.paven.core.update.service;

import com.roundfeather.paven.core.update.api.ConfigClient;
import com.roundfeather.paven.core.update.model.MinimalUser;
import com.roundfeather.paven.model.config.Tenant;
import com.roundfeather.paven.utils.events.clients.EventClient;
import com.roundfeather.persistence.utils.datastore.DatastoreRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class UserUpdateServiceTest {

    @InjectMock
    EventClient eventClient;
    @InjectMock
    DatastoreRepository<MinimalUser> repository;
    @InjectMock
    @RestClient
    ConfigClient configClient;

    @Inject
    UserUpdateService userService;

    @Test
    void testUpdateAllUsers() {
        when(repository.list(any()))
                .thenReturn(List.of(
                        MinimalUser.builder()
                                .userId("abcde")
                                .userKey(12345L)
                                .email("a.b@c.com")
                                .deletedUserId(null)
                                .build(),
                        MinimalUser.builder()
                                .userId("abcde")
                                .userKey(12345L)
                                .email("a.b@c.com")
                                .deletedUserId("qwert")
                                .build())
                );

        when(configClient.getTenant(any()))
                .thenReturn(Tenant.builder()
                        .tenantId("t1")
                        .spaceId("s1")
                        .nameSpace("n1")
                        .build());

        userService.updateAllUsers();

        Mockito.verify(eventClient, Mockito.times(1))
                .publish(any(), any(), any());
    }
}
