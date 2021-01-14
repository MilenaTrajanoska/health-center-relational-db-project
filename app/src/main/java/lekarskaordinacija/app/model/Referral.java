package lekarskaordinacija.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "upat")
@Data
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "broj_na_upat")
    private long id;

    @NotNull
    @Column(name = "datum")
    private Date date;

    @Column(name = "prichina")
    String reason;

    @Column(name = "opis")
    String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broj_na_pregled", referencedColumnName = "broj_na_pregled")
    private Examination examination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMBG_pacient", referencedColumnName = "EMBG")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMBG_doktor_kreira", referencedColumnName = "EMBG")
    private Doctor doctorCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMBG_doktor_upaten_kon", referencedColumnName = "EMBG")
    private Doctor doctorReferred;

}
