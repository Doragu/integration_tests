package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        blogPost = new BlogPost();
        blogPost.setEntry("test");
        blogPost.setUser(user);

        likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);

        entityManager.persist(user);
        entityManager.persist(blogPost);
    }

    @Test
    public void shouldFindNoLikePostsIfRepositoryIsEmpty() {
        List<LikePost> likePosts = repository.findAll();

        assertThat(likePosts, hasSize(0));
    }

    @Test
    public void shouldFindOneLikePostIfRepositoryContainsOneLikePostEntity() {
        LikePost persistedLikePost = entityManager.persist(likePost);

        List<LikePost> likePosts = repository.findAll();
        assertThat(likePosts, hasSize(1));
        assertThat(likePosts.get(0).getPost(), equalTo(persistedLikePost.getPost()));

    }

    @Test
    public void shouldStoreANewLikePost() {
        LikePost persistedLikePost = repository.save(likePost);

        assertThat(persistedLikePost.getPost(), notNullValue());
    }

    @Test
    public void shouldFindLikePostByFindByUserAndPostWithBothParameters() {
        repository.save(likePost);

        Optional<LikePost> result = repository.findByUserAndPost(user, blogPost);
        assertTrue(result.isPresent());
    }

    @Test
    public void shouldNotFindLikePostByFindByUserAndPostWithOneParameter() {
        repository.save(likePost);

        Optional<LikePost> result = repository.findByUserAndPost(null, blogPost);
        assertFalse(result.isPresent());

        result = repository.findByUserAndPost(user, null);
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldNotFindLikePostByFindByUserAndPostWithZeroParameters() {
        repository.save(likePost);

        Optional<LikePost> result = repository.findByUserAndPost(null, null);
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldFindByUserAndPostNotThrowExceptionWithNullParameters() {
        assertDoesNotThrow(() -> repository.findByUserAndPost(null, null));
        assertDoesNotThrow(() -> repository.findByUserAndPost(user, null));
        assertDoesNotThrow(() -> repository.findByUserAndPost(null, blogPost));
    }

    @Test
    public void checkIfChangingUserForRepositoryItemsWork() {
        User tempUser = new User();
        tempUser.setFirstName("Adam");
        tempUser.setAccountStatus(AccountStatus.NEW);
        tempUser.setEmail("Morga@mail.com");
        entityManager.persist(tempUser);

        repository.save(likePost);
        LikePost tempLikePost = repository.findAll().get(0);
        tempLikePost.setUser(tempUser);

        assertThat(tempUser.getLastName(), equalTo(repository.findAll().get(0).getUser().getLastName()));
    }

    @Test
    public void checkIfChangingBlogPostForRepositoryItemsWork() {
        BlogPost tempPost = new BlogPost();
        tempPost.setEntry("testtestest");
        tempPost.setUser(user);
        entityManager.persist(tempPost);

        repository.save(likePost);
        LikePost tempLikePost = repository.findAll().get(0);
        tempLikePost.setPost(tempPost);

        assertThat(tempPost.getEntry(), equalTo(repository.findAll().get(0).getPost().getEntry()));
    }
}
