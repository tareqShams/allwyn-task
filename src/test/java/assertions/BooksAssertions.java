package assertions;

import com.api.task.automation.dataModels.BookDm;
import com.api.task.automation.utils.Log;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BooksAssertions {

    SoftAssert softAssert = new SoftAssert();

    public void assertGetBooksData(int statusCode, BookDm[] books) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, 200, "Invalid status Code");
        Log.info("Validating Response is not empty");
        Assert.assertTrue(books.length != 0, "Response is empty");
    }

    public void assertBookData(int statusCode, BookDm response, BookDm expected) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, Integer.parseInt(expected.getExpectedResponseCode()));
        if (statusCode == 200) {
            Log.info("Validating Response Data");
            Log.info("Validating Book Id");
            softAssert.assertEquals(response.getId(), expected.getId(),
                    String.format("Invalid book Id with expected %s and Actual %s", expected.getId(), response.getId()));
            Log.info("Validating Book title");
            softAssert.assertEquals(response.getTitle(), expected.getTitle(),
                    String.format("Invalid book Title with expected %s and Actual %s", expected.getTitle(), response.getTitle()));
            Log.info("Validating Book Description");
            softAssert.assertEquals(response.getDescription(), expected.getDescription(),
                    String.format("Invalid book Description with expected %s and Actual %s", expected.getDescription(), response.getDescription()));
            Log.info("Validating Book Pages Count");
            softAssert.assertEquals(response.getPageCount(), expected.getPageCount(),
                    String.format("Invalid book Pages count with expected %s and Actual %s", expected.getPageCount(), response.getPageCount()));
            Log.info("Validating Book Excerpt");
            softAssert.assertEquals(response.getExcerpt(), expected.getExcerpt(),
                    String.format("Invalid book Excerpt with expected %s and Actual %s", expected.getExcerpt(), response.getExcerpt()));
            Log.info("Validating Book PublishDate");
            softAssert.assertTrue(response.getPublishDate().contains(expected.getPublishDate()),
                    String.format("Invalid book Publish Date with expected %s and Actual %s", expected.getExcerpt(), response.getExcerpt()));
        } else {
            Log.info("Validating Error title");
            softAssert.assertEquals(response.getTitle(), expected.getExpectedResponseTitle(),
                    String.format("Invalid response error title with expected %s and Actual %s", expected.getTitle(), response.getExpectedResponseTitle()));

        }
        softAssert.assertAll();
    }

    public void assertDeletedBookResponse(int statusCode,String expectedStatusCode, String response, String expected) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, Integer.parseInt(expectedStatusCode));
        if (statusCode != 200) {
            Log.info("Validating Error title");
            softAssert.assertEquals(response, expected,
                    String.format("Invalid response error title with expected %s and Actual %s",expected, response));
        }
        softAssert.assertAll();
    }
}
