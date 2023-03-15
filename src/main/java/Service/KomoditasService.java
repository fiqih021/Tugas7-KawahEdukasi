package Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;



import Model.Komoditas;

@ApplicationScoped
public class KomoditasService {

    public List<Komoditas> getKomoditas() {
        List<Komoditas> listKomoditas = Komoditas.listAll();
        return listKomoditas;
    }

    public Komoditas getKomoditasByUUID(UUID id) {
        return Komoditas.findById(id);
    }

    @Transactional
    public List<Komoditas> createKomoditas(Komoditas komoditas) {
        komoditas.id = UUID.randomUUID();
        komoditas.createAt = LocalDate.now();
        komoditas.updateAt = LocalDate.now();
        komoditas.persist();
        return Komoditas.listAll();
    }

    @Transactional
    public Komoditas updateNamaKomoditas(UUID id, Komoditas komoditas) {
        Komoditas entity = Komoditas.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        // map all fields from the Penghuni parameter to the existing entity
        entity.namaBarang = komoditas.namaBarang;


        return entity;
    }

    @Transactional
    public Komoditas addTotal(UUID id, Komoditas komoditas){
        Komoditas entity = Komoditas.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.total = komoditas.total + entity.total;
        entity.updateAt = LocalDate.now();

        return entity;
    }

    @Transactional
    public List<Komoditas> deleteKomoditas(UUID id) {
        Komoditas entity = Komoditas.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
        return Komoditas.listAll();
    }


}
