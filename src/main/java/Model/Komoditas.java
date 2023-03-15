package Model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Komoditas extends PanacheEntityBase {
    @Id
    @Column(name = "id",nullable = false)
    public UUID id;
    public String namaBarang;
    public int total;
    public LocalDate createAt;
    public LocalDate updateAt;

    public Komoditas(UUID id, String namaBarang, int total, LocalDate createAt, LocalDate updateAt) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.total = total;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
    
    public Komoditas() {
    }
}
