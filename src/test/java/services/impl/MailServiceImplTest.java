package services.impl;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.repositories.VerificationTokenRepository;
import com.ppbkaf.application.services.MailSenderService;
import com.ppbkaf.application.services.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceImplTest {

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailSenderService mailSenderService;

    @InjectMocks
    private MailServiceImpl mailServiceImpl;

    @Test
    public void resendNotNullTokenPassTest() {
        when(this.verificationTokenRepository.getTokenByUser(anyInt())).thenReturn(mock(VerificationToken.class));
        when(this.userRepository.getUserByEmail(anyString())).thenReturn(mock(User.class));
        doNothing().when(this.mailSenderService).send(any(User.class), any(VerificationToken.class));

        this.mailServiceImpl.resend(anyString());
        verify(this.mailSenderService, times(1)).send(any(User.class), any(VerificationToken.class));
    }

    @Test(expected = DatabaseException.class)
    public void resendNullTokenPassTest() {
        when(this.userRepository.getUserByEmail(anyString())).thenReturn(mock(User.class));
        when(this.verificationTokenRepository.getTokenByUser(anyInt())).thenThrow(RuntimeException.class);

        this.mailServiceImpl.resend(anyString());
    }

    @Test(expected = DatabaseException.class)
    public void resendFailureTest() {
        when(this.userRepository.getUserByEmail(anyString())).thenThrow(RuntimeException.class);

        this.mailServiceImpl.resend(anyString());
    }
}
