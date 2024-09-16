package com.roundfeather.paven.core.update.api.headers;

import com.roundfeather.paven.core.update.api.utils.GCPAuthUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@ApplicationScoped
public class TenantHeaderFactory implements ClientHeadersFactory {

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> inboundHeaders, MultivaluedMap<String, String> outboundHeaders) {
        outboundHeaders.add("Authorization", String.format("Bearer %s", GCPAuthUtil.getAuthToken()));
        outboundHeaders.add("platform", "web");
        outboundHeaders.add("os", "desktop");
        return outboundHeaders;
    }
}
