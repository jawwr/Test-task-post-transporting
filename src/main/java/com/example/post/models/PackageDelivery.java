package com.example.post.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public PackageDelivery(PostPackage postPackage, PostOffice postOffice, PackageDeliveryStatus deliveryStatus) {
        this.postPackage = postPackage;
        this.postOffice = postOffice;
        this.deliveryStatus = deliveryStatus;
    }
}
