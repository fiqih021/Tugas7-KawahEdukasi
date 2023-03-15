package Controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.inject.Inject;
import javax.ws.rs.*;

import Model.Komoditas;
import Service.JasperReportGeneratorService;
import Service.KomoditasService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import io.quarkus.scheduler.Scheduled;

@Path("/komoditas")
public class KomoditasResources {
    
    @Inject
    KomoditasService komoditasService;

    
    @GET
    public List<Komoditas> listKomoditas() {
        return komoditasService.getKomoditas();
    }

    @GET
    @Path("/{id}")
    public Komoditas get(UUID id) {
        return komoditasService.getKomoditasByUUID(id);
    }

    @POST
    public List<Komoditas> create(Komoditas Komoditas) {
        return komoditasService.createKomoditas(Komoditas);
    }

    @PUT
    @Path("/{id}")
    public Komoditas update(UUID id, Komoditas Komoditas) {
        return komoditasService.updateNamaKomoditas(id, Komoditas);
    }

    @PUT
    @Path("/add/{id}")
    public Komoditas addTotal(UUID id, Komoditas Komoditas) {
        return komoditasService.addTotal(id, Komoditas);
    }

    @DELETE
    @Path("/{id}")
    public List<Komoditas> delete(UUID id) {
        return komoditasService.deleteKomoditas(id);

    }

    @Inject
    ReactiveMailer reactiveMailer;
    @Inject
    JasperReportGeneratorService jasperReportGeneratorService;
    @Scheduled(cron="0 0 23 ? * SAT") // setiap Sabtu jam 23:00
    public Uni<Void> sendEmail() throws Exception{
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String monthYearString = date.format(formatter);
        String fileName = "Laporan " + monthYearString  + ".pdf";
        String outputFileName = "external_resources/generatedReport/" + fileName;
        String jasperReportPath = "external_resources/jasperReport/daftar-penghuni.jrxml";
        jasperReportGeneratorService.generatePdfReport(jasperReportPath, outputFileName);
        return reactiveMailer.send(
            Mail.withText("fiqihhamdareza021@gmail.com", "Hasil Panen Kebun Pak Dengklek", "Laporan Hasil Panen Pak Dengklek pada bulan "+ monthYearString )
            .addAttachment(fileName, new File(outputFileName), "application/pdf")
        );
    }




}
