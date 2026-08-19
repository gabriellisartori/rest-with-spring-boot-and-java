package io.github.gabriellisartori.integrationtests.swagger;

import io.github.gabriellisartori.config.TestConfigs;
import io.github.gabriellisartori.integrationtests.testcontainers.AbstractIntegrationTest;

import static io.restassured.RestAssured.given;

import static junit.framework.TestCase.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUIPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        assertTrue(content.contains("Swagger UI"));
    }

}
