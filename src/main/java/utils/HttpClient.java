package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Simple static HTTP client wrapper for RestAssured.
 */
public class HttpClient {

    private HttpClient() {
        // private constructor to prevent instantiation
    }

    /**
     * Static GET request without auth.
     *
     * @param baseUrl   Base URL of the API (e.g. https://dummyjson.com)
     * @param endpoint  Relative endpoint (e.g. "/c/c5e6-2aa5-4a71-a8ca")
     */
    public static Response get(String baseUrl, String endpoint) {
        // ensure no trailing slash duplication
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .when()
                .get(baseUrl + endpoint)
                .then()
                .extract()
                .response();
    }
}
