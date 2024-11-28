package com.github.bat333.stockroom.domain;

import com.github.bat333.stockroom.model.DataPart;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @Column(name = "actives",nullable = false )
    private Boolean active = true;

    public Part( DataPart dataPart) {
        this.name = dataPart.name();
        this.amount = dataPart.amount();
        this.image = dataPart.imageData();
    }

    public Part update(DataPart part) {
        if(part.name() != null){
            this.name = part.name();
        }
        if(part.imageData() != null){
            this.image = part.imageData();
        }
        if(part.amount() > 0){
            this.amount = part.amount();
        }

        return this;
    }

    public void delete() {
        this.active = false;
    }
}
