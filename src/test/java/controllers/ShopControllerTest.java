package controllers;

import com.ppbkaf.application.controllers.ShopController;
import com.ppbkaf.application.dtos.ShopDTO;
import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.ShopService;
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
public class ShopControllerTest {
    @Mock
    private ShopService shopService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShopController shopController;

    @Test
    public void getAllPassTest() {
        List<Shop> shops = Arrays.asList(mock(Shop.class), mock(Shop.class));
        List<ShopDTO> shopsDTO = Arrays.asList(this.modelMapper.map(mock(Shop.class), ShopDTO.class),
                this.modelMapper.map(mock(Shop.class), ShopDTO.class));
        when(this.shopService.getAll()).thenReturn(shops);

        ResponseEntity<List<ShopDTO>> responseEntity = this.shopController.getAll();
        Assert.assertEquals(shopsDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.shopService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.shopService.getAll()).thenThrow(GetException.class);

        this.shopController.getAll();
    }

    @Test
    public void getPassTest() {
        Shop shop = mock(Shop.class);
        ShopDTO shopDTO = new ShopDTO();
        when(this.modelMapper.map(any(Shop.class), eq(ShopDTO.class))).thenReturn(shopDTO);
        when(this.shopService.get(anyInt())).thenReturn(shop);

        ResponseEntity<ShopDTO> responseEntity = this.shopController.get(1);
        Assert.assertEquals(shopDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.shopService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.shopService.get(anyInt())).thenThrow(GetException.class);

        this.shopController.get(1);
    }

    @Test
    public void addPassTest() {
        ShopDTO shopDTO = mock(ShopDTO.class);
        Shop shop = this.modelMapper.map(shopDTO, Shop.class);
        when(this.shopService.add(shop)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.shopController.add(shopDTO);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.shopService, times(1)).add(shop);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.modelMapper.map(any(ShopDTO.class), eq(Shop.class))).thenReturn(new Shop());
        when(this.shopService.add(any(Shop.class))).thenThrow(AddException.class);

        this.shopController.add(mock(ShopDTO.class));
    }

    @Test
    public void updatePassTest() {
        ShopDTO shopDTO = mock(ShopDTO.class);
        Shop shop = this.modelMapper.map(shopDTO, Shop.class);
        doNothing().when(this.shopService).update(1, shop);

        ResponseEntity<?> responseEntity = this.shopController.update(1, shopDTO);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.shopService, times(1)).update(1, shop);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        when(this.modelMapper.map(any(ShopDTO.class), eq(Shop.class))).thenReturn(new Shop());
        doThrow(UpdateException.class).when(this.shopService).update(anyInt(), any(Shop.class));

        this.shopController.update(1, mock(ShopDTO.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.shopService).delete(1);

        ResponseEntity<?> responseEntity = this.shopController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.shopService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.shopService).delete(anyInt());

        this.shopController.delete(1);
    }
}
