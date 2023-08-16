package com.example.post.service;

import com.example.post.exception.PackageNotExistException;
import com.example.post.exception.PackageStatusException;
import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;
import com.example.post.repositories.PackageDeliveryRepository;
import com.example.post.repositories.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageServiceImpl implements PackageService {
    private final PackageRepository repository;
    private final PostOfficeService postOfficeService;
    private final PackageDeliveryRepository packageDeliveryRepository;

    @Override
    @Transactional
    public long register(long postOfficeIndex, PostPackage postPackage) {
        var fromPostOffice = postOfficeService.getOfficeById(postOfficeIndex);
        var receivePostOffice = postOfficeService.getOfficeById(postPackage.getReceiveIndex());
        var savedPackage = repository.save(postPackage);
        PackageDelivery delivery = new PackageDelivery(savedPackage, fromPostOffice, PackageDeliveryStatus.REGISTER, LocalDateTime.now());
        packageDeliveryRepository.save(delivery);
        return savedPackage.getId();
    }

    @Override
    @Transactional
    public void updateDeliveryStatus(long postOfficeIndex,
                                     long packageId,
                                     PackageDeliveryStatus status) {
        var fromPostOffice = postOfficeService.getOfficeById(postOfficeIndex);
        var savedPackage = repository.findById(packageId);
        if (savedPackage == null) {
            throw new PackageNotExistException("Package with id " + packageId + " does not exist");
        }
        if (getDeliveryStatus(packageId).getDeliveryStatus() == PackageDeliveryStatus.RECEIVE) {
            throw new PackageStatusException("Package with id " + packageId + " already received");
        }
        PackageDelivery delivery = new PackageDelivery(savedPackage, fromPostOffice, status, LocalDateTime.now());
        packageDeliveryRepository.save(delivery);
    }

    @Override
    public PackageDelivery getDeliveryStatus(long packageId) {
        var delivery = packageDeliveryRepository.findTopByPostPackageId(packageId);
        if (delivery == null) {
            throw new PackageNotExistException("Package with id " + packageId + " does not exist");
        }
        return delivery;
    }

    @Override
    public List<PackageDelivery> getPackageDeliveryHistory(long packageId) {
        var history = packageDeliveryRepository.findAllByPostPackageId(packageId);
        if (history.isEmpty()) {
            throw new PackageNotExistException("Package with id " + packageId + " does not exist");
        }
        return history;
    }

    @Override
    public PostPackage getPackageInfo(long packageId) {
        var savedPackage = repository.findById(packageId);
        if (savedPackage == null) {
            throw new PackageNotExistException("Package with id " + packageId + " does not exist");
        }
        return savedPackage;
    }
}
