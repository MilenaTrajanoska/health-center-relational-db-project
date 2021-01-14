package lekarskaordinacija.app.viewModel;

import lekarskaordinacija.app.enumeration.ReportType;
import lombok.Data;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Table(name = "dijagnozi_za_pacienti")
@Immutable
@Entity
public class DiagnosisPatientView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ime_prezime")
    private String fullName;

    @Column(name = "zdravstvena_legitimacija")
    private String medicalCardID;

    @Column(name = "broj_na_pregled")
    private long examinationId;

    @Column(name = "datum")
    private Date examinationDate;

    @Column(name = "tip_izveshtaj")
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(name = "dijagnoza")
    private String diagnosis;

    @Column(name = "terapija")
    private String therapy;

}
