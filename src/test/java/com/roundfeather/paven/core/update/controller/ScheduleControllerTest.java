package com.roundfeather.paven.core.update.controller;

import com.roundfeather.paven.core.update.service.UserUpdateService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ScheduleControllerTest {

    @Inject
    UserUpdateService userService;

    @Test
    void updateUserTest() {
        io.restassured.response.Response response = given()
                .when()
                .post("/paven/v1/update/schedule/users");
        response.then()
                .statusCode(204);
    }
}
