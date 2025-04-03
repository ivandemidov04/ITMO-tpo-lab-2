import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Function {

    private final Cos cos;
    private final Sin sin;
    private final Cot cot;
    private final Csc csc;
    private final Log log;
    private final Ln ln;

    public Function(Cos cos, Sin sin, Cot cot, Csc csc, Log log, Ln ln) {
        this.cos = cos;
        this.sin = sin;
        this.cot = cot;
        this.csc = csc;
        this.log = log;
        this.ln = ln;
    }

    public Function() {
        this.cos = new Cos();
        this.sin = new Sin();
        this.cot = new Cot();
        this.csc = new Csc();
        this.ln = new Ln();
        this.log = new Log();
    }

    public double calculate(double x, double eps) {
        if (x <= 0) {
            double cotVal = cot.cot(x, eps);
            double cosVal = cos.cos(x, eps);
            double cscVal = csc.csc(x, eps);

            if (Double.isNaN(cotVal) || Double.isNaN(cosVal) || Double.isNaN(cscVal)) {
                return Double.NaN;
            }

            if (Math.abs(cosVal) <= eps) {
                return Double.NaN;
            }

            double term1 = cotVal / cosVal;
            double term2 = term1 * cscVal;
            double term3 = term2 - cotVal;
            double term4 = term3 + Math.pow(cosVal, 2);
            return term4 + cosVal;
        } else {
            double log10x = log.log(10, x, eps);
            double lnx = ln.ln(x, eps);
            double log3x = log.log(3, x, eps);
            double log2x = log.log(2, x, eps);
            double log5x = log.log(5, x, eps);

            if (Double.isNaN(log10x) || Double.isNaN(lnx) || Double.isNaN(log3x) ||
                    Double.isNaN(log2x) || Double.isNaN(log5x)) {
                return Double.NaN;
            }

            double term1 = log10x - log10x;
            double term2 = term1 + lnx;
            double term3 = log3x + lnx;
            double term4 = term2 - term3;
            double term5 = log2x * (log10x * log2x);
            double term6 = term4 * term5;
            return term6 - log5x;
        }
    }

    public double writeResultToCSV(double x, double eps, Writer out) {
        double res = calculate(x, eps);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecord(x, res);
        } catch (IOException e) {
            System.out.println("Wrong filename");
        }
        return res;
    }
}