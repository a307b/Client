package Doctor.journalMakerClasses;
import com.itextpdf.layout.element.Paragraph;

// Text objects are put together to make Paragraph objects
public class ParagraphVariables {

    private Paragraph patientName;
    private Paragraph CPR;
    private Paragraph printDate;
    private Paragraph title;
    private Paragraph treatmentDuration;
    private Paragraph dateWritten;
    private Paragraph noteType;
    private Paragraph examinationDetails;
    private Paragraph diagnose;
    private Paragraph verification;
    private Paragraph hospitalAndDepartmentName;

    public ParagraphVariables(TextVariables textVariables) {
        this.patientName = new Paragraph().add(textVariables.getPatientNameBold()).add(textVariables.getPatientName());
        this.CPR = new Paragraph().add(textVariables.getCPRBold()).add(textVariables.getCPR());
        this.printDate = new Paragraph().add(textVariables.getPrintDateBold()).add(textVariables.getPrintDate());
        this.title = new Paragraph().add(textVariables.getTitle());
        this.treatmentDuration = new Paragraph().add(textVariables.getTreatmentDuration());
        this.dateWritten = new Paragraph().add(textVariables.getDateWrittenBold()).add(textVariables.getDateWritten());
        this.noteType = new Paragraph().add(textVariables.getNoteTypeBold()).add(textVariables.getNoteType());
        this.examinationDetails = new Paragraph().add(textVariables.getExaminationDetails());
        this.diagnose = new Paragraph().add(textVariables.getDiagnoseBold()).add(textVariables.getDiagnose());
        this.verification = new Paragraph().add(textVariables.getVerification());
        this.hospitalAndDepartmentName = new Paragraph().add(textVariables.getHospitalNameBold()).add(textVariables.getHospitalName()).add(textVariables.getDepartmentNameBold()).add(textVariables.getDepartmentName());

    }

    public Paragraph getPatientName() {
        return patientName;
    }

    public Paragraph getCPR() {
        return CPR;
    }

    public Paragraph getPrintDate() {
        return printDate;
    }

    public Paragraph getTitle() {
        return title;
    }

    public Paragraph getTreatmentDuration() {
        return treatmentDuration;
    }

    public Paragraph getDateWritten() {
        return dateWritten;
    }

    public Paragraph getNoteType() {
        return noteType;
    }

    public Paragraph getExaminationDetails() {
        return examinationDetails;
    }

    public Paragraph getDiagnose() {
        return diagnose;
    }

    public Paragraph getVerification() {
        return verification;
    }

    public Paragraph getHospitalAndDepartmentName() {
        return hospitalAndDepartmentName;
    }
}
