package mobile.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URL;

public class BaseMobileTest {

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName(
                System.getProperty("deviceName", "Pixel 7")
        );

        options.setAppPackage("org.wikipedia");
        options.setAppActivity("org.wikipedia.main.MainActivity");
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");
        options.setNoReset(false);

        String appiumUrl = System.getProperty(
                "appiumUrl",
                "http://127.0.0.1:4723/"
        );

        driver = new AndroidDriver(
                new URL(appiumUrl),
                options
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}