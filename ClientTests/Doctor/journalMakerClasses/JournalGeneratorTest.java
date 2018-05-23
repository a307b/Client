package Doctor.journalMakerClasses;

import org.junit.jupiter.api.Test;

class JournalGeneratorTest {
    /* Tests makeJournal with some dummy encryptedData */
    @Test
    void makeJournal() {
        JournalGenerator journalGenerator = new JournalGenerator();
        journalGenerator.makeJournal("makeJournalTest", "Bob", "1234567890", "21.3.2018","10.3.2018", "20.3.2018", "19.3.2018", "Røntgennote",
                "Undersøgelsen er foretaget som postoperativ kontrol.\n" +
                        "Der ses sammenholdt medundersøgelsen fra29.07.17, at den tidligere\n" +
                        "påviste fraktur vedproksimalehøjre femur, nu er behandlet med total\n" +
                        "hoftealloplastik, som ses i god stilling i alle planer", "postoperativ kontrol",
                "Anders Bensen", "Ib Jensen", "Dennis Krieger", "Aalborg Universitetshospital",
                "Alb Røntgen Amb", "Alb O-kir sengeafdeling");
    }
}