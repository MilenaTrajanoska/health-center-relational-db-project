package lekarskaordinacija.app.service;

import lekarskaordinacija.app.model.Report;
import lekarskaordinacija.app.repository.ReportRepository;
import org.hibernate.type.BigIntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportDiagnosisTherapyService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ReportRepository reportRepository;


    @Transactional
    public boolean createReportWithDiagnosesAndTherapies(Report report) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try{
            List current = entityManager.createNativeQuery("select last_value(broj_na_izveshtaj) " +
                    "over (order by broj_na_izveshtaj desc) from izveshtaj;")
                    .getResultList();
            Optional id = current.stream()
                    .max(Comparator.comparing(BigInteger::longValue));
            if (id.isPresent()) {
                report.setBroj_na_izveshtaj(((BigInteger)current.get(0)).longValue() + 1);
            }
            reportRepository.save(report);
            transactionManager.commit(transactionStatus);
            return true;
        }catch (Exception e){
            transactionManager.rollback(transactionStatus);
        }

        return false;
    }

}
