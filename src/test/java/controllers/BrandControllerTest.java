package controllers;

import com.ppbkaf.application.controllers.BrandController;
import com.ppbkaf.application.dtos.BrandDTO;
import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.BrandService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BrandControllerTest {
    @Mock
    private BrandService brandService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BrandController brandController;

    @Test
    public void getAllPassTest() {
        List<Brand> brands = Arrays.asList(mock(Brand.class), mock(Brand.class));
        List<BrandDTO> brandsDTO = Arrays.asList(this.modelMapper.map(mock(Brand.class), BrandDTO.class),
                this.modelMapper.map(mock(Brand.class), BrandDTO.class));
        when(this.brandService.getAll()).thenReturn(brands);

        ResponseEntity<List<BrandDTO>> responseEntity = this.brandController.getAll();
        Assert.assertEquals(brandsDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.brandService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.brandService.getAll()).thenThrow(GetException.class);

        this.brandController.getAll();
    }

    @Test
    public void getPassTest() {
        Brand brand = mock(Brand.class);
        BrandDTO brandDTO = new BrandDTO();
        when(this.modelMapper.map(any(Brand.class), eq(BrandDTO.class))).thenReturn(brandDTO);
        when(this.brandService.get(anyInt())).thenReturn(brand);

        ResponseEntity<BrandDTO> responseEntity = this.brandController.get(1);
        Assert.assertEquals(brandDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.brandService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.brandService.get(anyInt())).thenThrow(GetException.class);

        this.brandController.get(1);
    }

    @Test
    public void addPassTest() {
        BrandDTO brandDTO = mock(BrandDTO.class);
        Brand brand = this.modelMapper.map(brandDTO, Brand.class);
        when(this.brandService.add(brand)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.brandController.add(brandDTO);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.brandService, times(1)).add(brand);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.modelMapper.map(any(BrandDTO.class), eq(Brand.class))).thenReturn(new Brand());
        when(this.brandService.add(any(Brand.class))).thenThrow(AddException.class);

        this.brandController.add(mock(BrandDTO.class));
    }

    @Test
    public void updatePassTest() {
        BrandDTO brandDTO = mock(BrandDTO.class);
        Brand brand = this.modelMapper.map(brandDTO, Brand.class);
        doNothing().when(this.brandService).update(1, brand);

        ResponseEntity<?> responseEntity = this.brandController.update(1, brandDTO);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.brandService, times(1)).update(1, brand);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        when(this.modelMapper.map(any(BrandDTO.class), eq(Brand.class))).thenReturn(new Brand());
        doThrow(UpdateException.class).when(this.brandService).update(anyInt(), any(Brand.class));

        this.brandController.update(1, mock(BrandDTO.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.brandService).delete(1);

        ResponseEntity<?> responseEntity = this.brandController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.brandService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.brandService).delete(anyInt());

        this.brandController.delete(1);
    }
}
