package lekarskaordinacija.app.service;

import lekarskaordinacija.app.viewModel.DailyExaminationsView;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DailyExaminationsService {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<DailyExaminationsView> allDailyExaminations() {
        return entityManager.createQuery("select d from DailyExaminationsView d",
                DailyExaminationsView.class).getResultList();
    }

}
