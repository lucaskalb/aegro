package com.aegro;

import com.aegro.application.Productivity;
import com.aegro.domain.Farm;
import com.aegro.domain.Field;
import com.aegro.rest.FarmVO;
import com.aegro.rest.FieldVO;
import com.aegro.rest.GoodsVO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AegroApplicationTests {

    private static final String BASE_PATH = "/v1/farms";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreateFarm() {
        createFarm("Fazenda 1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("name", equalTo("Fazenda 1"));
    }

    @Test
    void shouldCreateField() {
        var farm = createFarm("Fazenda 2")
                .then().extract().body().as(Farm.class);

        createField(farm.getId(), "Campo 1", BigDecimal.ONE)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("goods", empty())
                .body("name", equalTo("Campo 1"));

    }

    @Test
    void shouldCreateGoods() {
        var farm = createFarm("Fazenda 3")
                .then().extract().body().as(Farm.class);

        var field = createField(farm.getId(), "Campo 2", BigDecimal.ONE)
                .then().extract().body().as(Field.class);

        createGoods(farm.getId(), field.getId(), BigDecimal.TEN)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldGetProductivity() {
        var farm = createFarm("Fazenda 4")
                .then().extract().body().as(Farm.class);

        var field1 = createField(farm.getId(), "Campo 1", BigDecimal.ONE)
                .then().extract().body().as(Field.class);
        var field2 = createField(farm.getId(), "Campo 2", BigDecimal.valueOf(2))
                .then().extract().body().as(Field.class);

        createGoods(farm.getId(), field1.getId(), BigDecimal.TEN);
        createGoods(farm.getId(), field2.getId(), BigDecimal.TEN);


        var productivity = given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_PATH.concat("/").concat(farm.getId().toString()).concat("/productivity"))
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(Productivity.class);


        assertThat(productivity.getFarmId(), equalTo(farm.getId()));
        assertThat(productivity.getFarmProductivity(), equalTo(new BigDecimal("6.67")));
        assertThat(productivity.getFields().values(), containsInAnyOrder(new BigDecimal("10.00"), new BigDecimal("5.00")));
    }

    private Response createGoods(UUID farmId, UUID fieldId, BigDecimal quantity) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .body(new GoodsVO(quantity))
                .post(BASE_PATH.concat("/").concat(farmId.toString())
                        .concat("/fields/").concat(fieldId.toString())
                        .concat("/goods"));
    }

    private Response createField(UUID farmId, String name, BigDecimal area) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .body(new FieldVO(name, area))
                .post(BASE_PATH.concat("/").concat(farmId.toString())
                        .concat("/fields"));
    }

    private Response createFarm(String name) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .body(new FarmVO(name))
                .post(BASE_PATH);
    }

}
