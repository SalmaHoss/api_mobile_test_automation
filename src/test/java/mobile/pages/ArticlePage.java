package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ArticlePage extends BasePage {

    private final By articleTitle =
            By.xpath("//android.webkit.WebView//android.widget.TextView[@text='Artificial intelligence']");
    private final By saveButton =
            By.id("org.wikipedia:id/page_save");

    private final By promoCloseButton =
            By.id("org.wikipedia:id/closeButton");

    // تأكد من الـ id الحقيقي عن طريق Appium Inspector قبل الاعتماد عليه
    private final By tooltipGotItButton =
            By.id("org.wikipedia:id/got_it_button");


    public ArticlePage(AndroidDriver driver) {
        super(driver);
    }


    private void dismissPromoOverlayIfPresent() {

        System.out.println("Checking for promo overlay...");

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            WebElement closeButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            promoCloseButton
                    )
            );

            System.out.println("Promo overlay found. Closing it...");

            closeButton.click();

            System.out.println("Promo overlay closed.");

        } catch (Exception e) {

            System.out.println(
                    "Promo overlay did not appear. Continuing..."
            );
        }
    }


    private void dismissTooltipIfPresent() {

        System.out.println("Checking for onboarding tooltip...");

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(5));

        try {

            WebElement gotItButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            tooltipGotItButton
                    )
            );

            System.out.println("Tooltip found. Dismissing it...");

            gotItButton.click();

            System.out.println("Tooltip dismissed.");

        } catch (Exception e) {

            System.out.println(
                    "Tooltip did not appear. Continuing..."
            );
        }
    }


    /**
     * يبحث عن أول context يحتوي على "WEBVIEW" وينتقل إليه.
     * يعيد true إذا نجح التبديل، false إذا لم يجد WebView (نبقى NATIVE_APP).
     */
    private boolean switchToWebViewContext() {

        try {

            Set<String> contexts = driver.getContextHandles();

            System.out.println("Available contexts: " + contexts);

            for (String context : contexts) {
                if (context.contains("WEBVIEW")) {
                    driver.context(context);
                    System.out.println("Switched to context: " + context);
                    return true;
                }
            }

            System.out.println("No WEBVIEW context found. Staying in NATIVE_APP.");
            return false;

        } catch (Exception e) {

            System.out.println(
                    "Failed to switch context: " + e.getMessage()
            );
            return false;
        }
    }


    private void switchBackToNativeContext() {

        try {
            driver.context("NATIVE_APP");
        } catch (Exception e) {
            System.out.println(
                    "Failed to switch back to NATIVE_APP: " + e.getMessage()
            );
        }
    }


    public boolean isArticleDisplayed() {

        // النوافذ المنبثقة (promo + onboarding tooltip) قد تظهر قبل قراءة العنوان
        dismissPromoOverlayIfPresent();
        dismissTooltipIfPresent();

        boolean switchedToWebView = switchToWebViewContext();

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.webkit.WebView//android.widget.TextView[@text='Artificial intelligence']"))).isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "=== PAGE SOURCE AT ARTICLE CHECK FAILURE ==="
            );

            System.out.println(
                    driver.getPageSource()
            );

            System.out.println(
                    "=== END PAGE SOURCE ==="
            );

            return false;

        } finally {

            if (switchedToWebView) {
                switchBackToNativeContext();
            }
        }
    }


    public void tapSave() {

        click(saveButton);
    }
}