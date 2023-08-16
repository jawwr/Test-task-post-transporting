package com.example.post.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "delivery_history")
public class PackageDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostPackage postPackage;

    @OneToOne
    private PostOffice postOffice;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private PackageDeliveryStatus deliveryStatus;

    @Column(name = "time", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;

    public PackageDelivery(PostPackage postPackage, PostOffice postOffice, PackageDeliveryStatus deliveryStatus, LocalDateTime time) {
        this.postPackage = postPackage;
        this.postOffice = postOffice;
        this.deliveryStatus = deliveryStatus;
        this.time = time;
    }
}
