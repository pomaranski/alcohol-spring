package controllers;

import com.ppbkaf.application.controllers.SaleController;
import com.ppbkaf.application.dtos.SaleDTO;
import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.SaleService;
import com.ppbkaf.application.services.UserOpinionService;
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
public class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @Mock
    private UserOpinionService userOpinionService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SaleController saleController;

    @Test
    public void getAllPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        List<SaleDTO> salesDTO = Arrays.asList(this.modelMapper.map(mock(Sale.class), SaleDTO.class),
                this.modelMapper.map(mock(Sale.class), SaleDTO.class));
        when(this.saleService.getAll()).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> responseEntity = this.saleController.getAll();
        Assert.assertEquals(salesDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.saleService.getAll()).thenThrow(GetException.class);
        this.saleController.getAll();
    }

    @Test
    public void getPassTest() {
        Sale sale = mock(Sale.class);
        SaleDTO saleDTO = new SaleDTO();
        when(this.modelMapper.map(any(Sale.class), eq(SaleDTO.class))).thenReturn(saleDTO);
        when(this.saleService.get(anyInt())).thenReturn(sale);

        ResponseEntity<SaleDTO> responseEntity = this.saleController.get(1);
        Assert.assertEquals(saleDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.saleService.get(anyInt())).thenThrow(GetException.class);

        this.saleController.get(1);
    }

    @Test
    public void addPassTest() {
        Sale sale = mock(Sale.class);
        when(this.saleService.add(sale)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.saleController.add(sale);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).add(sale);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.saleService.add(any(Sale.class))).thenThrow(AddException.class);

        this.saleController.add(mock(Sale.class));
    }

    @Test
    public void updatePassTest() {
        SaleDTO saleDTO = mock(SaleDTO.class);
        Sale sale = this.modelMapper.map(saleDTO, Sale.class);
        doNothing().when(this.saleService).update(1, sale);

        ResponseEntity<?> responseEntity = this.saleController.update(1, saleDTO);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).update(1, sale);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        when(this.modelMapper.map(any(SaleDTO.class), eq(Sale.class))).thenReturn(new Sale());
        doThrow(UpdateException.class).when(this.saleService).update(anyInt(), any(Sale.class));

        this.saleController.update(1, mock(SaleDTO.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.saleService).delete(1);

        ResponseEntity<?> responseEntity = this.saleController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.saleService).delete(anyInt());

        this.saleController.delete(1);
    }

    @Test
    public void likePassTest() {
        doNothing().when(this.userOpinionService).like(1);

        ResponseEntity<?> responseEntity = this.saleController.like(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.userOpinionService, times(1)).like(1);
    }

    @Test(expected = UpdateException.class)
    public void likeFailureTest() {
        doThrow(UpdateException.class).when(this.userOpinionService).like(anyInt());

        this.saleController.like(1);
    }

    @Test
    public void dislikePassTest() {
        doNothing().when(this.userOpinionService).dislike(1);

        ResponseEntity<?> responseEntity = this.saleController.dislike(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.userOpinionService, times(1)).dislike(1);
    }

    @Test(expected = UpdateException.class)
    public void dislikeFailureTest() {
        doThrow(UpdateException.class).when(this.userOpinionService).dislike(anyInt());

        this.saleController.dislike(1);
    }

    @Test
    public void getAllSalesByShopAndKindPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        List<SaleDTO> salesDTO = Arrays.asList(this.modelMapper.map(mock(Sale.class), SaleDTO.class),
                this.modelMapper.map(mock(Sale.class), SaleDTO.class));
        when(this.saleService.getAllSalesByShopAndKind(anyInt(), anyInt())).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> responseEntity = this.saleController.getAllSalesByShopAndKind(1, 1);
        Assert.assertEquals(salesDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).getAllSalesByShopAndKind(1, 1);
    }

    @Test(expected = GetException.class)
    public void getAllSalesByShopAndKindFailureTest() {
        when(this.saleService.getAllSalesByShopAndKind(anyInt(), anyInt())).thenThrow(GetException.class);

        this.saleController.getAllSalesByShopAndKind(1, 1);
    }

    @Test
    public void getAllSalesByShopPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        List<SaleDTO> salesDTO = Arrays.asList(this.modelMapper.map(mock(Sale.class), SaleDTO.class),
                this.modelMapper.map(mock(Sale.class), SaleDTO.class));
        when(this.saleService.getAllSalesByShop(anyInt())).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> responseEntity = this.saleController.getAllSalesByShop(1);
        Assert.assertEquals(salesDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).getAllSalesByShop(1);
    }

    @Test(expected = GetException.class)
    public void getAllSalesByShopFailureTest() {
        when(this.saleService.getAllSalesByShop(anyInt())).thenThrow(GetException.class);

        this.saleController.getAllSalesByShop(1);
    }

    @Test
    public void getAllSalesByKindPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        List<SaleDTO> salesDTO = Arrays.asList(this.modelMapper.map(mock(Sale.class), SaleDTO.class),
                this.modelMapper.map(mock(Sale.class), SaleDTO.class));
        when(this.saleService.getAllSalesByKind(anyInt())).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> responseEntity = this.saleController.getAllSalesByKind(1);
        Assert.assertEquals(salesDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).getAllSalesByKind(1);
    }

    @Test(expected = GetException.class)
    public void getAllSalesByKindFailureTest() {
        when(this.saleService.getAllSalesByKind(anyInt())).thenThrow(GetException.class);

        this.saleController.getAllSalesByKind(1);
    }

    @Test
    public void getAllSalesByUserPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        List<SaleDTO> salesDTO = Arrays.asList(this.modelMapper.map(mock(Sale.class), SaleDTO.class),
                this.modelMapper.map(mock(Sale.class), SaleDTO.class));
        when(this.saleService.getAllSalesByUser()).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> responseEntity = this.saleController.getAllSalesByUser();
        Assert.assertEquals(salesDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.saleService, times(1)).getAllSalesByUser();
    }

    @Test(expected = GetException.class)
    public void getAllSalesByUserFailureTest() {
        when(this.saleService.getAllSalesByUser()).thenThrow(GetException.class);

        this.saleController.getAllSalesByUser();
    }
}
