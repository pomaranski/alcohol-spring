package repositories.impl.mock;

import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.repositories.impl.KindRepositoryImpl;
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
public class KindRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private KindRepositoryImpl kindRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<Kind> kinds = Arrays.asList(mock(Kind.class), mock(Kind.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Kind.class))).thenReturn(query);
        when(query.list()).thenReturn(kinds);

        Assert.assertEquals(kinds, this.kindRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery("FROM Kind", Kind.class).list()).thenThrow(Exception.class);
        this.kindRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        Kind kind = mock(Kind.class);
        when(this.session.get(Kind.class, 1)).thenReturn(kind);

        Assert.assertEquals(kind, this.kindRepositoryImpl.get(1));
        verify(this.session, times(1)).get(Kind.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(Kind.class, 1)).thenThrow(Exception.class);

        this.kindRepositoryImpl.get(1);
    }

    @Test
    public void addPassTest() {
        Kind kind = Kind.builder().id(1).build();
        when(this.session.save(kind)).thenReturn(kind.getId());

        Assert.assertEquals(kind.getId(), this.kindRepositoryImpl.add(kind));
        verify(this.session, times(1)).save(any(Kind.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(Kind.class))).thenThrow(Exception.class);

        this.kindRepositoryImpl.add(mock(Kind.class));
    }

    @Test
    public void updatePassTest() {
        Kind kind = mock(Kind.class);
        when(this.session.load(Kind.class, 1)).thenReturn(kind);
        this.kindRepositoryImpl.update(1, kind);

        verify(this.session, times(1)).load(Kind.class, 1);
        verify(kind, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        Kind kind = mock(Kind.class);
        doThrow(Exception.class).when(this.session).update(any(Kind.class));
        this.kindRepositoryImpl.update(1, kind);
    }

    @Test
    public void deletePassTest() {
        Kind kind = mock(Kind.class);
        when(this.session.load(Kind.class, 1)).thenReturn(kind);
        doNothing().when(this.session).delete(kind);
        this.kindRepositoryImpl.delete(1);

        verify(this.session, times(1)).delete(kind);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        Kind kind = mock(Kind.class);
        doThrow(Exception.class).when(this.session).delete(kind);
        this.kindRepositoryImpl.delete(1);
    }
}
