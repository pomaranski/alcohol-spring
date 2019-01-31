package repositories.impl.mock;

import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.repositories.impl.AlcoholRepositoryImpl;
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
public class AlcoholRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private AlcoholRepositoryImpl alcoholRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Alcohol.class))).thenReturn(query);
        when(query.list()).thenReturn(alcohols);

        Assert.assertEquals(alcohols, this.alcoholRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery("FROM Alcohol", Alcohol.class).list()).thenThrow(Exception.class);
        this.alcoholRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        Alcohol alcohol = mock(Alcohol.class);
        when(this.session.get(Alcohol.class, 1)).thenReturn(alcohol);

        Assert.assertEquals(alcohol, this.alcoholRepositoryImpl.get(1));
        verify(this.session, times(1)).get(Alcohol.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(Alcohol.class, 1)).thenThrow(Exception.class);

        this.alcoholRepositoryImpl.get(1);
    }

    @Test
    public void addPassTest() {
        Alcohol alcohol = mock(Alcohol.class);

        when(this.session.save(alcohol)).thenReturn(5);

        Assert.assertEquals(5, this.alcoholRepositoryImpl.add(alcohol));
        verify(this.session, times(1)).save(any(Alcohol.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(Alcohol.class))).thenThrow(new Exception());

        this.alcoholRepositoryImpl.add(mock(Alcohol.class));
    }

    @Test
    public void updatePassTest() {
        Alcohol alcohol = mock(Alcohol.class);

        when(this.session.load(Alcohol.class, 1)).thenReturn(alcohol);

        this.alcoholRepositoryImpl.update(1, alcohol);

        verify(this.session, times(1)).load(Alcohol.class, 1);
        verify(alcohol, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        Alcohol alcohol = mock(Alcohol.class);
        doThrow(Exception.class).when(this.session).update(any(Alcohol.class));
        this.alcoholRepositoryImpl.update(1, alcohol);
    }

    @Test
    public void deletePassTest() {
        Alcohol alcohol = mock(Alcohol.class);
        when(this.session.load(Alcohol.class, 1)).thenReturn(alcohol);
        doNothing().when(this.session).delete(alcohol);
        this.alcoholRepositoryImpl.delete(1);

        verify(this.session, times(1)).delete(alcohol);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        Alcohol alcohol = mock(Alcohol.class);
        doThrow(Exception.class).when(this.session).delete(alcohol);
        this.alcoholRepositoryImpl.delete(1);
    }

    @Test
    public void getAllAlcoholsByKindPassTest() {
        int kindId = 2;
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        Query<Alcohol> query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Alcohol.class))).thenReturn(query);
        when(query.setParameter("kindId", kindId)).thenReturn(query);
        when(query.getResultList()).thenReturn(alcohols);

        Assert.assertEquals(alcohols, this.alcoholRepositoryImpl.getAllAlcoholsByKind(kindId));
    }

    @Test(expected = Exception.class)
    public void getAllAlcoholsByKindFailureTest() {
        Query<Alcohol> query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);
        this.alcoholRepositoryImpl.getAllAlcoholsByKind(anyInt());
    }
}
