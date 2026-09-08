package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CollectionDetailPage extends BasePage {

    private final By gotItButton =
            By.id("org.wikipedia:id/buttonView");

    public CollectionDetailPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean containsArticle(String articleTitle) {

        dismissShareTooltip();

        By article = By.xpath(
                String.format(
                        "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='%s']",
                        articleTitle
                )
        );

        try {

            WebElement articleElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(article)
            );

            System.out.println(
                    "Article found in collection: "
                            + articleElement.getText()
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Article was NOT found in collection: "
                            + articleTitle
            );

            System.out.println("=== CURRENT PAGE SOURCE ===");
            System.out.println(driver.getPageSource());
            System.out.println("=== END PAGE SOURCE ===");

            return false;
        }
    }

    private void dismissShareTooltip() {

        try {

            WebElement gotIt = wait.until(
                    ExpectedConditions.elementToBeClickable(gotItButton)
            );

            System.out.println(
                    "Share tooltip found. Clicking Got it..."
            );

            gotIt.click();

            System.out.println(
                    "Share tooltip dismissed."
            );

        } catch (Exception e) {

            System.out.println(
                    "Share tooltip did not appear. Continuing..."
            );
        }
    }
}