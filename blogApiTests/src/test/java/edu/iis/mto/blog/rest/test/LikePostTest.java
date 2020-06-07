package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class LikePostTest {

    private static final String CONFIRMED_USER_LIKE_POST_API = "/blog/user/4/like/1";

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
    public void likePostTwiceShouldNotChangeItsState() {
        String postApi = "/blog/post/1";

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
               .get(postApi)
               .then()
               .body("likesCount", is(1));
    }

    @Test
    public void likePostWithOwnerOfPostUserReturns400Status() {
        String ownerUserListPostApi = "/blog/user/1/like/1";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(ownerUserListPostApi);
    }

    @Test
    public void likePostWithNEWUserStatusReturns400Status() {
        String newUserLikePostApi = "/blog/user/2/like/1";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(newUserLikePostApi);
    }

    @Test
    public void likePostWithREMOVEDUserStatusReturns400Status() {
        String removedUserLikePostApi = "/blog/user/3/like/1";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(removedUserLikePostApi);
    }

    @Test
    public void likePostWithNonExistentUserStatusReturns400Status() {
        String nonExistentuserLikePostApi = "/blog/user/0/like/1";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(nonExistentuserLikePostApi);
    }
}
