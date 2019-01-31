package controllers;

import com.ppbkaf.application.controllers.UserOpinionController;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.services.UserOpinionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserOpinionControllerTest {

    @Mock
    private UserOpinionService userOpinionService;

    @InjectMocks
    private UserOpinionController userOpinionController;

    @Test
    public void getAllPassTest() {
        when(this.userOpinionService.getAllByUser()).thenReturn(mock(List.class));
        ResponseEntity<?> responseEntity = this.userOpinionController.getAll();
        Assert.assertEquals(HashMap.class, responseEntity.getBody().getClass());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(this.userOpinionService, times(1)).getAllByUser();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.userOpinionService.getAllByUser()).thenThrow(GetException.class);
        this.userOpinionController.getAll();
    }
}
