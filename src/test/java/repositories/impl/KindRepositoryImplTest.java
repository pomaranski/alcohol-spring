package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.repositories.KindRepository;
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
public class KindRepositoryImplTest {

    @Autowired
    private KindRepository kindRepository;

    private List<Kind> kinds = Arrays.asList(new Kind(1, "name1"), new Kind(2, "name2"));

    @Test
    public void getAllTest() {

        for (Kind item : kinds) {
            item.setId(this.kindRepository.add(item));
        }

        Assert.assertEquals(kinds, this.kindRepository.getAll());
    }

    @Test
    public void getTest() {
        for (Kind item : kinds) {
            item.setId(this.kindRepository.add(item));
        }

        for (Kind item : kinds) {
            Assert.assertEquals(item, this.kindRepository.get(item.getId()));
        }
    }

    @Test
    public void addTest() {
        for (Kind item : kinds) {
            item.setId(this.kindRepository.add(item));
        }

        Assert.assertEquals(2, this.kindRepository.getAll().size());

        for (Kind item : kinds) {
            Assert.assertEquals(item, this.kindRepository.get(item.getId()));
        }
    }

    @Test
    public void updateTest() {
        for (Kind item : kinds) {
            item.setId(this.kindRepository.add(item));
        }

        kinds.get(0).setName("1name");
        this.kindRepository.update(kinds.get(0).getId(), kinds.get(0));

        Assert.assertEquals(kinds.get(0), this.kindRepository.get(kinds.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (Kind item : kinds) {
            item.setId(this.kindRepository.add(item));
        }

        this.kindRepository.delete(kinds.get(0).getId());

        Assert.assertEquals(1, this.kindRepository.getAll().size());
        Assert.assertEquals(kinds.get(1), this.kindRepository.get(kinds.get(1).getId()));
    }

}
