package com.example.post.service.utils;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryDto;
import org.springframework.stereotype.Component;

@Component
public class PackageDeliveryMapper implements PackageDeliveryDtoConverter {
    @Override
    public PackageDeliveryDto convert(PackageDelivery delivery) {
        return new PackageDeliveryDto(
                delivery.getId(),
                delivery.getPostPackage().getId(),
                delivery.getPostOffice().getIndex(),
                delivery.getDeliveryStatus(),
                delivery.getTime()
        );
    }
}
