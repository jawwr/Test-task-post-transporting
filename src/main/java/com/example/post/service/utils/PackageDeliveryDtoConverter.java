package com.example.post.service.utils;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryDto;

public interface PackageDeliveryDtoConverter {
    PackageDeliveryDto convert(PackageDelivery delivery);
}
