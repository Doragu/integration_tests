package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class FindUserTest {

    private final String SEARCH_USER_BY_NAME_API = "/blog/user/find?searchString=Jack";

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
        String searchNewUserByNameApi = "/blog/user/find?searchString=Brian";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchNewUserByNameApi)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithREMOVEDStatusReturnsZeroUsers() {
        String searchRemovedUserByNameApi = "/blog/user/find?searchString=Edd";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchRemovedUserByNameApi)
               .then()
               .body("size()", is(0));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenFirstName() {
        String searchMultipleUserByNameApi = "/blog/user/find?searchString=John";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchMultipleUserByNameApi)
               .then()
               .body("size()", is(2));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenLastName() {
        String searchUserByLastNameApi = "/blog/user/find?searchString=Sparrow";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchUserByLastNameApi)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithProperUserStatusReturnsAllUsersWithGivenEmail() {
        String searchUserByEmailAPi = "/blog/user/find?searchString=confirm3_test_user@domain.com";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchUserByEmailAPi)
               .then()
               .body("size()", is(1));
    }

    @Test
    public void findUserWithNonExistentParamsReturnsZeroUsers() {
        String searchUserByNonExistentParamsApi = "/blog/user/find?searchString=qwertypoiuytmkiqz";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(searchUserByNonExistentParamsApi)
               .then()
               .body("size()", is(0));
    }

}
