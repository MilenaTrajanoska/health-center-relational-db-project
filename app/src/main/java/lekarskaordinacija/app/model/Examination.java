package lekarskaordinacija.app.model;

import lekarskaordinacija.app.enumeration.ExaminationType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Date;

@Entity
@Table(name = "pregled")
@Data
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broj_na_pregled")
    private long id;

    @NotNull
    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private ExaminationType examinationType;

    @NotNull
    @Column(name = "datum")
    private Date date;

    @NotNull
    @Column(name = "vreme_pochetok")
    private Time startTime;

    @Column(name = "vreme_kraj")
    private Time endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMBG_pacient", referencedColumnName = "EMBG")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMBG_doktor", referencedColumnName = "EMBG")
    private Doctor doctor;

}
