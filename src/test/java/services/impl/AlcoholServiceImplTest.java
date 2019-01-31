package services.impl;

import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.AlcoholRepository;
import com.ppbkaf.application.services.impl.AlcoholServiceImpl;
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
public class AlcoholServiceImplTest {
    @Mock
    private AlcoholRepository alcoholRepository;

    @InjectMocks
    private AlcoholServiceImpl alcoholService;

    @Test
    public void getAllPassTest() {
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        when(this.alcoholRepository.getAll()).thenReturn(alcohols);

        Assert.assertEquals(alcohols, this.alcoholService.getAll());
        verify(this.alcoholRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.alcoholRepository.getAll()).thenThrow(RuntimeException.class);
        this.alcoholService.getAll();
    }

    @Test
    public void getPassTest() {
        Alcohol alcohol = mock(Alcohol.class);
        when(this.alcoholRepository.get(1)).thenReturn(alcohol);

        Assert.assertEquals(alcohol, this.alcoholService.get(1));
        verify(this.alcoholRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.alcoholRepository.get(1)).thenThrow(RuntimeException.class);

        this.alcoholService.get(1);
    }

    @Test
    public void addPassTest() {
        Alcohol alcohol = Alcohol.builder().id(1).build();
        when(this.alcoholRepository.add(alcohol)).thenReturn(alcohol.getId());

        Assert.assertEquals(alcohol.getId(), this.alcoholService.add(alcohol));
        verify(this.alcoholRepository, times(1)).add(any(Alcohol.class));
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.alcoholRepository.add(any(Alcohol.class))).thenThrow(RuntimeException.class);

        this.alcoholService.add(mock(Alcohol.class));
    }

    @Test
    public void updatePassTest() {
        Alcohol alcohol = mock(Alcohol.class);
        doNothing().when(this.alcoholRepository).update(1, alcohol);
        this.alcoholService.update(1, alcohol);

        verify(this.alcoholRepository, times(1)).update(1, alcohol);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        Alcohol alcohol = mock(Alcohol.class);
        doThrow(RuntimeException.class).when(this.alcoholRepository).update(anyInt(), any(Alcohol.class));
        this.alcoholService.update(1, alcohol);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.alcoholRepository).delete(1);
        this.alcoholService.delete(1);

        verify(this.alcoholRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.alcoholRepository).delete(1);
        this.alcoholService.delete(1);
    }

    @Test
    public void getAllAlcoholsByKindPassTest() {
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        when(this.alcoholRepository.getAllAlcoholsByKind(anyInt())).thenReturn(alcohols);

        Assert.assertEquals(alcohols, this.alcoholService.getAllAlcoholsByKind(1));
        verify(this.alcoholRepository, times(1)).getAllAlcoholsByKind(1);
    }

    @Test(expected = DatabaseException.class)
    public void getAllAlcoholsByKindFailureTest() {
        doThrow(RuntimeException.class).when(this.alcoholRepository).getAllAlcoholsByKind(anyInt());
        this.alcoholService.getAllAlcoholsByKind(1);
    }
}
