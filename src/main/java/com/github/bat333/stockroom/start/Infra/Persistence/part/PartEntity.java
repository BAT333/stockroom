package com.github.bat333.stockroom.start.Infra.Persistence.part;


import com.github.bat333.stockroom.start.Infra.Persistence.sector.SectorEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "parts")
public class PartEntity {
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
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "sector")
    private SectorEntity sector;
    @Version
    private Integer version;

    public void delete() {
        this.active = false;
    }

    public void update(PartEntity part) {
        if(part.cod != null){
            this.cod = part.cod;
        }
        if(part.name != null){
            this.name = part.name;
        }
        if(part.image != null){
            this.image = part.image;
        }
        if(part.amount > 0){
            this.amount = part.amount;
        }
        if(part.sector != null){
            this.sector = part.sector;
        }


    }

}
