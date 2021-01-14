package lekarskaordinacija.app.service;

import lekarskaordinacija.app.enumeration.ExaminationType;
import lekarskaordinacija.app.formModel.RefferalExaminationForm;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.BigIntegerType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ReferralService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean createReferral(RefferalExaminationForm form) {

        try {
            entityManager.createNativeQuery("begin;\n" +
                    "insert into upat (datum, prichina, opis, broj_na_pregled," +
                    "embg_pacient, embg_doktor_kreira, embg_doktor_upaten_kon)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?);\n" +
                    "insert into pregled(tip, datum, vreme_pochetok, vreme_kraj, embg_pacient, embg_doktor)\n" +
                    "values (?, ?, ?, ?, ?, ?);\n" +
                    "commit;")
            .setParameter(1, form.getReferralDate())
            .setParameter(2, form.getReason())
            .setParameter(3, form.getDescription())
            .setParameter(4, new TypedParameterValue(BigIntegerType.INSTANCE, null))
            .setParameter(5, form.getEmbgPatient())
            .setParameter(6, form.getEmbgDoctorCreator())
            .setParameter(7, form.getEmbgDoctorReffered())
            .setParameter(8, form.getExaminationType()
                    .equals(ExaminationType.kontrolen) ? "kontrolen" : "redoven")
            .setParameter(9, form.getExaminationDate())
            .setParameter(10, form.getExaminationStartTime())
            .setParameter(11, form.getExaminationEndTime())
            .setParameter(12, form.getEmbgPatient())
            .setParameter(13, form.getEmbgDoctorReffered())
            .executeUpdate();
        }catch (Exception e){
            return false;
        }

        return true;
    }
}

