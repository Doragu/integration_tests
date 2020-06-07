package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @MockBean
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogDataMapper dataMapper;

    @Autowired
    private BlogService blogService;

    @Captor
    private ArgumentCaptor<User> userParam;

    @Captor
    private ArgumentCaptor<BlogPost> blogParam;

    private BlogPost blogPost;
    private User user, otherUser;

    @Before
    public void setUp() {
        Long userID = 5L;
        Long otherUserID = 9L;
        Long blogPostID = 3L;

        user = new User();
        user.setId(userID);

        otherUser = new User();
        otherUser.setId(otherUserID);

        blogPost = new BlogPost();
        blogPost.setId(blogPostID);
        blogPost.setUser(otherUser);

        when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        when(userRepository.findById(otherUserID)).thenReturn(Optional.of(otherUser));
        when(blogPostRepository.findById(blogPostID)).thenReturn(Optional.of(blogPost));
    }

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void addingLikePostShouldNotThrowForUsersWithStatusCONFIRMED() {
        user.setAccountStatus(AccountStatus.CONFIRMED);
        assertDoesNotThrow(() -> blogService.addLikeToPost(user.getId(), blogPost.getId()));
    }

    @Test
    public void addingLikePostShouldThrowForUsersWithStatusNotCONFIRMED() {
        user.setAccountStatus(AccountStatus.NEW);
        assertThrows(DomainError.class, () -> blogService.addLikeToPost(user.getId(), blogPost.getId()));

        user.setAccountStatus(AccountStatus.REMOVED);
        assertThrows(DomainError.class, () -> blogService.addLikeToPost(user.getId(), blogPost.getId()));
    }

}
