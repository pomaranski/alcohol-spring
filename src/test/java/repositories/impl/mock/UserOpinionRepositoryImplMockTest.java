package repositories.impl.mock;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.UserOpinion;
import com.ppbkaf.application.repositories.impl.UserOpinionRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserOpinionRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private UserOpinionRepositoryImpl userOpinionRepositoryImpl;

    @Test
    public void likeExistsPassTest() {
        Query query = mock(Query.class);
        when(this.sessionFactory.getCurrentSession()).thenReturn(session);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(1);
        when(this.session.load(UserOpinion.class, 1)).thenReturn(mock(UserOpinion.class));

        Assert.assertEquals(1, this.userOpinionRepositoryImpl.like(1, 1));
    }

    @Test
    public void likeNotExistsPassTest() {
        Query query = mock(Query.class);
        when(this.sessionFactory.getCurrentSession()).thenReturn(session);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);
        when(this.session.load(eq(Sale.class), anyInt())).thenReturn(mock(Sale.class));
        when(this.session.load(eq(User.class), anyInt())).thenReturn(mock(User.class));
        when(this.session.save(any(UserOpinion.class))).thenReturn(1);

        Assert.assertEquals(1, this.userOpinionRepositoryImpl.like(1, 1));
    }

    @Test(expected = Exception.class)
    public void likeFailureTest() {
        when(this.session.createQuery(anyString(), eq(UserOpinion.class)).list()).thenThrow(Exception.class);
        this.userOpinionRepositoryImpl.like(1, 1);
    }

    @Test
    public void dislikeExistsPassTest() {
        Query query = mock(Query.class);
        when(this.sessionFactory.getCurrentSession()).thenReturn(session);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(1);
        when(this.session.load(UserOpinion.class, 1)).thenReturn(mock(UserOpinion.class));

        Assert.assertEquals(1, this.userOpinionRepositoryImpl.dislike(1, 1));
    }

    @Test
    public void dislikeNotExistsPassTest() {
        Query query = mock(Query.class);
        when(this.sessionFactory.getCurrentSession()).thenReturn(session);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);
        when(this.session.load(eq(Sale.class), anyInt())).thenReturn(mock(Sale.class));
        when(this.session.load(eq(User.class), anyInt())).thenReturn(mock(User.class));
        when(this.session.save(any(UserOpinion.class))).thenReturn(1);

        Assert.assertEquals(1, this.userOpinionRepositoryImpl.dislike(1, 1));
    }

    @Test(expected = Exception.class)
    public void dislikeFailureTest() {
        when(this.session.createQuery(anyString(), eq(UserOpinion.class)).list()).thenThrow(Exception.class);
        this.userOpinionRepositoryImpl.dislike(1, 1);
    }

    @Test
    public void getAllByUserPassTest() {
        Query query = mock(Query.class);
        List list = mock(List.class);
        when(this.sessionFactory.getCurrentSession()).thenReturn(session);
        when(this.session.createQuery(anyString(), eq(UserOpinionDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(list);

        Assert.assertEquals(list, this.userOpinionRepositoryImpl.getAllByUser(1));
    }

    @Test(expected = Exception.class)
    public void getAllByUserFailureTest() {
        Query query = mock(Query.class);
        when(query.getResultList()).thenThrow(Exception.class);

        this.userOpinionRepositoryImpl.getAllByUser(1);
    }
}
