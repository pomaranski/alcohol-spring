package repositories.impl.mock;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.repositories.impl.UserRepositoryImpl;
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
public class UserRepositoryImplMockTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @Before
    public void before() {
        when(this.sessionFactory.getCurrentSession()).thenReturn(this.session);
    }

    @Test
    public void getAllPassTest() {
        List<User> users = Arrays.asList(mock(User.class), mock(User.class));
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.list()).thenReturn(users);

        Assert.assertEquals(users, this.userRepositoryImpl.getAll());
    }

    @Test(expected = Exception.class)
    public void getAllFailureTest() {
        when(this.session.createQuery(anyString(), eq(User.class)).list()).thenThrow(Exception.class);
        this.userRepositoryImpl.getAll();
    }

    @Test
    public void getPassTest() {
        User user = mock(User.class);
        when(this.session.get(User.class, 1)).thenReturn(user);

        Assert.assertEquals(user, this.userRepositoryImpl.get(1));
        verify(this.session, times(1)).get(User.class, 1);
    }

    @Test(expected = Exception.class)
    public void getFailureTest() {
        when(this.session.get(User.class, 1)).thenThrow(Exception.class);

        this.userRepositoryImpl.get(1);
    }

    @Test
    public void getUserByEmailPassTest() {
        Query query = mock(Query.class);
        User user = mock(User.class);
        when(this.session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        Assert.assertEquals(user, this.userRepositoryImpl.getUserByEmail("email"));
    }

    @Test(expected = Exception.class)
    public void getUserByEmailFailureTest() {
        Query<User> query = mock(Query.class);
        when(query.getSingleResult()).thenThrow(Exception.class);

        this.userRepositoryImpl.getUserByEmail("email");
    }

    @Test
    public void addPassTest() {
        User user = User.builder().id(1).build();
        when(this.session.save(user)).thenReturn(user.getId());

        Assert.assertEquals(user.getId(), this.userRepositoryImpl.add(user));
        verify(this.session, times(1)).save(any(User.class));
    }

    @Test(expected = Exception.class)
    public void addFailureTest() {
        when(this.session.save(any(User.class))).thenThrow(Exception.class);

        this.userRepositoryImpl.add(mock(User.class));
    }

    @Test
    public void registerPassTest() {
        User user = User.builder().id(1).build();
        VerificationToken verificationToken = VerificationToken.builder().id(1).build();
        when(this.session.save(user)).thenReturn(user.getId());
        when(this.session.save(verificationToken)).thenReturn(verificationToken.getId());

        Assert.assertEquals(user.getId(), this.userRepositoryImpl.register(user, verificationToken));
        verify(this.session, times(1)).save(any(VerificationToken.class));
        verify(this.session, times(1)).save(any(User.class));
    }

    @Test(expected = Exception.class)
    public void registerFailureTest() {
        when(this.session.save(any(VerificationToken.class))).thenThrow(Exception.class);

        this.userRepositoryImpl.register(mock(User.class), mock(VerificationToken.class));
    }

    @Test
    public void activatePassTest() {
        Query query = mock(Query.class);
        User user = mock(User.class);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1);
        when(this.session.load(User.class, 1)).thenReturn(user);

        this.userRepositoryImpl.activate("token");

        verify(user, times(1)).setActivated(true);
    }

    @Test(expected = Exception.class)
    public void activateFailureTest() {
        when(this.session.save(any(VerificationToken.class))).thenThrow(Exception.class);

        this.userRepositoryImpl.register(mock(User.class), mock(VerificationToken.class));
    }

    @Test
    public void updatePassTest() {
        User user = mock(User.class);
        when(this.session.load(User.class, 1)).thenReturn(user);
        this.userRepositoryImpl.update(1, user);

        verify(this.session, times(1)).load(User.class, 1);
        verify(user, times(1)).copy(any());
    }

    @Test(expected = Exception.class)
    public void updateFailureTest() {
        User user = mock(User.class);
        doThrow(Exception.class).when(this.session).update(any(User.class));
        this.userRepositoryImpl.update(1, user);
    }

    @Test
    public void deletePassTest() {
        User user = mock(User.class);
        when(this.session.load(User.class, 1)).thenReturn(user);
        doNothing().when(this.session).delete(user);
        this.userRepositoryImpl.delete(1);

        verify(this.session, times(1)).delete(user);
    }

    @Test(expected = Exception.class)
    public void deleteFailureTest() {
        User user = mock(User.class);
        doThrow(Exception.class).when(this.session).delete(user);
        this.userRepositoryImpl.delete(1);
    }

    @Test
    public void getUserByLoginPassTest() {
        String login = "login";
        Query query = mock(Query.class);
        User user = mock(User.class);
        when(this.session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter("login", login)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        Assert.assertEquals(user, this.userRepositoryImpl.getUserByLogin(login));
    }

    @Test(expected = Exception.class)
    public void getUserByLoginFailureTest() {
        Query<User> query = mock(Query.class);
        when(query.getSingleResult()).thenThrow(Exception.class);

        this.userRepositoryImpl.getUserByLogin(anyString());
    }

    @Test
    public void getUserIdByLoginPassTest() {
        String login = "login";
        Query query = mock(Query.class);
        when(this.session.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
        when(query.setParameter("login", login)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1);

        Assert.assertEquals(Integer.valueOf(1), this.userRepositoryImpl.getUserIdByLogin(login));
    }

    @Test(expected = Exception.class)
    public void getUserIdByLoginFailureTest() {
        Query<User> query = mock(Query.class);
        when(query.getSingleResult()).thenThrow(Exception.class);

        this.userRepositoryImpl.getUserIdByLogin(anyString());
    }
}
