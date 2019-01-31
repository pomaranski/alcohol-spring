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


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class,
        PersistenceTestConfig.class,
        SecurityTestConfig.class})
@ComponentScan(basePackages = "com.ppbkaf.application.repositories")
@EnableAutoConfiguration
@ActiveProfiles("test")
@Transactional
public class UserOpinionRepositoryImplTest {

    @Autowired
    private UserOpinionRepository userOpinionRepository;

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

    private Sale sale;
    private User user;

    @Before
    public void before() {
        Brand brand = new Brand(1, "name1");
        Kind kind = new Kind(1, "name1");
        Shop shop = new Shop(1, "name1");
        Alcohol alcohol = Alcohol.builder()
                .alcoholicStrength(40.0)
                .brand(brand).id(1)
                .kind(kind).name("name1")
                .volume(700).build();
        this.user = User.builder()
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
        this.user.setId(this.userRepository.add(this.user));

        this.sale = Sale.builder()
                .alcohol(alcohol)
                .id(1)
                .price(100)
                .rate(Rate.builder()
                        .negativeRates(1)
                        .positiveRates(2).build())
                .shop(shop)
                .user(this.user).build();

        this.sale.setId(this.saleRepository.add(this.sale));
    }

    @Test
    public void likeTest() {
        UserOpinion userOpinion = UserOpinion.builder()
                .opinion(1)
                .sale(this.sale)
                .user(this.user)
                .build();

        userOpinion.setId(this.userOpinionRepository.like(this.sale.getId(), this.user.getId()));

        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).get(0).getOpinion());
        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());

        userOpinion.setId(this.userOpinionRepository.like(this.sale.getId(), this.user.getId()));
        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());
    }

    @Test
    public void dislikeTest() {
        UserOpinion userOpinion = UserOpinion.builder()
                .opinion(-1)
                .sale(this.sale)
                .user(this.user)
                .build();

        userOpinion.setId(this.userOpinionRepository.dislike(this.sale.getId(), this.user.getId()));

        Assert.assertEquals(-1, this.userOpinionRepository.getAllByUser(this.user.getId()).get(0).getOpinion());
        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());

        userOpinion.setId(this.userOpinionRepository.like(this.sale.getId(), this.user.getId()));
        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());
    }

    @Test
    public void likeDislikeTest() {
        UserOpinion userOpinion = UserOpinion.builder()
                .opinion(1)
                .sale(this.sale)
                .user(this.user)
                .build();

        userOpinion.setId(this.userOpinionRepository.like(this.sale.getId(), this.user.getId()));
        userOpinion.setId(this.userOpinionRepository.dislike(this.sale.getId(), this.user.getId()));

        Assert.assertEquals(-1, this.userOpinionRepository.getAllByUser(this.user.getId()).get(0).getOpinion());
        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());
    }

    @Test
    public void getAllByUserTest() {
        UserOpinion userOpinion = UserOpinion.builder()
                .opinion(1)
                .sale(this.sale)
                .user(this.user)
                .build();

        Assert.assertEquals(0, this.userOpinionRepository.getAllByUser(this.user.getId()).size());

        userOpinion.setId(this.userOpinionRepository.like(this.sale.getId(), this.user.getId()));

        Assert.assertEquals(1, this.userOpinionRepository.getAllByUser(this.user.getId()).size());
    }
}
