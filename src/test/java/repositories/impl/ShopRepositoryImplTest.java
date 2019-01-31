package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.repositories.ShopRepository;
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
public class ShopRepositoryImplTest {

    @Autowired
    private ShopRepository shopRepository;

    private List<Shop> shops = Arrays.asList(new Shop(1, "name1"), new Shop(2, "name2"));

    @Test
    public void getAllTest() {
        for (Shop item : shops) {
            item.setId(this.shopRepository.add(item));
        }

        Assert.assertEquals(shops, this.shopRepository.getAll());
    }

    @Test
    public void getTest() {
        for (Shop item : shops) {
            item.setId(this.shopRepository.add(item));
        }

        for (Shop item : shops) {
            Assert.assertEquals(item, this.shopRepository.get(item.getId()));
        }
    }

    @Test
    public void addTest() {
        for (Shop item : shops) {
            item.setId(this.shopRepository.add(item));
        }

        Assert.assertEquals(2, this.shopRepository.getAll().size());

        for (Shop item : shops) {
            Assert.assertEquals(item, this.shopRepository.get(item.getId()));
        }
    }

    @Test
    public void updateTest() {
        for (Shop item : shops) {
            item.setId(this.shopRepository.add(item));
        }

        shops.get(0).setName("1name");
        this.shopRepository.update(shops.get(0).getId(), shops.get(0));

        Assert.assertEquals(shops.get(0), this.shopRepository.get(shops.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (Shop item : shops) {
            item.setId(this.shopRepository.add(item));
        }

        this.shopRepository.delete(shops.get(0).getId());

        Assert.assertEquals(1, this.shopRepository.getAll().size());
        Assert.assertEquals(shops.get(1), this.shopRepository.get(shops.get(1).getId()));
    }

}
