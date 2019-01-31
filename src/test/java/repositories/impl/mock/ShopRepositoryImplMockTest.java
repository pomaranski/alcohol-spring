package repositories.impl.mock;

import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.repositories.impl.ShopRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShopRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private ShopRepositoryImpl shopRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<Shop> shops = Arrays.asList(mock(Shop.class), mock(Shop.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Shop.class))).thenReturn(query);
        when(query.list()).thenReturn(shops);

        Assert.assertEquals(shops, this.shopRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery("FROM Shop", Shop.class).list()).thenThrow(Exception.class);
        this.shopRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        Shop shop = mock(Shop.class);
        when(this.session.get(Shop.class, 1)).thenReturn(shop);

        Assert.assertEquals(shop, this.shopRepositoryImpl.get(1));
        verify(this.session, times(1)).get(Shop.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(Shop.class, 1)).thenThrow(Exception.class);

        this.shopRepositoryImpl.get(1);
    }

    @Test
    public void addPassTest() {
        Shop shop = Shop.builder().id(1).build();
        when(this.session.save(shop)).thenReturn(shop.getId());

        Assert.assertEquals(shop.getId(), this.shopRepositoryImpl.add(shop));
        verify(this.session, times(1)).save(any(Shop.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(Shop.class))).thenThrow(Exception.class);

        this.shopRepositoryImpl.add(mock(Shop.class));
    }

    @Test
    public void updatePassTest() {
        Shop shop = mock(Shop.class);
        when(this.session.load(Shop.class, 1)).thenReturn(shop);
        this.shopRepositoryImpl.update(1, shop);

        verify(this.session, times(1)).load(Shop.class, 1);
        verify(shop, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        Shop shop = mock(Shop.class);
        doThrow(Exception.class).when(this.session).update(any(Shop.class));
        this.shopRepositoryImpl.update(1, shop);
    }

    @Test
    public void deletePassTest() {
        Shop shop = mock(Shop.class);
        when(this.session.load(Shop.class, 1)).thenReturn(shop);
        this.shopRepositoryImpl.delete(1);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        Shop shop = mock(Shop.class);
        doThrow(Exception.class).when(this.session).delete(shop);
        this.shopRepositoryImpl.delete(1);
    }
}
