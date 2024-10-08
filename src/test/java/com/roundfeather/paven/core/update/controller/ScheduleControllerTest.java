package com.roundfeather.paven.core.update.controller;

import com.roundfeather.paven.core.update.service.UserUpdateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ScheduleControllerTest {

    @InjectMock
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
