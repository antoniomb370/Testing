package extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentFactory {

    private static ExtentReports extent;
    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Selenium Version", "4.15.0");
            extent.setSystemInfo("Platform", "Windows 11");
        }
        return extent;

    }

}
