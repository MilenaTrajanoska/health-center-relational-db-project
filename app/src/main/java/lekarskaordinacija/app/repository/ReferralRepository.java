package lekarskaordinacija.app.repository;

import lekarskaordinacija.app.model.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, BigInteger> {
}
