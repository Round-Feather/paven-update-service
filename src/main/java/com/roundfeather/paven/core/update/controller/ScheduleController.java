package com.roundfeather.paven.core.update.controller;

import com.roundfeather.paven.core.update.service.UserUpdateService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Slf4j
@Path("/paven/v1/update/schedule")
public class ScheduleController {

    @Inject
    UserUpdateService userService;

    @POST
    @Path("/users")
    @Operation(
            summary = "Trigger Users Update",
            description = "Trigger an event to update all users"
    )
    @APIResponse(responseCode = "204")
    public void updateUser() {
        userService.updateAllUsers();
    }
}
