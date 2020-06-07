package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class FindPostTest {

    private static final String CONFIRMED_USER_POSTS_API = "/blog/user/1/post";
    private static final String CONFIRMED_USER_POSTS_API_2 = "/blog/user/4/post";
    private static final String CONFIRMED_USER_ZERO_POSTS_API = "/blog/user/5/post";
    private static final String NEW_USER_POSTS_API = "/blog/user/2/post";
    private static final String REMOVED_USER_POSTS_API = "/blog/user/3/post";

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
               .body("size()", is(3));
    }

    @Test
    public void findPostWithProperUserWithZeroPostsReturnsOKStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(CONFIRMED_USER_ZERO_POSTS_API)
               .then()
               .body("size()", is(0));
    }

    @Test
    public void findPostWithNEWStatusUserReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .get(NEW_USER_POSTS_API);
    }

    @Test
    public void findPostWithREMOVEDStatusUserReturns400Status() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .get(REMOVED_USER_POSTS_API);
    }

    @Test
    public void findPostReturnsPostWithProperAmountOfLikes() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(CONFIRMED_USER_POSTS_API_2)
               .then()
               .body("[0][\"likesCount\"]", is(1));
    }
}

