package com.github.bat333.stockroom.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "names",nullable = false,unique = true)
    private String name;
    @Lob
    @Column(name = "images",nullable = false)
    private byte[] image;
    @Column(name = "quantities",nullable = false)
    private double amount;
    @Column(name = "actives",nullable = false,columnDefinition = "TINYINT(1) DEFAULT 1" )
    private Boolean active = true;
}
