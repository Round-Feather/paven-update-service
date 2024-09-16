package com.roundfeather.paven.core.update.api.headers;

import com.roundfeather.paven.core.update.api.utils.GCPAuthUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@QuarkusTest
class TenantHeaderFactoryTest {

    @Inject
    TenantHeaderFactory headerFactory;

    @Test
    void updateTest() {
        MultivaluedMap<String, String> inboundHeaders = new MultivaluedHashMap<>();
        MultivaluedMap<String, String> outboundHeaders = new MultivaluedHashMap<>();

        MultivaluedMap<String, String> expectedHeaders = new MultivaluedHashMap<>();
        expectedHeaders.add("Authorization", "Bearer token");
        expectedHeaders.add("platform", "web");
        expectedHeaders.add("os", "desktop");

        MockedStatic<GCPAuthUtil> authUtilMocked = mockStatic(GCPAuthUtil.class);
        authUtilMocked.when(GCPAuthUtil::getAuthToken).thenReturn("token");

        MultivaluedMap<String, String> actualHeaders = headerFactory.update(inboundHeaders, outboundHeaders);

        assertEquals(expectedHeaders, actualHeaders);
    }
}
