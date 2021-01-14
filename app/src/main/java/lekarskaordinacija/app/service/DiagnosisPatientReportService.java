package lekarskaordinacija.app.service;

import lekarskaordinacija.app.viewModel.DiagnosisPatientView;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DiagnosisPatientReportService {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<DiagnosisPatientView> getDiagnosisReport() {
       return entityManager.createQuery("select v from DiagnosisPatientView v",
               DiagnosisPatientView.class).getResultList();
    }

}
