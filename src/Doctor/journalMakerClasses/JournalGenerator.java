package Doctor.journalMakerClasses;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.File;
import java.util.ArrayList;

public class JournalGenerator
{
    public void makeJournal(String saveLocation, String patientName, String CPR, String printDate, String startTDate, String endTDate,
                     String dateWritten, String noteType, String examinationDetails, String diagnose, String interpretedBy,
                     String writtenBy, String authenticatedBy, String hospitalName, String departmentName, String uploadedBy)  {
        try {
            // PDF save location
            final String savePath = "C:\\Journal\\" + saveLocation +".pdf";

            /* Creates directory if it does not exist */
            File file = new File("C:\\Journal");
            if(!file.exists()) {
                file.mkdirs();
            }
            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(new PdfWriter(savePath));

            // Initialize document
            Document document = new Document(pdf);

            // Adds standard font
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

            // Sets standard font and size for the document. Bold font is different from
            // the standard font and need to be specified in TextVariables
            final int standardFont = 10;
            document.setFont(font).setFontSize(standardFont);

            QueryVariables queryVariables = new QueryVariables(patientName,  CPR,  printDate,  startTDate,  endTDate,
                    dateWritten,  noteType,  examinationDetails,  diagnose,  interpretedBy,
                    writtenBy,  authenticatedBy,  hospitalName,  departmentName,  uploadedBy);
            TextVariables textVariables = new TextVariables(queryVariables);
            ParagraphVariables paragraphs = new ParagraphVariables(textVariables);

            // Creates and formats a table
            int tableFontSize = 11;
            float[] columnWidths = {6000, 6000, 6000};

            Table table = new Table(columnWidths);

            table.addCell(paragraphs.getPatientName()).setFontSize(tableFontSize);
            table.addCell(paragraphs.getCPR()).setFontSize(tableFontSize);
            table.addCell(paragraphs.getPrintDate()).setFontSize(tableFontSize);

            // Adds the table as the header of the document
            document.add(table);

            // Adds the Text objects to paragraphs
            ArrayList<Paragraph> paragraphsList = new ArrayList<Paragraph>();
            paragraphsList.add(paragraphs.getTitle());
            paragraphsList.add(paragraphs.getTreatmentDuration());
            paragraphsList.add(paragraphs.getDateWritten());
            paragraphsList.add(paragraphs.getNoteType());
            paragraphsList.add(paragraphs.getExaminationDetails());
            paragraphsList.add(paragraphs.getDiagnose());
            paragraphsList.add(paragraphs.getVerification());
            paragraphsList.add(paragraphs.getHospitalAndDepartmentName());

            // Adds the paragraphs to the document
            for(Paragraph paragraph: paragraphsList) {
                document.add(paragraph);
            }

            //Close document
            document.close();
        }catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }catch (java.io.IOException i) {
            i.printStackTrace();
        }
    }
}