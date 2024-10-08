package com.roundfeather.paven.core.update.api;

import com.roundfeather.paven.core.update.api.headers.TenantHeaderFactory;
import com.roundfeather.paven.model.config.Tenant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/paven/v1/config")
@RegisterRestClient(configKey="config-service")
@RegisterClientHeaders(TenantHeaderFactory.class)
@ApplicationScoped
@ClientHeaderParam(name = "audience", value = "quarkus.rest-client.config-service.url")
public interface ConfigClient {

    @GET
    @Path("/tenant")
    @Produces(MediaType.APPLICATION_JSON)
    Tenant getTenant(@QueryParam("email") String email);
}
