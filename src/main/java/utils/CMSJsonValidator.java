package utils;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;

public class CMSJsonValidator {

    public static void validate(Response response, String configFilePath, ExtentTest test) throws IOException {
        DocumentContext context = JsonPath.parse(response.asString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode config = mapper.readTree(new File(configFilePath));

        ArrayNode validations = (ArrayNode) config.get("validations");

        SoftAssert softAssert = new SoftAssert();

        for (JsonNode validation : validations) {
            String description = validation.get("description").asText();
            String jsonPath = validation.get("jsonPath").asText();
            String expected = validation.get("expected").asText();

            Object actualValue;
            try {
                actualValue = context.read(jsonPath);
            } catch (PathNotFoundException e) {
                actualValue = null;
            }

            boolean passed;
            String message;

            switch (expected) {
                case "null":
                    passed = actualValue != null;
                    message = description + " (expected null) actual: " + actualValue;
                    softAssert.assertNull(actualValue, message);
                    break;
                case "notNull":
                    passed = actualValue != null;
                    message = description + " (expected not null) actual: " + actualValue;
                    softAssert.assertNotNull(actualValue, message);
                    break;
                case "true":
                    passed = Boolean.TRUE.equals(actualValue);
                    message = description + " expected true but was " + actualValue;
                    softAssert.assertEquals(actualValue, true, message);
                    break;
                case "false":
                    passed = Boolean.FALSE.equals(actualValue);
                    message = description + " expected false but was " + actualValue;
                    softAssert.assertEquals(actualValue, false, message);
                    break;
                default:
                    if (expected.startsWith("regex:")) {
                        String regex = expected.substring(6);
                        passed = actualValue != null && actualValue.toString().matches(regex);
                        message = description + " regex: " + regex + " actual: " + actualValue;
                        softAssert.assertTrue(passed, message);
                    } else {
                        passed = expected.equals(actualValue != null ? actualValue.toString() : null);
                        message = description + " expected " + expected + " but was " + actualValue;
                        softAssert.assertEquals(actualValue != null ? actualValue.toString() : null, expected, message);
                    }
            }

            if (passed) {
                test.pass("✅ " + message);
            } else {
                test.fail("❌ " + message);
            }
        }

        softAssert.assertAll();
    }
}
