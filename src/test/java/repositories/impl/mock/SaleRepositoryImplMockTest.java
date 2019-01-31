package repositories.impl.mock;

import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.repositories.impl.SaleRepositoryImpl;
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
public class SaleRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private SaleRepositoryImpl saleRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.list()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery(anyString(), Sale.class).list()).thenThrow(Exception.class);
        this.saleRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        Sale sale = mock(Sale.class);
        when(this.session.get(Sale.class, 1)).thenReturn(sale);

        Assert.assertEquals(sale, this.saleRepositoryImpl.get(1));
        verify(this.session, times(1)).get(Sale.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(Sale.class, 1)).thenThrow(Exception.class);

        this.saleRepositoryImpl.get(1);
    }

    @Test
    public void addPassTest() {
        Sale sale = mock(Sale.class);

        when(this.session.save(sale)).thenReturn(5);

        Assert.assertEquals(5, this.saleRepositoryImpl.add(sale));
        verify(this.session, times(1)).save(any(Sale.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(Sale.class))).thenThrow(Exception.class);

        this.saleRepositoryImpl.add(mock(Sale.class));
    }

    @Test
    public void updatePassTest() {
        Sale sale = mock(Sale.class);

        when(this.session.load(Sale.class, 1)).thenReturn(sale);
        this.saleRepositoryImpl.update(1, sale);

        verify(this.session, times(1)).load(Sale.class, 1);
        verify(sale, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        Sale sale = mock(Sale.class);
        doThrow(Exception.class).when(this.session).update(any(Sale.class));
        this.saleRepositoryImpl.update(1, sale);
    }

    @Test
    public void deletePassTest() {
        Sale sale = mock(Sale.class);
        when(this.session.load(Sale.class, 1)).thenReturn(sale);
        doNothing().when(this.session).delete(sale);
        this.saleRepositoryImpl.delete(1);

        verify(this.session, times(1)).delete(sale);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        Sale sale = mock(Sale.class);
        doThrow(Exception.class).when(this.session).delete(sale);
        this.saleRepositoryImpl.delete(1);
    }

    @Test
    public void getAllSalesByShopAndKindPassTest() {
        int shopId = 1;
        int kindId = 2;
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        Query<Sale> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.setParameter("shopId", shopId)).thenReturn(query);
        when(query.setParameter("kindId", kindId)).thenReturn(query);
        when(query.getResultList()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleRepositoryImpl.getAllSalesByShopAndKind(shopId, kindId));
    }

    @Test(expected = Exception.class)
    public void getAllSalesByShopAndKindFailureTest() {
        Query<Sale> query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);
        this.saleRepositoryImpl.getAllSalesByShopAndKind(anyInt(), anyInt());
    }

    @Test
    public void getAllSalesByShopPassTest() {
        int shopId = 1;
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        Query<Sale> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.setParameter("shopId", shopId)).thenReturn(query);
        when(query.getResultList()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleRepositoryImpl.getAllSalesByShop(shopId));
    }

    @Test(expected = Exception.class)
    public void getAllSalesByShopFailureTest() {
        Query<Sale> query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);
        this.saleRepositoryImpl.getAllSalesByShop(anyInt());
    }

    @Test
    public void getAllSalesByKindPassTest() {
        int kindId = 2;
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        Query<Sale> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.setParameter("kindId", kindId)).thenReturn(query);
        when(query.getResultList()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleRepositoryImpl.getAllSalesByKind(kindId));
    }

    @Test(expected = Exception.class)
    public void getAllSalesByKindFailureTest() {
        Query<Sale> query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);
        this.saleRepositoryImpl.getAllSalesByKind(anyInt());
    }

    @Test
    public void getAllSalesByUserPassTest() {
        int userId = 2;
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        Query<Sale> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.setParameter("userId", userId)).thenReturn(query);
        when(query.getResultList()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleRepositoryImpl.getAllSalesByUser(userId));
    }

    @Test(expected = Exception.class)
    public void getAllSalesByUserFailureTest() {
        Query<Sale> query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);
        this.saleRepositoryImpl.getAllSalesByUser(anyInt());
    }

    @Test
    public void getOwnerIdPassTest() {
        int id = 3;
        Query<Integer> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1);

        Assert.assertEquals(1, this.saleRepositoryImpl.getOwnerId(id));
    }

    @Test(expected = Exception.class)
    public void getOwnerIdFailureTest() {
        Query<Sale> query = mock(Query.class);
        when(query.getSingleResult()).thenThrow(Exception.class);

        this.saleRepositoryImpl.getOwnerId(1);
    }
}
