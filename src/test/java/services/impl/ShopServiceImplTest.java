package services.impl;

import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.exceptions.DatabaseException;
import com.ppbkaf.application.repositories.ShopRepository;
import com.ppbkaf.application.services.impl.ShopServiceImpl;
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
public class ShopServiceImplTest {
    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    public void getAllPassTest() {
        List<Shop> shops = Arrays.asList(mock(Shop.class), mock(Shop.class));
        when(this.shopRepository.getAll()).thenReturn(shops);

        Assert.assertEquals(shops, this.shopService.getAll());
        verify(this.shopRepository, times(1)).getAll();
    }

    @Test(expected = DatabaseException.class)
    public void getAllFailureTest() {
        when(this.shopRepository.getAll()).thenThrow(RuntimeException.class);
        this.shopService.getAll();
    }

    @Test
    public void getPassTest() {
        Shop shop = mock(Shop.class);
        when(this.shopRepository.get(1)).thenReturn(shop);

        Assert.assertEquals(shop, this.shopService.get(1));
        verify(this.shopRepository, times(1)).get(1);
    }

    @Test(expected = DatabaseException.class)
    public void getFailureTest() {
        when(this.shopRepository.get(1)).thenThrow(RuntimeException.class);

        this.shopService.get(1);
    }

    @Test
    public void addPassTest() {
        Shop shop = Shop.builder().id(1).build();
        when(this.shopRepository.add(shop)).thenReturn(shop.getId());

        Assert.assertEquals(shop.getId(), this.shopService.add(shop));
        verify(this.shopRepository, times(1)).add(any(Shop.class));
    }

    @Test(expected = DatabaseException.class)
    public void addFailureTest() {
        when(this.shopRepository.add(any(Shop.class))).thenThrow(RuntimeException.class);

        this.shopService.add(mock(Shop.class));
    }

    @Test
    public void updatePassTest() {
        Shop shop = mock(Shop.class);
        doNothing().when(this.shopRepository).update(1, shop);
        this.shopService.update(1, shop);

        verify(this.shopRepository, times(1)).update(1, shop);
    }

    @Test(expected = DatabaseException.class)
    public void updateFailureTest() {
        Shop shop = mock(Shop.class);
        doThrow(RuntimeException.class).when(this.shopRepository).update(anyInt(), any(Shop.class));
        this.shopService.update(1, shop);
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.shopRepository).delete(1);
        this.shopService.delete(1);

        verify(this.shopRepository, times(1)).delete(1);
    }

    @Test(expected = DatabaseException.class)
    public void deleteFailureTest() {
        doThrow(RuntimeException.class).when(this.shopRepository).delete(1);
        this.shopService.delete(1);
    }
}
