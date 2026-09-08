package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;

public class CollectionsPage extends BasePage {

    private final By savedTab =
            By.id("org.wikipedia:id/nav_tab_reading_lists");

    private final By collectionsTab =
            By.xpath("//*[@text='Collections']");

    private final By searchCollectionsIcon =
            By.id("org.wikipedia:id/menu_search_lists");

    private final By searchCollectionsInput =
            By.id("org.wikipedia:id/search_src_text");

    public CollectionsPage(AndroidDriver driver) {
        super(driver);
    }

    public void openCollections() {

        // Go back until Saved section is available
        for (int i = 0; i < 5; i++) {

            if (!driver.findElements(savedTab).isEmpty()) {
                break;
            }

            driver.navigate().back();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (driver.findElements(savedTab).isEmpty()) {
            throw new RuntimeException(
                    "Saved tab was not found."
            );
        }

        // Open Saved
        click(savedTab);

        // Open Collections
        click(collectionsTab);
    }

    public void searchForCollection(String collectionName) {

        click(searchCollectionsIcon);

        typeText(
                searchCollectionsInput,
                collectionName
        );
    }

    public void openCollection(String collectionName) {

        By collectionItem = By.xpath(
                String.format(
                        "//*[@text='%s']",
                        collectionName
                )
        );

        click(collectionItem);
    }
}