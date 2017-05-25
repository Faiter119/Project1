package excel;

import data.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OlavH on 20-Apr-17.
 */
public class excelTest {

    public static List<Person> persons(){

        return Arrays.asList(
                new Person("Olav Husby",93240605),
                new Person("Magnus FN", 23456722)
        );

    }

    public static void main(String[] args)  {

        Object [] FILE_HEADER = {"name","tlf"};

        try {
            CSVPrinter printer = CSVFormat.EXCEL.withHeader("name", "tlf").print(new FileWriter("./events.xlsx", false));

            //printer.printRecord(FILE_HEADER);

            printer.println();
            //printer.printRecord(persons());

            printer.flush();
            printer.close();
        }catch (Exception e){e.printStackTrace();}
    }

}
