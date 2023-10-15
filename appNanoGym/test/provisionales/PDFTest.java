package provisionales;
import usuario.*;

import java.time.Month;
import java.time.Year;

public class PDFTest {
    public static void main (String ... args) {
        Monitor m = new Monitor("Jose", "1234", "Jose Luis", "jose.gmail.com", "70421808V");

        m.addHorasTrabajadas(Month.FEBRUARY, Year.of(2023), 20);

        m.generarPDF(m, Month.FEBRUARY, Year.of(2023));
    }
}
