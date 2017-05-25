import Files.FileSingleObjectReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by OlavH on 20-Apr-17.
 */
public class ExcelPrinter {

    public static void main(String[] args) throws IOException {

        String filePath = "./storage.txt/";

        FileSingleObjectReader reader = new FileSingleObjectReader(Paths.get(filePath));

        List<Event> eventList = (List<Event>) reader.readObject();

        CSVFormat excel = CSVFormat.EXCEL;

        CSVPrinter printer = new CSVPrinter(new FileWriter("./events.xls", false), excel);

        printer.printRecord();
    }
}
