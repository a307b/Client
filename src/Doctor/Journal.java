package Doctor;

public class Journal
{
    private String patientName;
    private String CPR;
    private String printDate;
    private String startTDate;
    private String endTDate;
    private String dateWritten;
    private String noteType;
    private String examinationDetails;
    private String diagnose;
    private String interpretedBy;
    private String writtenBy;
    private String authenticatedBy;
    private String hospitalName;
    private String departmentName;
    private String uploadedBy;

    public Journal(String patientName, String CPR, String printDate, String startTDate, String endTDate, String dateWritten, String noteType, String examinationDetails, String diagnose, String interpretedBy, String writtenBy, String authenticatedBy, String hospitalName, String departmentName, String uploadedBy)
    {
        this.patientName = patientName;
        this.CPR = CPR;
        this.printDate = printDate;
        this.startTDate = startTDate;
        this.endTDate = endTDate;
        this.dateWritten = dateWritten;
        this.noteType = noteType;
        this.examinationDetails = examinationDetails;
        this.diagnose = diagnose;
        this.interpretedBy = interpretedBy;
        this.writtenBy = writtenBy;
        this.authenticatedBy = authenticatedBy;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.uploadedBy = uploadedBy;
    }

    @Override
    public String toString()
    {
        return  patientName + ":"
                + CPR + ":"
                + printDate + ":"
                + startTDate + ":"
                + endTDate + ":"
                + dateWritten + ":"
                + noteType + ":"
                + examinationDetails + ":"
                + diagnose + ":"
                + interpretedBy + ":"
                + writtenBy + ":"
                + authenticatedBy + ":"
                + hospitalName + ":"
                + departmentName + ":"
                + uploadedBy;
    }


}
