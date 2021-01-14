package lekarskaordinacija.app.service;

import lekarskaordinacija.app.model.Examination;
import lekarskaordinacija.app.repository.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaminationService {

    @Autowired
    private ExaminationRepository examinationRepository;

    public void makeAppointment(Examination examination) {
        try {
            examinationRepository.save(examination);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}

