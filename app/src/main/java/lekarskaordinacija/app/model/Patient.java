package lekarskaordinacija.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pacient")
@Data
public class Patient{

    @Id
    @Size(min=13, max=13, message = "Невалиден матичен број")
    @Column(name = "embg")
    @JoinColumn(name = "embg")
    private String EMBG;

    @NotNull
    @Size(min = 11, max = 11, message = "Невалиден број на здравствена легитимација")
    @Column(name = "zdravstvena_legitimacija")
    String medicalCardID;

    @NotNull
    @Size(min = 9, max = 9, message = "Невалиден број на осигуреник")
    @Column(name = "broj_na_osigurenik")
    String insuranceNumber;

    @NotNull
    @Column(name = "datum_ragjanje")
    Date dateOfBirth;

    @Column(name = "adresa")
    String address;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMBG_matichen_lekar", referencedColumnName = "embg")
    Doctor doctor;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "kontakt",
            joinColumns = {
                    @JoinColumn(name = "EMBG_pacient", referencedColumnName = "embg")
            }
    )
    @Column(name = "kontakt")
    List<String> contacts;

}
