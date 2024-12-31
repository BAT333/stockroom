package com.github.bat333.stockroom.domain;

import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataUpdatePart;
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
    @Column(name = "cods",nullable = false)
    private Long cod;
    @Column(name = "names",nullable = false)
    private String name;
    @Lob
    @Column(name = "images",nullable = false,length = 999999999)
    private byte[] image;
    @Column(name = "quantities",nullable = false)
    private double amount;
    @Column(name = "actives",nullable = false )
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "sector")
    private Sector sector;
    @Version
    private Integer version;

    public Part( DataPart dataPart) {
        this.cod = dataPart.cod();
        this.name = dataPart.name();
        this.amount = dataPart.amount();
        this.image = dataPart.image();
    }

    public Part(@Valid DataPart dataPart, Sector sector) {
        this.cod = dataPart.cod();
        this.name = dataPart.name();
        this.amount = dataPart.amount();
        this.image = dataPart.image();
        this.sector = sector;
    }

    public Part(@Valid DataPart dataPart, Sector sector, byte[] img) {
        this.cod = dataPart.cod();
        this.name = dataPart.name();
        this.amount = dataPart.amount();
        this.image = img;
        this.sector = sector;
    }

    public Part update(DataUpdatePart part, Sector sector) {
        if(part.cod() != null){
            this.cod = part.cod();
        }
        if(part.name() != null){
            this.name = part.name();
        }
        if(part.image() != null){
            this.image = part.image();
        }
        if(part.amount() > 0){
            this.amount = part.amount();
        }
        if(part.sector() != null){
            System.out.println(sector);
            this.sector = sector;
        }

        return this;
    }

    public void delete() {
        this.active = false;
    }
}
