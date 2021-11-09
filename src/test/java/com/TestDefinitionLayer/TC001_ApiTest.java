package com.TestDefinitionLayer;

import com.api.ApiActions;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Api1")
public class TC001_ApiTest<T> extends ApiActions<T> {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify refund process is a success")
    @Description("I hit post api for refund with id")
    @Story("Refund API")
    public void AllBeers() {
        RestAssured.baseURI = "https://www.docomodigital.com/";

        Response response = httpPost("/operations/{id}/refund");
        Assert.assertEquals(getStatusCode(response) /* actual value */, 200 /* expected value */,
                "Correct status code returned");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify UUID is valid or invalid")
    @Description("Get Random Beers")
    @Story("Refund API")
    public void RandomBeers() {
        RestAssured.baseURI = "https://www.docomodigital.com/";

        String basePath = "/operations/{id}/refund";
        String UUID = "d1e90d8f-11f7-41e0-92ff-235e2a85ab3b";
        Response response = httpPost(basePath.replace("{id}", UUID));
        Assert.assertEquals(getStatusCode(response) /* actual value */, 200 /* expected value */,
                "Correct status code returned and UUID is valid");
        Response response2 = httpPost(basePath.replace("{id}", "invalid-uuid"));
        Assert.assertEquals(getStatusCode(response2) /* actual value */, 400 /* expected value */,
                "Correct status code returned and UUID is invalid");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify refund process is a failure")
    @Description("Get Single Beers")
    @Story("Refund API")
    public void SingleBeer() {
        RestAssured.baseURI = "https://www.docomodigital.com/";

        String basePath = "/operations/{id}/refund";
        String UUID = "d1e90d8f";
        Response response = httpPost(basePath.replace("{id}", UUID));
        Assert.assertEquals(getStatusCode(response) /* actual value */, 400 /* expected value */,
                "Correct status code returned and refund is failure");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify concurrent refund trial status code")
    @Description("Get All Brewery")
    @Story("Refund API")
    public void Brewery() {
        RestAssured.baseURI = "https://www.docomodigital.com/";

        String basePath = "/operations/{id}/refund";
        String UUID = "d1e90d8f-11f7-41e0-92ff-235e2a85ab3b";
        Response response = httpPost(basePath.replace("{id}", UUID));

        Assert.assertEquals(getStatusCode(response) /* actual value */, 200 /* expected value */,
                "Correct status code returned on first time refund process");
        Response response2 = httpPost(basePath.replace("{id}", UUID));
        Assert.assertEquals(getStatusCode(response2) /* actual value */, 423 /* expected value */,
                "Correct status code returned and user is blocked for second time refund for same id");

    }

}
