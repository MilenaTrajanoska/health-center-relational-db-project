package lekarskaordinacija.app.controller;

import lekarskaordinacija.app.formModel.RefferalExaminationForm;
import lekarskaordinacija.app.model.Examination;
import lekarskaordinacija.app.model.Person;
import lekarskaordinacija.app.model.Report;
import lekarskaordinacija.app.service.DailyExaminationsService;
import lekarskaordinacija.app.service.DiagnosisPatientReportService;
import lekarskaordinacija.app.service.ExaminationService;
import lekarskaordinacija.app.service.PersonService;
import lekarskaordinacija.app.service.ReferralService;
import lekarskaordinacija.app.service.ReportDiagnosisTherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DailyExaminationsService dailyExaminationsService;

    @Autowired
    private DiagnosisPatientReportService diagnosisPatientReportService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReportDiagnosisTherapyService reportDiagnosisTherapyService;

    @GetMapping("/dailyExaminations")
    public String getDailyExaminationsAll(Model model) {
        model.addAttribute("list", dailyExaminationsService.allDailyExaminations());
        return "dailyExaminationsList";
    }

    @GetMapping("/patientReports")
    public String getPatientDiagnosisReportAll(Model model) {
        model.addAttribute("list", diagnosisPatientReportService.getDiagnosisReport());
        return "diagnosisPatientReport";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/appointment")
    public String makeAppointment(Model model) {
        model.addAttribute("appointment", new Examination());
        List<Person> personList = personService.getAllUsers();
        model.addAttribute("allDoctors",personList );
        return "appointmentForm";
    }

    @PostMapping("/appointment")
    public String makeAppoinmentPost(@ModelAttribute("appointment") @Valid Examination appointment, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("allDoctors",personService.getAllUsers());
            model.addAttribute("invalidParameters", true);
            return "appointmentForm";
        }
        try{
            examinationService.makeAppointment(appointment);
            return "redirect:/";
        } catch (RuntimeException exception) {
            model.addAttribute("allDoctors",personService.getAllUsers());
            model.addAttribute("error", true);
            return "appointmentForm";
        }
    }

    @GetMapping("/createReferral")
    public String createReferral(Model model) {
        model.addAttribute("referralForm", new RefferalExaminationForm());
        List<Person> personList = personService.getAllUsers();
        model.addAttribute("allDoctors",personList );
        return "referralForm";
    }

    @PostMapping("/createReferral")
    public String createReferralPost(@ModelAttribute("refferalForm") @Valid RefferalExaminationForm refferalForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("allDoctors",personService.getAllUsers());
            model.addAttribute("invalidParameters", true);
            return "referralForm";
        }
        try{
            referralService.createReferral(refferalForm);
            return "redirect:/";
        } catch (RuntimeException exception) {
            model.addAttribute("allDoctors",personService.getAllUsers());
            model.addAttribute("error", true);
            return "appointmentForm";
        }
    }

    @GetMapping("/createReport")
    public String createReport(Model model) {
        model.addAttribute("report", new Report());
        return "reportForm";
    }

    @PostMapping("/createReport")
    public String createReportPost(@ModelAttribute("report") @Valid Report report, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidParameters", true);
            return "reportForm";
        }
        try{
            reportDiagnosisTherapyService.createReportWithDiagnosesAndTherapies(report);
            return "redirect:/";
        } catch (RuntimeException exception) {
            model.addAttribute("error", true);
            return "reportForm";
        }
    }

}
