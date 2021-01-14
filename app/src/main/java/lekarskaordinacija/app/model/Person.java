package lekarskaordinacija.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "chovek")
@Data
public class Person {

    @Id
    @Size(min=13, max=13, message = "Невалиден матичен број")
    @Column(name = "embg")
    String EMBG;

    @NotNull
    @Column(name = "ime")
    String name;

    @NotNull
    @Column(name = "prezime")
    String surname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "embg", referencedColumnName = "embg", insertable = false, updatable = false)
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "embg", referencedColumnName = "embg", insertable = false, updatable = false)
    private Doctor doctor;

}
