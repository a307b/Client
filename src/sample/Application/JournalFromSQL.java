//package sample.Application;

//import com.itextpdf.io.font.constants.StandardFonts;
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
/*
public class JournalFromSQL
{
    void makeJournal()  {
        try {
            // PDF save location
            final String DEST = "C:\\Journal\\FremstilletPDF.pdf";

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));

            // Initialize document
            Document document = new Document(pdf);

            // Adds standard font
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

            // Sets standard font and size for the document. Bold font is different from
            // the standard font and need to be specified in TextVariables
            final int standardFont = 10;
            document.setFont(font).setFontSize(standardFont);

            //Pseudo variable as Paragraph objects
            ParagraphVariables paragraphs = new ParagraphVariables();

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
            for (int i = 0; i < paragraphsList.size(); i++) {
                document.add(paragraphsList.get(i));
            }

            //Close document
            document.close();
        }catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }catch (java.io.IOException i) {
            i.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JournalFromSQL journal = new JournalFromSQL();
        journal.makeJournal();

   }
}*/