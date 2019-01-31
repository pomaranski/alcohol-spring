package services.impl;

import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.SaleRepository;
import com.ppbkaf.application.services.impl.SaleServiceImpl;
import com.ppbkaf.application.services.impl.util.UserDetectionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaleServiceImplTest {
    @Mock
    private SaleRepository saleRepository;

    @Mock
    private UserDetectionService userDetectionService;

    @InjectMocks
    private SaleServiceImpl saleService;

    @Test
    public void getAllPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        when(this.saleRepository.getAll()).thenReturn(sales);

        Assert.assertEquals(sales, this.saleService.getAll());
        verify(this.saleRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.saleRepository.getAll()).thenThrow(RuntimeException.class);
        this.saleService.getAll();
    }

    @Test
    public void getPassTest() {
        Sale sale = mock(Sale.class);
        when(this.saleRepository.get(1)).thenReturn(sale);

        Assert.assertEquals(sale, this.saleService.get(1));
        verify(this.saleRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.saleRepository.get(1)).thenThrow(RuntimeException.class);

        this.saleService.get(1);
    }

    @Test
    public void addPassTest() {
        Sale sale = Sale.builder().id(1).build();
        when(this.userDetectionService.getUser()).thenReturn(mock(User.class));
        when(this.saleRepository.add(sale)).thenReturn(sale.getId());

        Assert.assertEquals(sale.getId(), this.saleService.add(sale));
        verify(this.saleRepository, times(1)).add(any(Sale.class));
        verify(this.userDetectionService, times(1)).getUser();
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.saleRepository.add(any(Sale.class))).thenThrow(RuntimeException.class);

        this.saleService.add(mock(Sale.class));
    }

    @Test
    public void updatePassTest() {
        Sale sale = mock(Sale.class);
        doNothing().when(this.saleRepository).update(1, sale);
        this.saleService.update(1, sale);

        verify(this.saleRepository, times(1)).update(1, sale);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        Sale sale = mock(Sale.class);
        doThrow(RuntimeException.class).when(this.saleRepository).update(anyInt(), any(Sale.class));
        this.saleService.update(1, sale);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.saleRepository).delete(1);
        this.saleService.delete(1);

        verify(this.saleRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.saleRepository).delete(1);
        this.saleService.delete(1);
    }

    @Test
    public void getAllSalesByShopAndKindPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        when(this.saleRepository.getAllSalesByShopAndKind(anyInt(), anyInt())).thenReturn(sales);

        Assert.assertEquals(sales, this.saleService.getAllSalesByShopAndKind(1, 1));
        verify(this.saleRepository, times(1)).getAllSalesByShopAndKind(1, 1);
    }

    @Test(expected = DatabaseException.class)
    public void getAllSalesByShopAndKindFailureTest() {
        doThrow(RuntimeException.class).when(this.saleRepository).getAllSalesByShopAndKind(anyInt(), anyInt());
        this.saleService.getAllSalesByShopAndKind(1, 1);
    }

    @Test
    public void getAllSalesByShopPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        when(this.saleRepository.getAllSalesByShop(anyInt())).thenReturn(sales);

        Assert.assertEquals(sales, this.saleService.getAllSalesByShop(1));
        verify(this.saleRepository, times(1)).getAllSalesByShop(1);
    }

    @Test(expected = DatabaseException.class)
    public void getAllSalesByShopFailureTest() {
        doThrow(RuntimeException.class).when(this.saleRepository).getAllSalesByShop(anyInt());
        this.saleService.getAllSalesByShop(1);
    }

    @Test
    public void getAllSalesByKindPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        when(this.saleRepository.getAllSalesByKind(anyInt())).thenReturn(sales);

        Assert.assertEquals(sales, this.saleService.getAllSalesByKind(1));
        verify(this.saleRepository, times(1)).getAllSalesByKind(1);
    }

    @Test(expected = DatabaseException.class)
    public void getAllSalesByKindFailureTest() {
        doThrow(RuntimeException.class).when(this.saleRepository).getAllSalesByKind(anyInt());
        this.saleService.getAllSalesByKind(1);
    }

    @Test
    public void getAllSalesByUserPassTest() {
        List<Sale> sales = Arrays.asList(mock(Sale.class), mock(Sale.class));
        when(this.saleRepository.getAllSalesByUser(anyInt())).thenReturn(sales);

        Assert.assertEquals(sales, this.saleService.getAllSalesByUser());
        verify(this.saleRepository, times(1)).getAllSalesByUser(anyInt());
        verify(this.userDetectionService, times(1)).getUserId();
    }

    @Test(expected = DatabaseException.class)
    public void getAllSalesByUserFailureTest() {
        Authentication authentication = mock(Authentication.class);
        doThrow(RuntimeException.class).when(this.saleRepository).getAllSalesByUser(anyInt());
        this.saleService.getAllSalesByUser();
    }
}
