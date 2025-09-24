package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    // Only allow access via this method
    public static ExtentReports getInstance() {
        if (extent == null) {
            // generate report under target folder
            ExtentSparkReporter spark = new ExtentSparkReporter("extent/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
