package cmsServices;

import utils.HttpClient;
import utils.PropertyReader;
import io.restassured.response.Response;

/**
 * Static Service Layer for CMS APIs.
 * Reads base URL from config.properties once.
 */
public class ProductsAndServicesCMS {

    private static final String BASE_URL;

    static {
        BASE_URL = PropertyReader.getProperty("cms.base.url");
    }

    private ProductsAndServicesCMS() {
        // private constructor to prevent instantiation
    }

    /**
     * Fetch Addons from CMS.
     */
    public static Response getAddons() {
        return HttpClient.get(BASE_URL, "/c/3b00-6fbc-40c9-891a");
    }

    // Example of adding more endpoints later:
    // public static Response getLabels() {
    //     return HttpClient.get(BASE_URL, "/cms/labels");
    // }
}
