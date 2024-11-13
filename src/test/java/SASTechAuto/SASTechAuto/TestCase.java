package SASTechAuto.SASTechAuto;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.List;

/**
 * TestCase class contains test methods for various API related to player and
 * dice functionalities. This contains basic functionality for each end points
 */

public class TestCase {
	private ApiUtils apiUtils; // Utility instance to manage API requests

	/**
	 * Setup method to initialize API utilities before running the tests. Runs once
	 * for the class, as indicated by @BeforeClass annotation.
	 */

	@BeforeClass
	public void setup() {
		apiUtils = new ApiUtils(AppConfig.getRequestSpec(), AppConfig.getResponseSpec());
	}

	/**
	 * Tests the GET request for player name. Verifies that the status code is 200
	 * (OK) and that the response indicates success.
	 */
	@Test
	public void testGetPlayerName() {
		Response response = apiUtils.getRequest("playerName");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("status"), "success");
	}

	/**
	 * Tests the PUT request to update player data without proper authorization.
	 * Verifies that the status code is 401 (Unauthorized) and that the response
	 * status indicates failure.
	 */

	@Test
	public void testPutPlayer() {
		String jsonPayload = "{\"name\": \"foo\"}";
		Response response = apiUtils.putRequest("playerName", jsonPayload);
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.jsonPath().getString("status"), "failed");
	}

	/**
	 * Tests the GET request for a specific die by ID. Verifies that the status code
	 * is 200 (OK) and that the returned die ID matches the requested ID.
	 */
	@Test
	public void testGetDieID() {
		int requestId = 1; // The die ID to request
		Response response = apiUtils.getRequest("die/" + requestId);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("data.id"), requestId);
	}

	/**
	 * Tests the PUT request to update die data without authorization. Verifies that
	 * the status code is 401 (Unauthorized) and that the response status indicates
	 * failure.
	 */
	@Test
	public void testPutDieID() {

		String jsonPayload = "{\r\n" + "\"id\": 1,\r\n" + "\"value\": 5\r\n" + "}";

		Response response = apiUtils.putRequest("die", jsonPayload);
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.jsonPath().getString("status"), "failed");

	}

	/**
	 * Tests the POST request to roll a specific die by ID. Verifies that the status
	 * code is 200 (OK) and that the returned die ID matches the requested ID.
	 */

	@Test
	public void testPostRollDie() {
		int requestId = 1;
		Response response = apiUtils.postRequest("rollDie/" + requestId, "");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("data.id"), requestId);
	}

	/**
	 * Tests the GET request for the dice collection. Verifies that the status code
	 * is 200 (OK) and that there exists a DataItem with id=4 and value=3 in the
	 * response.
	 */

	@Test
	public void testGetDice() {
		Response response = apiUtils.getRequest("dice");// Send GET request to retrieve dice collection
		Assert.assertEquals(response.getStatusCode(), 200);

		// Deserialize the response to GetDiceResponsePayload object
		GetDiceResponsePayLoad res = response.as(GetDiceResponsePayLoad.class);
		List<DataItem> data = res.getData(); // Get the list of dice items from the response
		// Check if there is a DataItem with id=4 and value=3 in the dice data
		boolean isValid = data.stream().anyMatch(item -> item.getId() == 4 && item.getValue() == 3);

		Assert.assertTrue(isValid, "Expected DataItem with id=4 and value=3");
	}

}
