package lekarskaordinacija.app.formModel;

import lekarskaordinacija.app.enumeration.ExaminationType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;

@Data
public class RefferalExaminationForm {

    @NotNull
    private Date referralDate;
    private String reason;
    private String description;
    @NotNull
    @Size(min=13, max=13, message = "Невалиден матичен број")
    private String embgPatient;
    @NotNull
    @Size(min=13, max=13, message = "Невалиден матичен број")
    private String embgDoctorCreator;
    @NotNull
    @Size(min=13, max=13, message = "Невалиден матичен број")
    private String embgDoctorReffered;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ExaminationType examinationType;
    private String type;
    @NotNull
    private Date examinationDate;
    @NotNull
    private Time examinationStartTime;
    private Time examinationEndTime;

    public void setType(ExaminationType type) {
        this.type = type.equals(ExaminationType.kontrolen) ? "kontrolen" : "redoven";
    }

}
