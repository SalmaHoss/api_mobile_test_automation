package mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import mobile.base.BasePage;
import org.openqa.selenium.By;

public class CollectionPickerPage extends BasePage {

    private final By createNewCollectionButton =
            By.xpath(
                    "//android.widget.TextView" +
                            "[@text='Create a new collection']"
            );

    private final By collectionNameInput =
            By.id("org.wikipedia:id/text_input");

    private final By okButton =
            By.id("android:id/button1");

    public CollectionPickerPage(AndroidDriver driver) {
        super(driver);
    }

    public void createNewCollection(String collectionName) {

        click(createNewCollectionButton);

        typeText(
                collectionNameInput,
                collectionName
        );

        click(okButton);
    }
}