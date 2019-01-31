package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.repositories.VerificationTokenRepository;
import config.PersistenceTestConfig;
import config.SecurityTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class,
        PersistenceTestConfig.class,
        SecurityTestConfig.class})
@ComponentScan(basePackages = "com.ppbkaf.application.repositories")
@EnableAutoConfiguration
@ActiveProfiles("test")
@Transactional
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private List<User> users = Arrays.asList(new User(1, "login1", "password1", false, false, "aaa@aa.aa", "USER_ROLE")
            , new User(2, "login2", "password2", true, true, "bbb@bb.bb", "USER_ROLE"));

    @Test
    public void getAllTest() {

        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        Assert.assertEquals(users, this.userRepository.getAll());
    }

    @Test
    public void getTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        for (User item : users) {
            Assert.assertEquals(item, this.userRepository.get(item.getId()));
        }
    }

    @Test
    public void getUserByEmailTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        for (User item : users) {
            Assert.assertEquals(item, this.userRepository.getUserByEmail(item.getEmail()));
        }
    }

    @Test
    public void addTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        Assert.assertEquals(2, this.userRepository.getAll().size());

        for (User item : users) {
            Assert.assertEquals(item, this.userRepository.get(item.getId()));
        }
    }

    @Test
    public void registerTest() {
        List<VerificationToken> tokens =
                Arrays.asList(new VerificationToken(1, "token1", LocalDateTime.now(), users.get(0))
                        , new VerificationToken(2, "token2", LocalDateTime.now(), users.get(1)));

        for (int i = 0; i < users.size(); i++) {
            users.get(i).setId(this.userRepository.register(users.get(i), tokens.get(i)));
        }

        Assert.assertEquals(2, this.userRepository.getAll().size());

        for (int i = 0; i < users.size(); i++) {
            Assert.assertEquals(users.get(i), this.userRepository.get(users.get(i).getId()));
            Assert.assertEquals(tokens.get(i), this.verificationTokenRepository.getTokenByUser(users.get(i).getId()));
        }
    }

    @Test
    public void activateTest() {
        List<VerificationToken> tokens =
                Arrays.asList(new VerificationToken(1, "token1", LocalDateTime.now(), users.get(0))
                        , new VerificationToken(2, "token2", LocalDateTime.now(), users.get(1)));

        for (int i = 0; i < users.size(); i++) {
            users.get(i).setId(this.userRepository.register(users.get(i), tokens.get(i)));
        }

        this.userRepository.activate(tokens.get(0).getToken());

        Assert.assertTrue(this.userRepository.get(users.get(0).getId()).isActivated());
        Assert.assertEquals(users.get(0).getId(), this.userRepository.get(users.get(0).getId()).getId());
    }

    @Test
    public void updateTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        users.get(0).setLogin("1login");
        users.get(0).setPassword("1password");
        users.get(0).setAdmin(true);
        this.userRepository.update(users.get(0).getId(), users.get(0));

        Assert.assertEquals(users.get(0), this.userRepository.get(users.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        this.userRepository.delete(users.get(0).getId());

        Assert.assertEquals(1, this.userRepository.getAll().size());
        Assert.assertEquals(users.get(1), this.userRepository.get(users.get(1).getId()));
    }

    @Test
    public void getUserByLoginTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        for (User item : users) {
            Assert.assertEquals(item, this.userRepository.getUserByLogin(item.getLogin()));
        }
    }

    @Test
    public void getUserIdByLoginTest() {
        for (User item : users) {
            item.setId(this.userRepository.add(item));
        }

        for (User item : users) {
            Assert.assertEquals(Integer.valueOf(item.getId()), this.userRepository.getUserIdByLogin(item.getLogin()));
        }
    }
}
