package services.impl;

import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.UserOpinionRepository;
import com.ppbkaf.application.services.impl.UserOpinionServiceImpl;
import com.ppbkaf.application.services.impl.util.UserDetectionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserOpinionServiceImplTest {

    @Mock
    private UserOpinionRepository userOpinionRepository;

    @Mock
    private UserDetectionService userDetectionService;

    @InjectMocks
    private UserOpinionServiceImpl userOpinionService;

    @Test
    public void likePassTest() {
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.like(1, 1)).thenReturn(1);

        this.userOpinionService.like(1);

        verify(this.userOpinionRepository, times(1)).like(anyInt(), anyInt());
        verify(this.userDetectionService, times(1)).getUserId();
    }

    @Test(expected = DatabaseException.class)
    public void likeFailureTest() {
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.like(1, 1)).thenThrow(RuntimeException.class);

        this.userOpinionService.like(1);
    }

    @Test
    public void dislikePassTest() {
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.dislike(1, 1)).thenReturn(1);

        this.userOpinionService.dislike(1);

        verify(this.userOpinionRepository, times(1)).dislike(anyInt(), anyInt());
        verify(this.userDetectionService, times(1)).getUserId();
    }

    @Test(expected = DatabaseException.class)
    public void dislikeFailureTest() {
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.dislike(1, 1)).thenThrow(RuntimeException.class);

        this.userOpinionService.dislike(1);
    }

    @Test
    public void getAllByUserPassTest() {
        List list = mock(List.class);
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.getAllByUser(1)).thenReturn(list);

        Assert.assertEquals(list, this.userOpinionService.getAllByUser());
        verify(this.userDetectionService, times(1)).getUserId();
    }

    @Test(expected = DatabaseException.class)
    public void getAllByUserFailureTest() {
        when(this.userDetectionService.getUserId()).thenReturn(1);
        when(this.userOpinionRepository.getAllByUser(1)).thenThrow(RuntimeException.class);

        this.userOpinionService.getAllByUser();
    }
}
