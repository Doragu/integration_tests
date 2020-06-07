package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreatePostTest {

    private JSONObject jsonObj = new JSONObject().put("entry", "test post");

    @Test
    public void createPostWithProperUserStatusReturnsCreatedStatus() {
        String confirmedUserPostApi = "/blog/user/1/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_CREATED)
               .when()
               .post(confirmedUserPostApi);
    }

    @Test
    public void createPostWithNEWUserStatusReturns400Status() {
        String newUserPostApi = "/blog/user/2/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(newUserPostApi);
    }

    @Test
    public void createPostWithREMOVEDUserStatusReturns400Status() {
        String removedUserPostApi = "/blog/user/3/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(removedUserPostApi);
    }

    @Test
    public void createPostWithNonExistentUserStatusReturns400Status() {
        String nonExistentUserPostApi = "/blog/user/0/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(nonExistentUserPostApi);
    }
}
