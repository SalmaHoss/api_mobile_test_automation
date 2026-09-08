package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;

/**
 * Represents the 4-screen onboarding flow shown on first launch
 * (or after noReset: false clears app data). Screens 1-3 use a
 * next arrow button; screen 4 uses a text "Skip" button.
 */
public class OnboardingPage extends BasePage {

    private final By nextArrowButton = By.xpath("//android.widget.Button");
    private final By skipButton = By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.widget.Button");

    public OnboardingPage(AndroidDriver driver) {
        super(driver);
    }

    /** Skips through all onboarding screens, ending on the main app screen. */
    public void skipOnboarding() {
        for (int i = 0; i < 3; i++) {
            try {
                click(nextArrowButton);
                Thread.sleep(500); // برهة بسيطة بين كل انتقال شاشة
            } catch (Exception e) {
                break; // لو وصلنا لآخر شاشة قبل الوقت المتوقع
            }
        }
        try {
            click(skipButton);
        } catch (Exception e) {
            // الشاشة الرابعة ممكن تكون اتخطت لو العدد اختلف
        }
    }
}