package com.example.post.models;

import java.time.LocalDateTime;

public record PackageDeliveryDto(
        long id,
        long packageId,
        long postOfficeId,
        PackageDeliveryStatus status,
        LocalDateTime time
) {
}
