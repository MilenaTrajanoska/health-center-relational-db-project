package lekarskaordinacija.app.viewModel;

import lekarskaordinacija.app.enumeration.ExaminationType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Data
@Table(name = "pregledi_za_tekovniot_den")
@Entity
public class DailyExaminationsView {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name = "broj_na_pregled")
    private long examinationId;

    @Column(name = "tip_na_pregled")
    @Enumerated(EnumType.STRING)
    private ExaminationType examinationType;

    @Column(name = "datum")
    private Date date;

    @Column(name = "vreme_pochetok")
    private Time startTime;

    @Column(name = "vreme_kraj")
    private Time endTime;

    @Column(name = "zdravstvena_legitimacija")
    private String medicalCardId;

    @Column(name = "ime_pacient")
    private String patientName;

    @Column(name = "ime_doktor")
    private String doctorName;
}
