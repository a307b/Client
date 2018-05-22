package Doctor.journalMakerClasses;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;

// Query variables are converted to the Text objects needed to form the necessary paragraphs.
// To use formatting in Itext7 strings need to become text objects before they are used in paragraphs.

public class TextVariables {
    private PdfFont bold;

    //Text objects needed for the paragraphs in the header table
    private Text patientNameBold;
    private com.itextpdf.layout.element.Text patientName;
    private Text CPRBold;
    private Text CPR;
    private Text printDateBold;
    private Text printDate;
    // Text objects needed for the rest of the document
    private Text title;
    private Text treatmentDuration;
    private Text dateWrittenBold;
    private Text dateWritten;
    private Text noteTypeBold;
    private Text noteType;
    private Text examinationDetails;
    private Text diagnoseBold;
    private Text diagnose;
    private Text verification;
    private Text hospitalNameBold;
    private Text hospitalName;
    private Text departmentNameBold;
    private Text departmentName;

    //I have initialized the variables in a constructor contrary to the other variable classes
    //since i need to handle the exception created by createFont(StandardFonts.TIMES_BOLD),
    //and you cannot handle exception when you make variables in a class directly
    public TextVariables(QueryVariables queryVariables) {
        try {
            bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

            patientNameBold = new Text("Notater for:\n").setFont(bold);
            patientName = new Text(queryVariables.getPatientName());

            CPRBold = new Text("CPR:\n").setFont(bold);
            CPR = new Text(queryVariables.getCPR());

            printDateBold = new Text("Udskriftsdato:\n").setFont(bold);
            printDate = new Text(queryVariables.getPrintDate());


            // Text objects needed for the rest of the document
            title = new Text("Notater til forløb oprettet af " + queryVariables.getHospitalName() + ", " +
                    queryVariables.getUploadedBy()).setFontSize(20);
            treatmentDuration = new Text("Startdato: " + queryVariables.getStartTDate() + "    " + "Slutdato: " + queryVariables.getEndTDate()).setFontSize(15);

            dateWrittenBold = new Text("Dato \n").setFont(bold);
            dateWritten = new Text(queryVariables.getDateWritten());

            noteTypeBold = new Text("Titel \n").setFont(bold);
            noteType = new Text(queryVariables.getNoteType());

            examinationDetails = new Text(queryVariables.getExaminationDetails());

            diagnoseBold = new Text("Diagnose \n").setFont(bold);
            diagnose = new Text(queryVariables.getDiagnose());

            verification = new Text("/Fortolket af " + queryVariables.getInterpretedBy()
                    + "\n/Skrevet af " + queryVariables.getWrittenBy() + "\n/Godkendt af " + queryVariables.getAuthenticatedBy());

            hospitalNameBold = new Text("\n\n\nHospital:        ").setFont(bold);
            hospitalName = new Text(queryVariables.getHospitalName());

            departmentNameBold = new Text("\nAfdeling:        ").setFont(bold);
            departmentName = new Text(queryVariables.getDepartmentName());
        }catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public Text getPatientNameBold() {
        return patientNameBold;
    }

    public Text getPatientName() {
        return patientName;
    }

    public Text getCPRBold() {
        return CPRBold;
    }

    public Text getCPR() {
        return CPR;
    }

    public Text getPrintDateBold() {
        return printDateBold;
    }

    public Text getPrintDate() {
        return printDate;
    }

    public Text getTitle() {
        return title;
    }

    public Text getTreatmentDuration() {
        return treatmentDuration;
    }

    public Text getDateWrittenBold() {
        return dateWrittenBold;
    }

    public Text getDateWritten() {
        return dateWritten;
    }

    public Text getNoteTypeBold() {
        return noteTypeBold;
    }

    public Text getNoteType() {
        return noteType;
    }

    public Text getExaminationDetails() {
        return examinationDetails;
    }

    public Text getDiagnoseBold() {
        return diagnoseBold;
    }

    public Text getDiagnose() {
        return diagnose;
    }

    public Text getVerification() {
        return verification;
    }

    public Text getHospitalNameBold() {
        return hospitalNameBold;
    }

    public Text getHospitalName() {
        return hospitalName;
    }

    public Text getDepartmentNameBold() {
        return departmentNameBold;
    }

    public Text getDepartmentName() {
        return departmentName;
    }
}
