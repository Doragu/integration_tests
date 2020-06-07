package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class LikePostTest {

    private static final String CONFIRMED_USER_LIKE_POST_API = "/blog/user/4/like/1";
    private static final String OWNER_USER_LIKE_POST_API = "/blog/user/1/like/1";
    private static final String NEW_USER_LIKE_POST_API = "/blog/user/2/like/1";
    private static final String REMOVED_USER_LIKE_POST_API = "/blog/user/3/like/1";
    private static final String NON_EXISTENT_USER_LIKE_POST_API = "/blog/user/0/like/1";
    private static final String POST_API = "/blog/post/1";


    @Test
    public void likePostWithProperUserStatusReturnsOKStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(CONFIRMED_USER_LIKE_POST_API);
    }

    @Test
    public void likePostWithOwnerOfPostUserReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(OWNER_USER_LIKE_POST_API);
    }

    @Test
    public void likePostWithNEWUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(NEW_USER_LIKE_POST_API);
    }

    @Test
    public void likePostWithREMOVEDUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(REMOVED_USER_LIKE_POST_API);
    }

    @Test
    public void likePostWithNonExistentUserStatusReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(NON_EXISTENT_USER_LIKE_POST_API);
    }

    @Test
    public void likePostTwiceShouldNotChangeItsState() {
        given().accept(ContentType.JSON)
              .header("Content-Type", "application/json;charset=UTF-8")
              .expect()
              .log()
              .all()
              .statusCode(HttpStatus.SC_OK)
              .when()
              .post(CONFIRMED_USER_LIKE_POST_API);

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(CONFIRMED_USER_LIKE_POST_API);

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(POST_API)
               .then()
               .body("likesCount", is(1));
    }
}
