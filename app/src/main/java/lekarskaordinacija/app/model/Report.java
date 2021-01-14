package lekarskaordinacija.app.model;

import lekarskaordinacija.app.enumeration.ReportType;
import lekarskaordinacija.app.util.ReportPK;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "izveshtaj")
@Data
@IdClass(ReportPK.class)
public class Report {

    public Report() {
        this.therapies = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    @Id
    @Column(name = "broj_na_izveshtaj")
    private long broj_na_izveshtaj;

    @Id
    @Column(name = "broj_na_pregled")
    private long broj_na_pregled;

    @NotNull
    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private ReportType type;

    @NotNull
    @Column(name = "datum")
    private Date date;

    @Column(name = "anamneza")
    private String anamnesis;

    @Column(name = "naod")
    private String medicalFinding;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("broj_na_pregled")
    @JoinColumn(name = "broj_na_pregled", referencedColumnName = "broj_na_pregled")
    private Examination examination;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "dijagnoza",
            joinColumns ={
                @JoinColumn(name = "broj_na_izveshtaj", referencedColumnName = "broj_na_izveshtaj"),
                @JoinColumn(name = "broj_na_pregled", referencedColumnName = "broj_na_pregled")
            }
    )
    @Column(name = "dijagnoza")
    private List<String> diagnoses;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "terapija",
            joinColumns ={
                    @JoinColumn(name = "broj_na_izveshtaj", referencedColumnName = "broj_na_izveshtaj"),
                    @JoinColumn(name = "broj_na_pregled", referencedColumnName = "broj_na_pregled")
            }
    )
    @Column(name = "terapija")
    private List<String> therapies;

}
