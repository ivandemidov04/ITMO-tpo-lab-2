import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Cot {

    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public Cot() {
        this.sin = new Sin();
        this.cos = new Cos();
    }

    public double cot(double x, double eps) {
        double sinValue = sin.sin(x, eps);
        double cosValue = cos.cos(x, eps);

        if (Double.isNaN(sinValue) || Double.isNaN(cosValue)) {
            return Double.NaN;
        }

        if (Math.abs(sinValue) <= eps) {
            return Double.NaN; // cot is undefined when sin(x) = 0
        }

        return cosValue / sinValue;
    }

    public double writeResultToCSV(double x, double eps, Writer out) {
        double res = cot(x, eps);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecord(x, res);
        } catch (IOException e) {
            System.out.println("Wrong filename");
        }
        return res;
    }
}