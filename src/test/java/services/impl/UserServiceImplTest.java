package services.impl;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.services.impl.UserServiceImpl;
import com.ppbkaf.application.token.TokenCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenCreator tokenCreator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAllPassTest() {
        List<User> users = Arrays.asList(mock(User.class), mock(User.class));
        when(this.userRepository.getAll()).thenReturn(users);

        Assert.assertEquals(users, this.userService.getAll());
        verify(this.userRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.userRepository.getAll()).thenThrow(RuntimeException.class);
        this.userService.getAll();
    }

    @Test
    public void getPassTest() {
        User user = mock(User.class);
        when(this.userRepository.get(1)).thenReturn(user);

        Assert.assertEquals(user, this.userService.get(1));
        verify(this.userRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.userRepository.get(1)).thenThrow(RuntimeException.class);

        this.userService.get(1);
    }

    @Test
    public void addPassTest() {
        User user = User.builder().id(1).build();
        when(this.userRepository.add(user)).thenReturn(user.getId());

        Assert.assertEquals(user.getId(), this.userService.add(user));
        verify(this.userRepository, times(1)).add(any(User.class));
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.userRepository.add(any(User.class))).thenThrow(RuntimeException.class);

        this.userService.add(mock(User.class));
    }

    @Test
    public void registerPassTest() {
        User user = User.builder().id(1).build();
        VerificationToken verificationToken = VerificationToken.builder().id(1).build();
        when(this.tokenCreator.create()).thenReturn(verificationToken);
        when(this.userRepository.register(user, verificationToken)).thenReturn(user.getId());

        Assert.assertEquals(user.getId(), this.userService.register(user));
        verify(this.userRepository, times(1)).register(any(User.class), any(VerificationToken.class));
    }

    @Test(expected = DatabaseException.class)
    public void registerFailureTest() {
        when(this.tokenCreator.create()).thenReturn(mock(VerificationToken.class));
        when(this.userRepository.register(any(User.class), any(VerificationToken.class))).thenThrow(RuntimeException.class);

        this.userService.register(mock(User.class));
    }

    @Test
    public void activatePassTest() {
        doNothing().when(this.userRepository).activate(anyString());

        this.userService.activate("token");

        verify(this.userRepository, times(1)).activate(anyString());
    }

    @Test(expected = DatabaseException.class)
    public void activateFailureTest() {
        doThrow(RuntimeException.class).when(this.userRepository).activate(anyString());

        this.userService.activate(anyString());
    }

    @Test
    public void updatePassTest() {
        User user = mock(User.class);
        doNothing().when(this.userRepository).update(1, user);
        this.userService.update(1, user);

        verify(this.userRepository, times(1)).update(1, user);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        User user = mock(User.class);
        doThrow(RuntimeException.class).when(this.userRepository).update(anyInt(), any(User.class));
        this.userService.update(1, user);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.userRepository).delete(1);
        this.userService.delete(1);

        verify(this.userRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.userRepository).delete(1);
        this.userService.delete(1);
    }
}
