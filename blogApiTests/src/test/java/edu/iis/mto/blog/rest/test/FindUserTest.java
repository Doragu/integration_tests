package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class FindUserTest {

    private final String SEARCH_USER_BY_NAME_API = "/blog/user/find?searchString=Jack";
    private final String SEARCH_REMOVED_USER_BY_NAME_API = "/blog/user/find?searchString=Edd";
    private final String SEARCH_NEW_USER_BY_NAME_API = "/blog/user/find?searchString=Brian";
    private final String SEARCH_MULTIPLE_USER_BY_NAME_API = "/blog/user/find?searchString=John";
    private final String SEARCH_USER_BY_EMAIL_API = "/blog/user/find?searchString=confirm3_test_user@domain.com";
    private final String SEARCH_USER_BY_LAST_NAME_API = "/blog/user/find?searchString=Sparrow";
    private final String SEARCH_USER_BY_NON_EXISTENT_PARAMS_API = "/blog/user/find?searchString=qwertypoiuytmkiqz";

    @Test
    public void findUserWithProperUserStatusReturnsOKStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_USER_BY_NAME_API);
    }

    @Test
    public void findUserWithProperUserStatusReturnsProperUser() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_USER_BY_NAME_API)
               .then()
               .body("[0][\"lastName\"]", is("Sparrow"))
               .body("[0][\"email\"]", is("confirm2_test_user@domain.com"))
               .body("[0][\"id\"]", is(4));
    }

    @Test
    public void findUserWithNEWStatusAllNEWUsersWithGivenFirstName() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_NEW_USER_BY_NAME_API)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithREMOVEDStatusReturnsZeroUsers() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_REMOVED_USER_BY_NAME_API)
               .then()
               .body("size()", is(0));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenFirstName() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_MULTIPLE_USER_BY_NAME_API)
               .then()
               .body("size()", is(2));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenLastName() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_USER_BY_LAST_NAME_API)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenEmail() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_USER_BY_EMAIL_API)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithNonExistentParamsReturnsZeroUsers() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(SEARCH_USER_BY_NON_EXISTENT_PARAMS_API)
               .then()
               .body("size()", is(0));
    }

}
