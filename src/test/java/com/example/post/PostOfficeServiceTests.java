package com.example.post;


import com.example.post.exception.PostOfficeAlreadyExistException;
import com.example.post.exception.PostOfficeNotExistException;
import com.example.post.models.PostOffice;
import com.example.post.repositories.PostOfficeRepository;
import com.example.post.service.PostOfficeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PostOfficeServiceTests {
    @InjectMocks
    private PostOfficeServiceImpl service;

    @Mock
    private PostOfficeRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePostOffice() {
        PostOffice office = new PostOffice(123, "post office name", "receiver address");
        Mockito.when(repository.save(office)).thenReturn(office);

        var savedOffice = service.createOffice(office);
        Assertions.assertEquals(office.getIndex(), savedOffice);

        Mockito.verify(repository).findById(office.getIndex());
    }

    @Test
    public void testCreateExistPostOffice() {
        PostOffice office = new PostOffice(123, "post office name", "receiver address");
        Mockito.when(repository.findById(office.getIndex())).thenReturn(office);

        Assertions.assertThrows(PostOfficeAlreadyExistException.class, () -> service.createOffice(office));

        Mockito.verify(repository).findById(office.getIndex());
    }

    @Test
    public void testCreateZeroIndexPostOffice() {
        PostOffice office = new PostOffice(0, "post office name", "receiver address");
        Mockito.when(repository.findById(office.getIndex())).thenReturn(null);

        Assertions.assertThrows(PostOfficeNotExistException.class, () -> service.createOffice(office));

        Mockito.verify(repository).findById(office.getIndex());
    }

    @Test
    public void testGetPostOfficeById() {
        PostOffice office = new PostOffice(123123, "post office name", "receiver address");
        Mockito.when(repository.findById(office.getIndex())).thenReturn(office);

        var savedOffice = service.getOfficeById(office.getIndex());
        Assertions.assertEquals(office, savedOffice);

        Mockito.verify(repository).findById(office.getIndex());
    }

    @Test
    public void testGetNotExistPostOfficeById() {
        Mockito.when(repository.findById(123)).thenReturn(null);

        Assertions.assertThrows(PostOfficeNotExistException.class, () -> service.getOfficeById(123));

        Mockito.verify(repository).findById(123);
    }
}
