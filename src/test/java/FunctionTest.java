import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
public class FunctionTest {
    static double functionEps = 0.1;
    double eps = 0.15;

    static Csc cscMock;
    static Cot cotMock;
    static Cos cosMock;
    static Sin sinMock;
    static Ln lnMock;
    static Log logMock;

    static Reader cscIn;
    static Reader cotIn;
    static Reader cosIn;
    static Reader sinIn;
    static Reader lnIn;
    static Reader log2In;
    static Reader log3In;
    static Reader log5In;
    static Reader log10In;


    @BeforeAll
    static void init() {
        cscMock = Mockito.mock(Csc.class);
        cotMock = Mockito.mock(Cot.class);
        cosMock = Mockito.mock(Cos.class);
        sinMock = Mockito.mock(Sin.class);
        lnMock = Mockito.mock(Ln.class);
        logMock = Mockito.mock(Log.class);
        try {
            cscIn = new FileReader("src/main/resources/CsvFiles/Inputs/CscIn.csv");
            cotIn = new FileReader("src/main/resources/CsvFiles/Inputs/CotIn.csv");
            cosIn = new FileReader("src/main/resources/CsvFiles/Inputs/CosIn.csv");
            sinIn = new FileReader("src/main/resources/CsvFiles/Inputs/SinIn.csv");
            lnIn = new FileReader("src/main/resources/CsvFiles/Inputs/LnIn.csv");
            log2In = new FileReader("src/main/resources/CsvFiles/Inputs/Log2In.csv");
            log3In = new FileReader("src/main/resources/CsvFiles/Inputs/Log3In.csv");
            log5In = new FileReader("src/main/resources/CsvFiles/Inputs/Log5In.csv");
            log10In = new FileReader("src/main/resources/CsvFiles/Inputs/Log10In.csv");
            System.out.println();
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(cscIn);
            for (CSVRecord record : records) {
                Mockito.when(cscMock.csc(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(cotIn);
            for (CSVRecord record : records) {
                Mockito.when(cotMock.cot(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(cosIn);
            for (CSVRecord record : records) {
                Mockito.when(cosMock.cos(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(sinIn);
            for (CSVRecord record : records) {
                Mockito.when(sinMock.sin(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(lnIn);
            for (CSVRecord record : records) {
                Mockito.when(lnMock.ln(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log2In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(2, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log3In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(3, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log5In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(5, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log10In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(10, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
        } catch (IOException ex) {
            System.err.println("Ты как в тесте IOE поймал?!");
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithMocks(double value, double expected) {
        Function function = new Function(cosMock, sinMock, cotMock, cscMock, logMock, lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithCot(double value, double expected) {
        Function function = new Function(cosMock, sinMock, new Cot(sinMock, cosMock), cscMock, logMock, lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithCos(double value, double expected) {
        Function function = new Function(new Cos(sinMock), sinMock, new Cot(sinMock, new Cos(sinMock)), cscMock, logMock, lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithCsc(double value, double expected) {
        Function function = new Function(new Cos(sinMock), sinMock, new Cot(sinMock, new Cos(sinMock)), new Csc(sinMock), logMock, lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithSin(double value, double expected) {
        Function function = new Function(new Cos(new Sin()), new Sin(), new Cot(new Sin(), new Cos(new Sin())), new Csc(new Sin()), logMock, lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithLog(double value, double expected) {
        Function function = new Function(cosMock, sinMock, cotMock, cscMock, new Log(lnMock), lnMock);
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "CsvFiles/Inputs/FunctionIn.csv")
    void testSystemWithLn(double value, double expected) {
        Function function = new Function(cosMock, sinMock, cotMock, cscMock, new Log(new Ln()), new Ln());
        Assertions.assertEquals(expected, function.calculate(value, functionEps), eps);
    }
}
