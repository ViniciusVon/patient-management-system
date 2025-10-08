import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {
    @BeforeAll
    static void stUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    // Happy Path
    @Test
    public void shouldReturnOKWithValidToken() {
        // 1. Arrange
        // 2. act
        // 3. assert

        // Arrange
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;

        // Act & Assert (O assert é a partir do then)
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        System.out.println("Generated token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        // 1. Arrange
        // 2. act
        // 3. assert

        // Arrange
        String loginPayload = """
                {
                    "email": "invalid_user@test.com",
                    "password": "wrong_password"
                }
                """;

        // Act & Assert (O assert é a partir do then)
        given()
            .contentType("application/json")
            .body(loginPayload)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(401);
    }
}
