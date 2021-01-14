package lekarskaordinacija.app.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@Embeddable
public class ReportPK implements Serializable {

    @Column(name = "broj_na_izveshtaj", insertable = false, updatable = false)
    private long broj_na_izveshtaj;

    @Column(name = "broj_na_pregled", insertable = false, updatable = false)
    private long broj_na_pregled;

}
