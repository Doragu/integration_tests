package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user, user2;
    private final String NON_EXISTENT_STRING = "nonExistentString";

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        user2 = new User();
        user2.setFirstName("Kamil");
        user2.setLastName("Ladny");
        user2.setEmail("kamil@notadomain.com");
        user2.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void shouldFindUserByFirstName() {
        User persistedUser = repository.save(user);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", NON_EXISTENT_STRING, NON_EXISTENT_STRING);

        assertThat(users, hasSize(1));
        assertEquals(users.get(0).getId(), persistedUser.getId());

    }

    @Test
    public void shouldFindUserByFirstNameAndEmail() {
        User persistedUser = repository.save(user);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", NON_EXISTENT_STRING, "john@domain.com");

        assertThat(users, hasSize(1));
        assertEquals(users.get(0).getId(), persistedUser.getId());

    }

    @Test
    public void shouldFindUserByFirstNameAndLastNameAndEmail() {
        User persistedUser = repository.save(user);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", "Kowalski", "john@domain.com");

        assertThat(users, hasSize(1));
        assertEquals(users.get(0).getId(), persistedUser.getId());

    }

    @Test
    public void shouldFindAllUsersWithEmptyStringParameters() {
        repository.save(user);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("", "", "");

        assertThat(users, hasSize(2));

    }

    @Test
    public void shouldNotFindAnyUsersWithAllNonExistentParameters() {
        repository.save(user);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(NON_EXISTENT_STRING, NON_EXISTENT_STRING, NON_EXISTENT_STRING);

        assertThat(users, hasSize(0));

    }

}
