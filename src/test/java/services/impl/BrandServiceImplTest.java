package services.impl;

import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.BrandRepository;
import com.ppbkaf.application.services.impl.BrandServiceImpl;
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
public class BrandServiceImplTest {
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    public void getAllPassTest() {
        List<Brand> brands = Arrays.asList(mock(Brand.class), mock(Brand.class));
        when(this.brandRepository.getAll()).thenReturn(brands);

        Assert.assertEquals(brands, this.brandService.getAll());
        verify(this.brandRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.brandRepository.getAll()).thenThrow(RuntimeException.class);
        this.brandService.getAll();
    }

    @Test
    public void getPassTest() {
        Brand brand = mock(Brand.class);
        when(this.brandRepository.get(1)).thenReturn(brand);

        Assert.assertEquals(brand, this.brandService.get(1));
        verify(this.brandRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.brandRepository.get(1)).thenThrow(RuntimeException.class);

        this.brandService.get(1);
    }

    @Test
    public void addPassTest() {
        Brand brand = Brand.builder().id(1).build();
        when(this.brandRepository.add(brand)).thenReturn(brand.getId());

        Assert.assertEquals(brand.getId(), this.brandService.add(brand));
        verify(this.brandRepository, times(1)).add(any(Brand.class));
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.brandRepository.add(any(Brand.class))).thenThrow(RuntimeException.class);

        this.brandService.add(mock(Brand.class));
    }

    @Test
    public void updatePassTest() {
        Brand brand = mock(Brand.class);
        doNothing().when(this.brandRepository).update(1, brand);
        this.brandService.update(1, brand);

        verify(this.brandRepository, times(1)).update(1, brand);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        Brand brand = mock(Brand.class);
        doThrow(RuntimeException.class).when(this.brandRepository).update(anyInt(), any(Brand.class));
        this.brandService.update(1, brand);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.brandRepository).delete(1);
        this.brandService.delete(1);

        verify(this.brandRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.brandRepository).delete(1);
        this.brandService.delete(1);
    }
}
