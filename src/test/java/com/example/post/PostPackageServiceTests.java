package com.example.post;

import com.example.post.exception.PackageNotExistException;
import com.example.post.exception.PackageStatusException;
import com.example.post.exception.PostOfficeNotExistException;
import com.example.post.models.*;
import com.example.post.repositories.PackageDeliveryRepository;
import com.example.post.repositories.PackageRepository;
import com.example.post.service.PackageServiceImpl;
import com.example.post.service.PostOfficeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class PostPackageServiceTests {
    @InjectMocks
    private PackageServiceImpl service;
    @Mock
    private PackageRepository repository;
    @Mock
    private PostOfficeService postOfficeService;
    @Mock
    private PackageDeliveryRepository packageDeliveryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterPostPackage() {
        PostPackage postPackage = new PostPackage(123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        Mockito.when(repository.save(postPackage)).thenReturn(postPackage);

        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getOfficeById(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        PostOffice toPostOffice = new PostOffice(123124, "", "");
        Mockito.when(postOfficeService.getOfficeById(toPostOffice.getIndex())).thenReturn(toPostOffice);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, PackageDeliveryStatus.REGISTER);
        Mockito.when(packageDeliveryRepository.save(delivery)).thenReturn(delivery);

        var packageId = service.register(fromPostOffice.getIndex(), postPackage);
        Assertions.assertEquals(postPackage.getId(), packageId);
    }

    @Test
    public void testRegisterPostPackageFromNotExistPostOffice() {
        PostPackage postPackage = new PostPackage(123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        Mockito.when(repository.save(postPackage)).thenReturn(postPackage);
        Mockito.when(postOfficeService.getOfficeById(123123)).thenThrow(PostOfficeNotExistException.class);

        Assertions.assertThrows(PostOfficeNotExistException.class, () -> service.register(123123, postPackage));
    }

    @Test
    public void testRegisterPostPackageToNotExistPostOffice() {
        PostPackage postPackage = new PostPackage(123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        Mockito.when(repository.save(postPackage)).thenReturn(postPackage);
        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getOfficeById(fromPostOffice.getIndex())).thenReturn(fromPostOffice);
        PostOffice toPostOffice = new PostOffice(123124, "", "");
        Mockito.when(postOfficeService.getOfficeById(toPostOffice.getIndex())).thenThrow(PostOfficeNotExistException.class);

        Assertions.assertThrows(PostOfficeNotExistException.class, () -> service.register(fromPostOffice.getIndex(), postPackage));
    }

    @Test
    public void testUpdateStatus() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        PackageDeliveryStatus status = PackageDeliveryStatus.ARRIVE;

        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getOfficeById(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, status);
        Mockito.when(packageDeliveryRepository.save(delivery)).thenReturn(delivery);
        Mockito.when(packageDeliveryRepository.findTopByPostPackageId(postPackage.getId())).thenReturn(delivery);

        service.updateDeliveryStatus(fromPostOffice.getIndex(), postPackage.getId(), status);

        Mockito.verify(repository).findById(123);
        Mockito.verify(postOfficeService).getOfficeById(fromPostOffice.getIndex());
    }

    @Test
    public void testUpdateStatusInAlreadyReceivedPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        PackageDeliveryStatus status = PackageDeliveryStatus.ARRIVE;

        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getOfficeById(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, PackageDeliveryStatus.RECEIVE);
        Mockito.when(packageDeliveryRepository.findTopByPostPackageId(postPackage.getId())).thenReturn(delivery);

        Assertions.assertThrows(PackageStatusException.class, () -> service.updateDeliveryStatus(123123, 123, status));
    }

    @Test
    public void testUpdateStatusWithNotExistOffice() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        PackageDeliveryStatus status = PackageDeliveryStatus.ARRIVE;

        Mockito.when(postOfficeService.getOfficeById(123123)).thenThrow(PostOfficeNotExistException.class);

        Assertions.assertThrows(PostOfficeNotExistException.class,
                () -> service.updateDeliveryStatus(123123, postPackage.getId(), status));
    }

    @Test
    public void testUpdateStatusWithNotExistPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        PackageDeliveryStatus status = PackageDeliveryStatus.ARRIVE;

        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getOfficeById(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(null);

        Assertions.assertThrows(PackageNotExistException.class,
                () -> service.updateDeliveryStatus(123123, postPackage.getId(), status));
    }

    @Test
    public void testGetDeliveryStatus() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        PackageDeliveryStatus status = PackageDeliveryStatus.ARRIVE;

        PostOffice postOffice = new PostOffice();
        postOffice.setIndex(123123);

        PackageDelivery delivery = new PackageDelivery(postPackage, postOffice, status);

        Mockito.when(packageDeliveryRepository.findTopByPostPackageId(postPackage.getId())).thenReturn(delivery);

        Assertions.assertEquals(delivery, service.getDeliveryStatus(123));
    }

    @Test
    public void testGetDeliveryStatusWithNotExistPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        Mockito.when(packageDeliveryRepository.findTopByPostPackageId(postPackage.getId())).thenReturn(null);

        Assertions.assertThrows(PackageNotExistException.class, () -> service.getDeliveryStatus(123));
    }

    @Test
    public void testGetDeliveryHistory() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        PostOffice postOffice = new PostOffice();
        postOffice.setIndex(123123);

        var history = List.of(
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.REGISTER),
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.ARRIVE),
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.DEPART)
        );

        Mockito.when(packageDeliveryRepository.findAllByPostPackageId(postPackage.getId())).thenReturn(history);

        Assertions.assertEquals(history, service.getPackageDeliveryHistory(123));
    }

    @Test
    public void testGetDeliveryHistoryWithNotExistPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        Mockito.when(packageDeliveryRepository.findAllByPostPackageId(postPackage.getId())).thenReturn(Collections.emptyList());

        Assertions.assertThrows(PackageNotExistException.class, () -> service.getDeliveryStatus(123));
    }

    @Test
    public void testGetPackageInfo() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        Assertions.assertEquals(postPackage, service.getPackageInfo(123));
    }

    @Test
    public void testGetPackageInfoWithNotExistPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(null);

        Assertions.assertThrows(PackageNotExistException.class, () -> service.getPackageInfo(123));
    }
}
