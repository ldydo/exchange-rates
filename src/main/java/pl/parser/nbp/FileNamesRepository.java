package pl.parser.nbp;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class FileNamesRepository {

    private static final String URL = "http://www.nbp.pl/kursy/xml/";
    private static final String EXTENSION = ".txt";
    private static final String FILE_NAME_PREFIX = "dir";
    private static final String OUT_EXTENSION = ".xml";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");


    public List<String> getFileNamesByDates(Date sDate, Date eDate) throws IOException, ParseException {
        ArrayList<String> fileNames = new ArrayList<>();
        Calendar calendarSDate = Calendar.getInstance();
        Calendar calendarEDate = Calendar.getInstance();

        calendarSDate.setTime(sDate);
        calendarEDate.setTime(eDate);

        for (int i = calendarSDate.getWeekYear(); i <= calendarEDate.getWeekYear(); i++) {
            String fileNameSuffix = Calendar.getInstance().getWeekYear() == i ? "" : Integer.toString(i);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new URL(URL + FILE_NAME_PREFIX + fileNameSuffix + EXTENSION).openStream()))) {

                String line;
                while ((line = br.readLine()) != null) {
                    assert line.length() == 11;
                    String[] nameAndDate = line.split("z");
                    assert nameAndDate.length == 2;
                    Date dateFromFile = dateFormat.parse(nameAndDate[1]);
                    if (nameAndDate[0].charAt(0) == 'c' && !dateFromFile.after(eDate) && !dateFromFile.before(sDate)) {
                        fileNames.add(line + OUT_EXTENSION);
                    }
                }
            }
        }

        return fileNames;
    }
}
