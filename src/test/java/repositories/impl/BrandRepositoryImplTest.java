package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.repositories.BrandRepository;
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
public class BrandRepositoryImplTest {

    @Autowired
    private BrandRepository brandRepository;

    private List<Brand> brands = Arrays.asList(new Brand(1, "name1"), new Brand(2, "name2"));

    @Test
    public void getAllTest() {
        for (Brand item : brands) {
            item.setId(this.brandRepository.add(item));
        }

        Assert.assertEquals(brands, this.brandRepository.getAll());
    }

    @Test
    public void getTest() {
        for (Brand item : brands) {
            item.setId(this.brandRepository.add(item));
        }

        for (Brand item : brands) {
            Assert.assertEquals(item, this.brandRepository.get(item.getId()));
        }
    }

    @Test
    public void addTest() {
        for (Brand item : brands) {
            item.setId(this.brandRepository.add(item));
        }

        Assert.assertEquals(2, this.brandRepository.getAll().size());

        for (Brand item : brands) {
            Assert.assertEquals(item, this.brandRepository.get(item.getId()));
        }
    }

    @Test
    public void updateTest() {
        for (Brand item : brands) {
            item.setId(this.brandRepository.add(item));
        }

        brands.get(0).setName("1name");
        this.brandRepository.update(brands.get(0).getId(), brands.get(0));

        Assert.assertEquals(brands.get(0), this.brandRepository.get(brands.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (Brand item : brands) {
            item.setId(this.brandRepository.add(item));
        }

        this.brandRepository.delete(brands.get(0).getId());

        Assert.assertEquals(1, this.brandRepository.getAll().size());
        Assert.assertEquals(brands.get(1), this.brandRepository.get(brands.get(1).getId()));
    }

}
