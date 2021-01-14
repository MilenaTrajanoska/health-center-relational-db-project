package lekarskaordinacija.app.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "doktor")
@Data
public class Doctor {

    @Id
    @Size(min=13, max=13, message = "Невалиден матичен број")
    @Column(name = "embg")
    @JoinColumn(name = "embg")
    private String EMBG;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "specijalizacija",
            joinColumns = {
                    @JoinColumn(name = "EMBG_doktor", referencedColumnName = "embg")
            }
    )
    @Column(name = "specijalizacija")
    List<String> specializations;
}
