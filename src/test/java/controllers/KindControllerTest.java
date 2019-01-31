package controllers;

import com.ppbkaf.application.controllers.KindController;
import com.ppbkaf.application.dtos.KindDTO;
import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.services.KindService;
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
public class KindControllerTest {
    @Mock
    private KindService kindService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private KindController kindController;

    @Test
    public void getAllPassTest() {
        List<Kind> kinds = Arrays.asList(mock(Kind.class), mock(Kind.class));
        List<KindDTO> kindsDTO = Arrays.asList(this.modelMapper.map(mock(Kind.class), KindDTO.class),
                this.modelMapper.map(mock(Kind.class), KindDTO.class));
        when(this.kindService.getAll()).thenReturn(kinds);

        ResponseEntity<List<KindDTO>> responseEntity = this.kindController.getAll();
        Assert.assertEquals(kindsDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.kindService, times(1)).getAll();
    }

    @Test(expected = GetException.class)
    public void getAllFailureTest() {
        when(this.kindService.getAll()).thenThrow(GetException.class);

        this.kindController.getAll();
    }

    @Test
    public void getPassTest() {
        Kind kind = mock(Kind.class);
        KindDTO kindDTO = new KindDTO();
        when(this.modelMapper.map(any(Kind.class), eq(KindDTO.class))).thenReturn(kindDTO);
        when(this.kindService.get(anyInt())).thenReturn(kind);

        ResponseEntity<KindDTO> responseEntity = this.kindController.get(1);
        Assert.assertEquals(kindDTO, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(this.kindService, times(1)).get(1);
    }

    @Test(expected = GetException.class)
    public void getFailureTest() {
        when(this.kindService.get(anyInt())).thenThrow(GetException.class);

        this.kindController.get(1);
    }

    @Test
    public void addPassTest() {
        KindDTO kindDTO = mock(KindDTO.class);
        Kind kind = this.modelMapper.map(kindDTO, Kind.class);
        when(this.kindService.add(kind)).thenReturn(1);

        ResponseEntity<Integer> responseEntity = this.kindController.add(kindDTO);
        Assert.assertEquals(new Integer(1), responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.kindService, times(1)).add(kind);
    }

    @Test(expected = AddException.class)
    public void addFailureTest() {
        when(this.modelMapper.map(any(KindDTO.class), eq(Kind.class))).thenReturn(new Kind());
        when(this.kindService.add(any(Kind.class))).thenThrow(AddException.class);

        this.kindController.add(mock(KindDTO.class));
    }

    @Test
    public void updatePassTest() {
        Kind kind = mock(Kind.class);
        doNothing().when(this.kindService).update(1, kind);

        ResponseEntity<?> responseEntity = this.kindController.update(1, kind);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.kindService, times(1)).update(1, kind);
    }

    @Test(expected = UpdateException.class)
    public void updateFailureTest() {
        doThrow(UpdateException.class).when(this.kindService).update(anyInt(), any(Kind.class));

        this.kindController.update(1, mock(Kind.class));
    }

    @Test
    public void deletePassTest() {
        doNothing().when(this.kindService).delete(1);

        ResponseEntity<?> responseEntity = this.kindController.delete(1);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(this.kindService, times(1)).delete(1);
    }

    @Test(expected = DeleteException.class)
    public void deleteFailureTest() {
        doThrow(DeleteException.class).when(this.kindService).delete(anyInt());

        this.kindController.delete(1);
    }
}
