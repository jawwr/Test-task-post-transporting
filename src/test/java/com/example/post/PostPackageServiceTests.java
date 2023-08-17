package com.example.post;

import com.example.post.exception.PackageNotExistException;
import com.example.post.exception.PackageStatusException;
import com.example.post.exception.PostOfficeNotExistException;
import com.example.post.models.*;
import com.example.post.repositories.PackageDeliveryRepository;
import com.example.post.repositories.PackageRepository;
import com.example.post.service.PackageServiceImpl;
import com.example.post.service.PostOfficeService;
import com.example.post.service.utils.PackageDeliveryDtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
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
    @Mock
    private PackageDeliveryDtoConverter converter;

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
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        PostOffice toPostOffice = new PostOffice(123124, "", "");
        Mockito.when(postOfficeService.getPostOfficeByIndex(toPostOffice.getIndex())).thenReturn(toPostOffice);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, PackageDeliveryStatus.REGISTER, LocalDateTime.now());
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
        Mockito.when(postOfficeService.getPostOfficeByIndex(123123)).thenThrow(PostOfficeNotExistException.class);

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
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);
        PostOffice toPostOffice = new PostOffice(123124, "", "");
        Mockito.when(postOfficeService.getPostOfficeByIndex(toPostOffice.getIndex())).thenThrow(PostOfficeNotExistException.class);

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
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, status, LocalDateTime.now());
        Mockito.when(packageDeliveryRepository.save(delivery)).thenReturn(delivery);
        Mockito.when(packageDeliveryRepository.findLastMovement(postPackage.getId())).thenReturn(delivery);

        var dto = new PackageDeliveryDto(delivery.getId(), delivery.getPostPackage().getId(), delivery.getPostOffice().getIndex(), delivery.getDeliveryStatus(), delivery.getTime());
        Mockito.when(converter.convert(delivery)).thenReturn(dto);

        service.updateDeliveryStatus(fromPostOffice.getIndex(), postPackage.getId(), status);

        Mockito.verify(repository).findById(123);
        Mockito.verify(postOfficeService).getPostOfficeByIndex(fromPostOffice.getIndex());
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
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, PackageDeliveryStatus.RECEIVE, LocalDateTime.now());
        Mockito.when(packageDeliveryRepository.findLastMovement(postPackage.getId())).thenReturn(delivery);
        var dto = new PackageDeliveryDto(delivery.getId(), delivery.getPostPackage().getId(), delivery.getPostOffice().getIndex(), delivery.getDeliveryStatus(), delivery.getTime());
        Mockito.when(converter.convert(delivery)).thenReturn(dto);

        Assertions.assertThrows(PackageStatusException.class, () -> service.updateDeliveryStatus(123123, 123, status));
    }

    @Test
    public void testUpdateStatusToRegisterInRegisteredPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);
        PackageDeliveryStatus status = PackageDeliveryStatus.REGISTER;

        PostOffice fromPostOffice = new PostOffice(123123, "", "");
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

        Mockito.when(repository.findById(postPackage.getId())).thenReturn(postPackage);

        PackageDelivery delivery = new PackageDelivery(postPackage, fromPostOffice, PackageDeliveryStatus.RECEIVE, LocalDateTime.now());
        Mockito.when(packageDeliveryRepository.findLastMovement(postPackage.getId())).thenReturn(delivery);
        var dto = new PackageDeliveryDto(delivery.getId(), delivery.getPostPackage().getId(), delivery.getPostOffice().getIndex(), delivery.getDeliveryStatus(), delivery.getTime());
        Mockito.when(converter.convert(delivery)).thenReturn(dto);

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

        Mockito.when(postOfficeService.getPostOfficeByIndex(123123)).thenThrow(PostOfficeNotExistException.class);

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
        Mockito.when(postOfficeService.getPostOfficeByIndex(fromPostOffice.getIndex())).thenReturn(fromPostOffice);

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

        PackageDelivery delivery = new PackageDelivery(postPackage, postOffice, status, LocalDateTime.now());

        Mockito.when(packageDeliveryRepository.findLastMovement(postPackage.getId())).thenReturn(delivery);

        Assertions.assertEquals(converter.convert(delivery), service.getDeliveryStatus(123));
    }

    @Test
    public void testGetDeliveryStatusWithNotExistPackage() {
        PostPackage postPackage = new PostPackage(123,
                123124,
                "receiver name",
                "receiver address",
                PackageType.LETTER);

        Mockito.when(packageDeliveryRepository.findLastMovement(postPackage.getId())).thenReturn(null);

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
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.REGISTER, LocalDateTime.now()),
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.ARRIVE, LocalDateTime.now().plusDays(1)),
                new PackageDelivery(postPackage, postOffice, PackageDeliveryStatus.DEPART, LocalDateTime.now().plusDays(2))
        );

        var dto1 = new PackageDeliveryDto(history.get(0).getId(),
                history.get(0).getPostPackage().getId(),
                history.get(0).getPostOffice().getIndex(),
                history.get(0).getDeliveryStatus(),
                history.get(0).getTime());
        var dto2 = new PackageDeliveryDto(history.get(1).getId(),
                history.get(1).getPostPackage().getId(),
                history.get(1).getPostOffice().getIndex(),
                history.get(1).getDeliveryStatus(),
                history.get(1).getTime());
        var dto3 = new PackageDeliveryDto(history.get(2).getId(),
                history.get(2).getPostPackage().getId(),
                history.get(2).getPostOffice().getIndex(),
                history.get(2).getDeliveryStatus(),
                history.get(2).getTime());
        var result = List.of(
                dto1,
                dto2,
                dto3
        );
        Mockito.when(converter.convert(history.get(0))).thenReturn(dto1);
        Mockito.when(converter.convert(history.get(1))).thenReturn(dto2);
        Mockito.when(converter.convert(history.get(2))).thenReturn(dto3);

        Mockito.when(packageDeliveryRepository.findAllByPostPackageId(postPackage.getId())).thenReturn(history);

        Assertions.assertEquals(result, service.getPackageDeliveryHistory(123));
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
