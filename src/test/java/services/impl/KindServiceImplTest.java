package services.impl;

import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.KindRepository;
import com.ppbkaf.application.services.impl.KindServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KindServiceImplTest {
    @Mock
    private KindRepository kindRepository;

    @InjectMocks
    private KindServiceImpl kindService;

    @Test
    public void getAllPassTest() {
        List<Kind> kinds = Arrays.asList(mock(Kind.class), mock(Kind.class));
        when(this.kindRepository.getAll()).thenReturn(kinds);

        Assert.assertEquals(kinds, this.kindService.getAll());
        verify(this.kindRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.kindRepository.getAll()).thenThrow(RuntimeException.class);
        this.kindService.getAll();
    }

    @Test
    public void getPassTest() {
        Kind kind = mock(Kind.class);
        when(this.kindRepository.get(1)).thenReturn(kind);

        Assert.assertEquals(kind, this.kindService.get(1));
        verify(this.kindRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.kindRepository.get(1)).thenThrow(RuntimeException.class);

        this.kindService.get(1);
    }

    @Test
    public void addPassTest() {
        Kind kind = Kind.builder().id(1).build();
        when(this.kindRepository.add(kind)).thenReturn(kind.getId());

        Assert.assertEquals(kind.getId(), this.kindService.add(kind));
        verify(this.kindRepository, times(1)).add(any(Kind.class));
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.kindRepository.add(any(Kind.class))).thenThrow(RuntimeException.class);

        this.kindService.add(mock(Kind.class));
    }

    @Test
    public void updatePassTest() {
        Kind kind = mock(Kind.class);
        doNothing().when(this.kindRepository).update(1, kind);
        this.kindService.update(1, kind);

        verify(this.kindRepository, times(1)).update(1, kind);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        Kind kind = mock(Kind.class);
        doThrow(RuntimeException.class).when(this.kindRepository).update(anyInt(), any(Kind.class));
        this.kindService.update(1, kind);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.kindRepository).delete(1);
        this.kindService.delete(1);

        verify(this.kindRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.kindRepository).delete(1);
        this.kindService.delete(1);
    }
}
