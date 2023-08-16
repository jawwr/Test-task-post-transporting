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
@Table(name = "post_packages")
public class PostPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "receive_index", nullable = false)
    private long receiveIndex;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_address", nullable = false)
    private String receiverAddress;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageType type;
}
