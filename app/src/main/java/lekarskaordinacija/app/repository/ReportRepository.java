package lekarskaordinacija.app.repository;

import lekarskaordinacija.app.model.Report;
import lekarskaordinacija.app.util.ReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, ReportPK> {
}
