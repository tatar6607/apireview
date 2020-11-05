package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetRequest01 extends TestBase {

    @Test
    public void get01() {
        Response response = given().
                                accept(ContentType.JSON).
                                spec(spec01).
                            when().
                                get("/booking");
        response.prettyPrint();

        // API de ilk olarak assert edilecek sey ==> Statuscode dur.
        // status code ==> 200 ==> hersey yolunda //  400 ==> endpointinde hata var // 500 ==> serverde hata var.
        response.
                then().
                assertThat().
                statusCode(200);

        JsonPath json = response.jsonPath();
        List<Integer> allActualBookingid= json.getList("bookingid");
        System.out.println("Tum bookingidler: " + allActualBookingid);

        List<Integer> expectedBookingid = new ArrayList<>();
        expectedBookingid.add(1);
        expectedBookingid.add(11);
        expectedBookingid.add(4);
        System.out.println("Expected bookingid: " + expectedBookingid);

        // Verilen Bookingid lerin actaul listte bulundugunu verify edelim.
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(allActualBookingid.containsAll(expectedBookingid));
        softAssert.assertAll();
    }
}
