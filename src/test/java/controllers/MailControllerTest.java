package controllers;

import com.ppbkaf.application.controllers.MailController;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.services.MailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {
    @Mock
    private MailService mailService;

    @InjectMocks
    private MailController mailController;

    @Test
    public void resendPassTest() {
        doNothing().when(this.mailService).resend("a");

        Assert.assertEquals(HttpStatus.OK, this.mailController.resend("a").getStatusCode());
    }

    @Test(expected = GetException.class)
    public void resendFailureTest() {
        doThrow(GetException.class).when(this.mailService).resend(anyString());
        this.mailController.resend(anyString());
    }
}
