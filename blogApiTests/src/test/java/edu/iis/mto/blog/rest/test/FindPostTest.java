package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class FindPostTest {

    private static final String CONFIRMED_USER_POSTS_API = "/blog/user/1/post";

    @Test
    public void findPostWithProperUserStatusReturnsOKStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(CONFIRMED_USER_POSTS_API);
    }

    @Test
    public void findPostWithProperUserStatusReturnsAllHisPosts() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(CONFIRMED_USER_POSTS_API)
               .then()
               .body("size()", is(4));
    }

    @Test
    public void findPostWithProperUserWithZeroPostsReturnsOKStatus() {
        String confirmedUserZeroPostsApi = "/blog/user/5/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(confirmedUserZeroPostsApi)
               .then()
               .body("size()", is(0));
    }

    @Test
    public void findPostWithNEWStatusUserReturns400Status() {
        String newUserPostsApi = "/blog/user/2/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .get(newUserPostsApi);
    }

    @Test
    public void findPostWithREMOVEDStatusUserReturns400Status() {
        String removedUserPostsApi = "/blog/user/3/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .get(removedUserPostsApi);
    }

    @Test
    public void findPostReturnsPostWithProperAmountOfLikes() {
        String confirmedUserPostsApi = "/blog/user/4/post";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(confirmedUserPostsApi)
               .then()
               .body("[0][\"likesCount\"]", is(1));
    }
}

