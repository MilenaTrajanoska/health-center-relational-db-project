package lekarskaordinacija.app.repository;

import lekarskaordinacija.app.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, BigInteger> {
}
