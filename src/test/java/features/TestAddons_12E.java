package features;

import cmsServices.ProductsAndServicesCMS;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.CMSJsonValidator;
import utils.ExtentManager;

import java.io.IOException;

public class TestAddons_12E {

    private ExtentReports extent;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }

    @Test
    public void testGetAddons() throws IOException {
        ExtentTest test = extent.createTest("Validate Addons CMS");

        Response resp = ProductsAndServicesCMS.getAddons();
        test.info("Fetched CMS Addons Response");

        CMSJsonValidator.validate(resp, "src/test/resources/features/addons_12E.json", test);

        test.info("CMS Addons Response:\n" + resp.asPrettyString());
    }
}
