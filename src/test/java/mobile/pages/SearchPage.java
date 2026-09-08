package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends BasePage {

    private final By searchEntryPoint = By.id("org.wikipedia:id/nav_tab_search");
    private final By searchCard = By.id("org.wikipedia:id/search_card");
    private final By searchInput = By.id("org.wikipedia:id/search_src_text");
    private final By searchResultsContainer = By.id("org.wikipedia:id/fragment_search_results");

    public SearchPage(AndroidDriver driver) {
        super(driver);
    }

    public void openSearch() {
        click(searchEntryPoint);
        dismissSystemPopupIfPresent();
        click(searchCard);
    }

    public void searchFor(String query) {
        typeText(searchInput, query);
        waitForVisible(searchResultsContainer);
    }

    /** Opens the first search result — the item at index 0 in the results list. */
    public void openFirstResult() {
        WebElement resultsContainer = waitForVisible(searchResultsContainer);
        List<WebElement> clickableRows = resultsContainer.findElements(
                By.xpath(".//*[@clickable='true']"));
        clickableRows.get(0).click();
    }

    private void dismissSystemPopupIfPresent() {
        try {
            Thread.sleep(1000);
            if (driver.findElements(By.xpath("//*[@text='A Faster way to Search']")).size() > 0) {
                driver.navigate().back();
            }
        } catch (Exception e) {
        }
    }
}