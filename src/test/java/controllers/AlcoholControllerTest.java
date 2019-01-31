package controllers;

import com.ppbkaf.application.controllers.AlcoholController;
import com.ppbkaf.application.dtos.AlcoholDTO;
import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.AlcoholService;
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
public class AlcoholControllerTest {
    @Mock
    private AlcoholService alcoholService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AlcoholController alcoholController;

    @Test
    public void getAllPassTest() {
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        List<AlcoholDTO> alcoholsDTO = Arrays.asList(this.modelMapper.map(mock(Alcohol.class), AlcoholDTO.class),
                this.modelMapper.map(mock(Alcohol.class), AlcoholDTO.class));
        when(this.alcoholService.getAll()).thenReturn(alcohols);

        ResponseEntity<List<AlcoholDTO>> responseEntity = this.alcoholController.getAll();
        Assert.assertEquals(alcoholsDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.alcoholService.getAll()).thenThrow(GetException.class);

        this.alcoholController.getAll();
    }

    @Test
    public void getPassTest() {
        Alcohol alcohol = mock(Alcohol.class);
        AlcoholDTO alcoholDTO = new AlcoholDTO();
        when(this.modelMapper.map(any(Alcohol.class), eq(AlcoholDTO.class))).thenReturn(alcoholDTO);
        when(this.alcoholService.get(anyInt())).thenReturn(alcohol);

        ResponseEntity<AlcoholDTO> responseEntity = this.alcoholController.get(1);
        Assert.assertEquals(alcoholDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.alcoholService.get(anyInt())).thenThrow(GetException.class);

        this.alcoholController.get(1);
    }

    @Test
    public void addPassTest() {
        AlcoholDTO alcoholDTO = mock(AlcoholDTO.class);
        Alcohol alcohol = this.modelMapper.map(alcoholDTO, Alcohol.class);
        when(this.alcoholService.add(alcohol)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.alcoholController.add(alcoholDTO);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).add(alcohol);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.modelMapper.map(any(AlcoholDTO.class), eq(Alcohol.class))).thenReturn(new Alcohol());
        when(this.alcoholService.add(any(Alcohol.class))).thenThrow(AddException.class);

        this.alcoholController.add(mock(AlcoholDTO.class));
    }

    @Test
    public void updatePassTest() {
        AlcoholDTO alcoholDTO = mock(AlcoholDTO.class);
        Alcohol alcohol = this.modelMapper.map(alcoholDTO, Alcohol.class);
        doNothing().when(this.alcoholService).update(1, alcohol);

        ResponseEntity<?> responseEntity = this.alcoholController.update(1, alcoholDTO);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).update(1, alcohol);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        when(this.modelMapper.map(any(AlcoholDTO.class), eq(Alcohol.class))).thenReturn(new Alcohol());
        doThrow(UpdateException.class).when(this.alcoholService).update(anyInt(), any(Alcohol.class));

        this.alcoholController.update(1, mock(AlcoholDTO.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.alcoholService).delete(1);

        ResponseEntity<?> responseEntity = this.alcoholController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.alcoholService).delete(anyInt());

        this.alcoholController.delete(1);
    }

    @Test
    public void getAllAlcoholsByKindPassTest() {
        List<Alcohol> alcohols = Arrays.asList(mock(Alcohol.class), mock(Alcohol.class));
        List<AlcoholDTO> alcoholsDTO = Arrays.asList(this.modelMapper.map(mock(Alcohol.class), AlcoholDTO.class)
                , this.modelMapper.map(mock(Alcohol.class), AlcoholDTO.class));
        when(this.alcoholService.getAllAlcoholsByKind(anyInt())).thenReturn(alcohols);

        ResponseEntity<List<AlcoholDTO>> responseEntity = this.alcoholController.getAllAlcoholsByKind(1);
        Assert.assertEquals(alcoholsDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.alcoholService, times(1)).getAllAlcoholsByKind(1);
    }

    @Test(expected = GetException.class)
    public void getAllAlcoholsByKindFailureTest() {
        when(this.alcoholService.getAllAlcoholsByKind(anyInt())).thenThrow(GetException.class);

        this.alcoholController.getAllAlcoholsByKind(1);
    }
}
