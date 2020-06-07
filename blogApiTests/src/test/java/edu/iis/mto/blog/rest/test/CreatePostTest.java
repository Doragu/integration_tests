package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreatePostTest {

    private static final String CONFIRMED_USER_POST_API = "/blog/user/1/post";
    private static final String NEW_USER_POST_API = "/blog/user/2/post";
    private static final String REMOVED_USER_POST_API = "/blog/user/3/post";
    private static final String NON_EXISTENT_USER_POST_API = "/blog/user/0/post";


    private JSONObject jsonObj = new JSONObject().put("entry", "test post");

    @Test
    public void createPostWithProperUserStatusReturnsCreatedStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_CREATED)
               .when()
               .post(CONFIRMED_USER_POST_API);
    }

    @Test
    public void createPostWithNEWUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(NEW_USER_POST_API);
    }

    @Test
    public void createPostWithREMOVEDUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(REMOVED_USER_POST_API);
    }

    @Test
    public void createPostWithNonExistentUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(NON_EXISTENT_USER_POST_API);
    }
}
