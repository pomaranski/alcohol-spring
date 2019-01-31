package controllers;

import com.ppbkaf.application.controllers.UserController;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getAllPassTest() {
        List<User> users = Arrays.asList(mock(User.class), mock(User.class));
        when(this.userService.getAll()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = this.userController.getAll();
        Assert.assertEquals(users, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.userService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.userService.getAll()).thenThrow(GetException.class);

        this.userController.getAll();
    }

    @Test
    public void getPassTest() {
        User user = mock(User.class);
        when(this.userService.get(anyInt())).thenReturn(user);

        ResponseEntity<User> responseEntity = this.userController.get(1);
        Assert.assertEquals(user, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.userService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.userService.get(anyInt())).thenThrow(GetException.class);

        this.userController.get(1);
    }

    @Test
    public void addPassTest() {
        User user = mock(User.class);
        when(this.userService.add(user)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.userController.add(user);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.userService, times(1)).add(user);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.userService.add(any(User.class))).thenThrow(AddException.class);

        this.userController.add(mock(User.class));
    }

    @Test
    public void updatePassTest() {
        User user = mock(User.class);
        doNothing().when(this.userService).update(1, user);

        ResponseEntity<?> responseEntity = this.userController.update(1, user);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.userService, times(1)).update(1, user);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        doThrow(UpdateException.class).when(this.userService).update(anyInt(), any(User.class));

        this.userController.update(1, mock(User.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.userService).delete(1);

        ResponseEntity<?> responseEntity = this.userController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.userService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.userService).delete(anyInt());

        this.userController.delete(1);
    }
}
