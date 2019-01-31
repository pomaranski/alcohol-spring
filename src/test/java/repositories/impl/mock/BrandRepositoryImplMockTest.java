package repositories.impl.mock;

import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.repositories.impl.BrandRepositoryImpl;
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
public class BrandRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private BrandRepositoryImpl brandRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<Brand> brands = Arrays.asList(mock(Brand.class), mock(Brand.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Brand.class))).thenReturn(query);
        when(query.list()).thenReturn(brands);

        Assert.assertEquals(brands, this.brandRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery("FROM Brand", Brand.class).list()).thenThrow(Exception.class);
        this.brandRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        Brand brand = mock(Brand.class);
        when(this.session.get(Brand.class, 1)).thenReturn(brand);

        Assert.assertEquals(brand, this.brandRepositoryImpl.get(1));
        verify(this.session, times(1)).get(Brand.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(Brand.class, 1)).thenThrow(Exception.class);

        this.brandRepositoryImpl.get(1);
    }

    @Test
    public void addPassTest() {
        Brand brand = Brand.builder().id(1).build();
        when(this.session.save(brand)).thenReturn(brand.getId());

        Assert.assertEquals(brand.getId(), this.brandRepositoryImpl.add(brand));
        verify(this.session, times(1)).save(any(Brand.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(Brand.class))).thenThrow(Exception.class);

        this.brandRepositoryImpl.add(mock(Brand.class));
    }

    @Test
    public void updatePassTest() {
        Brand brand = mock(Brand.class);
        when(this.session.load(Brand.class, 1)).thenReturn(brand);
        this.brandRepositoryImpl.update(1, brand);

        verify(this.session, times(1)).load(Brand.class, 1);
        verify(brand, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        Brand brand = mock(Brand.class);
        doThrow(Exception.class).when(this.session).update(any(Brand.class));
        this.brandRepositoryImpl.update(1, brand);
    }

    @Test
    public void deletePassTest() {
        Brand brand = mock(Brand.class);
        when(this.session.load(Brand.class, 1)).thenReturn(brand);
        doNothing().when(this.session).delete(brand);
        this.brandRepositoryImpl.delete(1);

        verify(this.session, times(1)).delete(brand);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        Brand brand = mock(Brand.class);
        doThrow(Exception.class).when(this.session).delete(brand);
        this.brandRepositoryImpl.delete(1);
    }
}
