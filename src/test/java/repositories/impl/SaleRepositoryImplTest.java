package repositories.impl;

import com.ppbkaf.application.config.PersistenceConfig;
import com.ppbkaf.application.entities.*;
import com.ppbkaf.application.repositories.*;
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
public class SaleRepositoryImplTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private AlcoholRepository alcoholRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    private List<Sale> sales;

    private Shop shop;
    private Kind kind;
    private User user;

    @Before
    public void before() {
        Brand brand = new Brand(1, "name1");
        kind = new Kind(1, "name1");
        shop = new Shop(1, "name1");
        Alcohol alcohol = Alcohol.builder()
                .alcoholicStrength(40.0)
                .brand(brand).id(1)
                .kind(kind).name("name1")
                .volume(700).build();
        user = User.builder()
                .login("login1")
                .password("password1")
                .isAdmin(true)
                .isActivated(false)
                .email("aaa@aa.aa")
                .role("USER_ROLE").build();

        brand.setId(this.brandRepository.add(brand));
        kind.setId(this.kindRepository.add(kind));
        shop.setId(this.shopRepository.add(shop));
        alcohol.setId(this.alcoholRepository.add(alcohol));
        user.setId(this.userRepository.add(user));

        this.sales = Arrays.asList(Sale.builder()
                        .alcohol(alcohol)
                        .id(1)
                        .price(100)
                        .rate(Rate.builder()
                                .negativeRates(1)
                                .positiveRates(2).build())
                        .shop(shop)
                        .user(user).build(),
                Sale.builder()
                        .alcohol(alcohol)
                        .id(2)
                        .price(200)
                        .rate(Rate.builder()
                                .negativeRates(3)
                                .positiveRates(4).build())
                        .shop(shop)
                        .user(user).build());
    }

    @Test
    public void getAllTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(this.sales, this.saleRepository.getAll());
    }

    @Test
    public void getTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        for (Sale item : this.sales) {
            Assert.assertEquals(item, this.saleRepository.get(item.getId()));
        }
    }

    @Test
    public void addTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(2, this.saleRepository.getAll().size());

        for (Sale item : this.sales) {
            Assert.assertEquals(item, this.saleRepository.get(item.getId()));
        }
    }

    @Test
    public void updateTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Brand brand1 = new Brand(2, "name2");
        Kind kind1 = new Kind(2, "name2");
        Shop shop1 = new Shop(2, "name2");
        Alcohol alcohol1 = Alcohol.builder()
                .alcoholicStrength(50.0)
                .brand(brand1).id(2)
                .kind(kind1).name("name2")
                .volume(700).build();

        brand1.setId(this.brandRepository.add(brand1));
        kind1.setId(this.kindRepository.add(kind1));
        shop1.setId(this.shopRepository.add(shop1));
        alcohol1.setId(this.alcoholRepository.add(alcohol1));

        this.sales.get(0).setShop(shop1);
        this.sales.get(0).setRate(Rate.builder()
                .negativeRates(5)
                .positiveRates(6).build());
        this.sales.get(0).setPrice(300);
        this.sales.get(0).setAlcohol(alcohol1);
        this.saleRepository.update(this.sales.get(0).getId(), this.sales.get(0));

        Assert.assertEquals(this.sales.get(0), this.saleRepository.get(sales.get(0).getId()));
    }

    @Test
    public void deleteTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        this.saleRepository.delete(this.sales.get(0).getId());

        Assert.assertEquals(1, this.saleRepository.getAll().size());
        Assert.assertEquals(this.sales.get(1), this.saleRepository.get(this.sales.get(1).getId()));
    }

    @Test
    public void getAllSalesByShopAndKindTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(sales, this.saleRepository
                .getAllSalesByShopAndKind(this.shop.getId(), this.kind.getId()));
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByShopAndKind(this.shop.getId() + 1, this.kind.getId()).size());
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByShopAndKind(this.shop.getId(), this.kind.getId() + 1).size());
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByShopAndKind(this.shop.getId() + 1, this.kind.getId() + 1).size());
    }

    @Test
    public void getAllSalesByShopTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(sales, this.saleRepository
                .getAllSalesByShop(this.shop.getId()));
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByShop(this.shop.getId() + 1).size());
    }

    @Test
    public void getAllSalesByKindTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(sales, this.saleRepository
                .getAllSalesByKind(this.kind.getId()));
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByKind(this.kind.getId() + 1).size());
    }

    @Test
    public void getAllSalesByUserTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        Assert.assertEquals(sales, this.saleRepository
                .getAllSalesByUser(this.user.getId()));
        Assert.assertEquals(0, this.saleRepository
                .getAllSalesByUser(this.user.getId() + 1).size());
    }

    @Test
    public void getOwnerIdTest() {
        for (Sale item : this.sales) {
            item.setId(this.saleRepository.add(item));
        }

        for (Sale item : this.sales) {
            Assert.assertEquals(item.getUser().getId(), this.saleRepository.getOwnerId(item.getId()));
        }
    }
}
