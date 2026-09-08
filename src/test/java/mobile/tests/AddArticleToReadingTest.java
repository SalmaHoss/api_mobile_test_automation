package mobile.tests;

import mobile.base.BaseMobileTest;
import mobile.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddArticleToReadingTest extends BaseMobileTest {

    private static final String ARTICLE_TITLE =
            "Artificial intelligence";

    private static final String COLLECTION_NAME =
            "AI Reading List";

    @Test
    public void saveArticleToNewCollection_isReflectedInCollections() {

        // 1. Skip onboarding
        OnboardingPage onboardingPage =
                new OnboardingPage(driver);

        onboardingPage.skipOnboarding();

        // 2. Search for article
        SearchPage searchPage =
                new SearchPage(driver);

        searchPage.openSearch();
        searchPage.searchFor(ARTICLE_TITLE);

        // 3. Open article
        searchPage.openFirstResult();

        ArticlePage articlePage =
                new ArticlePage(driver);

        // 4. Verify article is displayed
        Assert.assertTrue(
                articlePage.isArticleDisplayed(),
                "Article page should be displayed"
        );

        // 5. Save article
        articlePage.tapSave();

        // 6. Create new collection
        CollectionPickerPage collectionPickerPage =
                new CollectionPickerPage(driver);

        collectionPickerPage.createNewCollection(
                COLLECTION_NAME
        );

        // 7. Navigate to Saved -> Collections
        CollectionsPage collectionsPage =
                new CollectionsPage(driver);

        collectionsPage.openCollections();

        // 8. Search for the created collection
        collectionsPage.searchForCollection(
                COLLECTION_NAME
        );

        // 9. Open the collection
        collectionsPage.openCollection(
                COLLECTION_NAME
        );

        // 10. Verify saved article
        CollectionDetailPage collectionDetailPage =
                new CollectionDetailPage(driver);

        Assert.assertTrue(
                collectionDetailPage.containsArticle(
                        ARTICLE_TITLE
                ),
                "Saved article should be displayed in the collection"
        );
    }
}