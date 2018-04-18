package sample.Application;

// Contains input received from the queury
public class QueryVariables {
    private String patientName = "Bob";
    private String CPR = "1234567890";
    private String printDate = "21.3.2018";
    // T = treatment
    private String startTDate = "10.3.2018";
    private String endTDate = "20.3.2018";
    private String dateWritten = "19.3.2018";
    private String noteType = "Røntgennote";
    private String examinationDetails = "Undersøgelsen er foretaget som postoperativ kontrol.\n" +
            "Der ses sammenholdt medundersøgelsen fra29.07.17, at den tidligere\n" +
            "påviste fraktur vedproksimalehøjre femur, nu er behandlet med total\n" +
            "hoftealloplastik, som ses i god stilling i alle planer";
    private String diagnose = "postoperativ kontrol";
    private String interpretedBy = "Anders Bensen";
    private String writtenBy = "Ib Jensen";
    private String authenticatedBy = "Dennis Krieger";
    private String hospitalName = "Aalborg Universitetshospital";
    private String departmentName = "Alb Røntgen Amb";
    private String uploadedBy = "Alb O-kir sengeafdeling";

    public String getPatientName() {
        return patientName;
    }

    public String getCPR() {
        return CPR;
    }

    public String getPrintDate() {
        return printDate;
    }

    public String getStartTDate() {
        return startTDate;
    }

    public String getEndTDate() {
        return endTDate;
    }

    public String getDateWritten() {
        return dateWritten;
    }

    public String getNoteType() {
        return noteType;
    }

    public String getExaminationDetails() {
        return examinationDetails;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getInterpretedBy() {
        return interpretedBy;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public String getAuthenticatedBy() {
        return authenticatedBy;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }
}