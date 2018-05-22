package Doctor.journalMakerClasses;

import Doctor.journalMakerClasses.ParagraphVariables;
import Doctor.journalMakerClasses.QueryVariables;
import Doctor.journalMakerClasses.TextVariables;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.util.ArrayList;

// Data transfer objects DTO er den type klasser jeg har lavet som kun har variable
// De bliver beskrevet på clean code side 100 (131 PDF) og formen der har private variabler
// og getters istedet for public variabler uden getters kaldes bean form.
// Han siger det er en form som gør Objectiv purister glade, men ellers ikke hjælper på
// noget så burde vi fjerne gettersene? På den ene side gør de filerne sikre siden vi
// arbejder med sygehusvæsenet, men hvis getters ikke er nødvendige er de spild af kode

public class JournalGenerator
{
    public void makeJournal(String patientName, String CPR, String printDate, String startTDate, String endTDate,
                     String dateWritten, String noteType, String examinationDetails, String diagnose, String interpretedBy,
                     String writtenBy, String authenticatedBy, String hospitalName, String departmentName, String uploadedBy)  {
        try {
            // PDF save location
            final String savePath = "C:\\Journal\\FremstilletPDF.pdf";

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

            //Paragraph objects
            /*  Pseudo variables for tests
            QueryVariables queryVariables = new QueryVariables("Bob", "1234567890", "21.3.2018","10.3.2018", "20.3.2018", "19.3.2018", "Røntgennote",
                    "Undersøgelsen er foretaget som postoperativ kontrol.\n" +
                            "Der ses sammenholdt medundersøgelsen fra29.07.17, at den tidligere\n" +
                            "påviste fraktur vedproksimalehøjre femur, nu er behandlet med total\n" +
                            "hoftealloplastik, som ses i god stilling i alle planer", "postoperativ kontrol",
                    "Anders Bensen", "Ib Jensen", "Dennis Krieger", "Aalborg Universitetshospital",
                    "Alb Røntgen Amb", "Alb O-kir sengeafdeling");
            */
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