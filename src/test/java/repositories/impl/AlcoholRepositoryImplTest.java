package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.repositories.AlcoholRepository;
import com.ppbkaf.application.repositories.BrandRepository;
import com.ppbkaf.application.repositories.KindRepository;
import config.PersistenceTestConfig;
import config.SecurityTestConfig;
import org.junit.Assert;
import org.junit.Before;
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
public class AlcoholRepositoryImplTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    private AlcoholRepository alcoholRepository;

    private List<Alcohol> alcohols;

    private Kind kind;

    @Before
    public void before() {
        Brand brand = new Brand(1, "name1");
        this.kind = new Kind(1, "name1");
        this.alcohols = Arrays.asList(
                Alcohol.builder()
                        .alcoholicStrength(40.0)
                        .brand(brand).id(1)
                        .kind(this.kind).name("name1")
                        .volume(700).build(),
                Alcohol.builder()
                        .alcoholicStrength(5.0)
                        .brand(brand).id(2)
                        .kind(this.kind).name("name2")
                        .volume(500).build()
        );

        brand.setId(this.brandRepository.add(brand));
        kind.setId(this.kindRepository.add(this.kind));
    }

    @Test
    public void getAllTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        Assert.assertEquals(this.alcohols, this.alcoholRepository.getAll());
    }

    @Test
    public void getTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        for (Alcohol item : this.alcohols) {
            Assert.assertEquals(item, this.alcoholRepository.get(item.getId()));
        }
    }

    @Test
    public void addTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        Assert.assertEquals(2, this.alcoholRepository.getAll().size());

        for (Alcohol item : this.alcohols) {
            Assert.assertEquals(item, this.alcoholRepository.get(item.getId()));
        }
    }

    @Test
    public void updateTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        Brand brand1 = new Brand(2, "name2");
        Kind kind1 = new Kind(2, "name2");

        brand1.setId(this.brandRepository.add(brand1));
        kind1.setId(this.kindRepository.add(kind1));

        this.alcohols.get(0).setName("1name");
        this.alcohols.get(0).setKind(kind1);
        this.alcohols.get(0).setVolume(100);
        this.alcohols.get(0).setBrand(brand1);
        this.alcohols.get(0).setAlcoholicStrength(12);
        this.alcoholRepository.update(this.alcohols.get(0).getId(), this.alcohols.get(0));

        Assert.assertEquals(this.alcohols.get(0), this.alcoholRepository.get(alcohols.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        this.alcoholRepository.delete(this.alcohols.get(0).getId());

        Assert.assertEquals(1, this.alcoholRepository.getAll().size());
        Assert.assertEquals(this.alcohols.get(1), this.alcoholRepository.get(this.alcohols.get(1).getId()));
    }

    @Test
    public void getAllSalesByShopAndKindTest() {
        for (Alcohol item : this.alcohols) {
            item.setId(this.alcoholRepository.add(item));
        }

        Assert.assertEquals(alcohols, this.alcoholRepository
                .getAllAlcoholsByKind(this.kind.getId()));
        Assert.assertEquals(0, this.alcoholRepository
                .getAllAlcoholsByKind(this.kind.getId() + 1).size());
    }

}
