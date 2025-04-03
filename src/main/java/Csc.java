import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Csc {

    private final Sin sin;

    public Csc(Sin sin) {
        this.sin = sin;
    }

    public Csc() {
        this.sin = new Sin();
    }

    public double csc(double x, double eps) {
        double sinValue = sin.sin(x, eps);

        if (Double.isNaN(sinValue)) {
            return Double.NaN;
        }

        if (Math.abs(sinValue) <= eps) {
            return Double.NaN; // csc is undefined when sin(x) = 0
        }

        return 1.0 / sinValue;
    }

    public double writeResultToCSV(double x, double eps, Writer out) {
        double res = csc(x, eps);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecord(x, res);
        } catch (IOException e) {
            System.out.println("Wrong filename");
        }
        return res;
    }
}