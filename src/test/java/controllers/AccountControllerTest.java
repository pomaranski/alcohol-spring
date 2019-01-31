package controllers;

import com.ppbkaf.application.controllers.AccountController;
import com.ppbkaf.application.dtos.UserDTO;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void registerPassTest() {
        when(this.userService.register(any(User.class))).thenReturn(1);
        when(this.modelMapper.map(any(UserDTO.class), eq(User.class))).thenReturn(new User());

        ResponseEntity<Integer> responseEntity = this.accountController
                .register(new UserDTO());
        Assert.assertEquals(Integer.valueOf(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.userService, times(1)).register(any(User.class));
    }

    @Test(expected = AddException.class)
    public void registerFailureTest() {
        when(this.modelMapper.map(any(UserDTO.class), eq(User.class))).thenReturn(new User());
        doThrow(AddException.class).when(this.userService).register(any(User.class));
        this.accountController.register(mock(UserDTO.class));
    }

    @Test
    public void activatePassTest() {
        doNothing().when(this.userService).activate(anyString());

        ResponseEntity<?> responseEntity = this.accountController.activate(anyString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.userService, times(1)).activate(anyString());
    }

    @Test(expected = GetException.class)
    public void activateFailureTest() {
        doThrow(GetException.class).when(this.userService).activate(anyString());
        this.accountController.activate(anyString());
    }
}
