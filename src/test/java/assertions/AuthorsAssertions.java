package assertions;

import com.api.task.automation.dataModels.AuthorDm;
import com.api.task.automation.dataModels.BookDm;
import com.api.task.automation.utils.Log;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AuthorsAssertions {

    SoftAssert softAssert = new SoftAssert();

    public void assertGetAuthorsData(int statusCode, AuthorDm[] authors) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, 200, "Invalid status Code");
        Log.info("Validating Response is not empty");
        Assert.assertTrue(authors.length != 0, "Response is empty");
    }

    public void assertAuthorData(int statusCode, AuthorDm response, AuthorDm expected) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, Integer.parseInt(expected.getExpectedResponseCode()));
        if (statusCode == 200) {
            Log.info("Validating Response Data");
            Log.info("Validating author Id");
            softAssert.assertEquals(response.getId(), expected.getId(),
                    String.format("Invalid Author Id with expected %s and Actual %s", expected.getId(), response.getId()));
            Log.info("Validating Book id");
            softAssert.assertEquals(response.getIdBook(), expected.getIdBook(),
                    String.format("Invalid book Id with expected %s and Actual %s", expected.getTitle(), response.getTitle()));
            Log.info("Validating Author's First name");
            softAssert.assertEquals(response.getFirstName(), expected.getFirstName(),
                    String.format("Invalid author first name with expected %s and Actual %s", expected.getFirstName(), response.getFirstName()));
            Log.info("Validating Author's last name");
            softAssert.assertEquals(response.getLastName(), expected.getLastName(),
                    String.format("Invalid Author's LastName with expected %s and Actual %s", expected.getLastName(), response.getLastName()));
        } else {
            Log.info("Validating Error title");
            softAssert.assertEquals(response.getTitle(), expected.getExpectedResponseTitle(),
                    String.format("Invalid response error title with expected %s and Actual %s", expected.getTitle(), response.getExpectedResponseTitle()));

        }
        softAssert.assertAll();
    }

    public void assertDeletedAuthorResponse(int statusCode, String expectedStatusCode, String response, String expected) {
        Log.info("Validating Status Code");
        Assert.assertEquals(statusCode, Integer.parseInt(expectedStatusCode));
        if (statusCode != 200) {
            Log.info("Validating Error title");
            softAssert.assertEquals(response, expected,
                    String.format("Invalid response error title with expected %s and Actual %s", expected, response));
        }
        softAssert.assertAll();
    }
}
