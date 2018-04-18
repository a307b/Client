/* sample.Application;

import com.itextpdf.layout.element.Paragraph;

// Text objects are put together to make Paragraph objects
public class ParagraphVariables {

    private TextVariables texts = new TextVariables();

    private Paragraph patientName = new Paragraph().add(texts.getPatientNameBold()).add(texts.getPatientName());
    private Paragraph CPR = new Paragraph().add(texts.getCPRBold()).add(texts.getCPR());
    private Paragraph printDate = new Paragraph().add(texts.getPrintDateBold()).add(texts.getPrintDate());
    private Paragraph title = new Paragraph().add(texts.getTitle());
    private Paragraph treatmentDuration = new Paragraph().add(texts.getTreatmentDuration());
    private Paragraph dateWritten = new Paragraph().add(texts.getDateWrittenBold()).add(texts.getDateWritten());
    private Paragraph noteType = new Paragraph().add(texts.getNoteTypeBold()).add(texts.getNoteType());
    private Paragraph examinationDetails = new Paragraph().add(texts.getExaminationDetails());
    private Paragraph diagnose = new Paragraph().add(texts.getDiagnoseBold()).add(texts.getDiagnose());
    private Paragraph verification = new Paragraph().add(texts.getVerification());
    private Paragraph hospitalAndDepartmentName = new Paragraph().add(texts.getHospitalNameBold()).add(texts.getHospitalName()).add(texts.getDepartmentNameBold()).add(texts.getDepartmentName());


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
}*/
