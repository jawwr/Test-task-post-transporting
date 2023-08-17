package com.example.post.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post_offices")
public class PostOffice {
    @Id
    private long index;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "receiver_address")
    private String receiverAddress;
}
