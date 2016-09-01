package pl.parser.nbp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ExchangeTablesRepository {

    public static final String URL = "http://www.nbp.pl/kursy/xml/";

    @Autowired
    private FileNamesRepository fileNamesRepository;

    public List<ExchangeTable> getExchangeTablesBetweenDates(Date sDate, Date eDate) throws IOException, ParseException, JAXBException {
        List<String> fileNamesByDates = fileNamesRepository.getFileNamesByDates(sDate, eDate);
        ArrayList<ExchangeTable> exchangeTables = new ArrayList<>();
        JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeTable.class);

        for (String fileNamesByDate : fileNamesByDates) {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ExchangeTable exchangeTable = (ExchangeTable) unmarshaller.unmarshal(new URL(URL + fileNamesByDate));
            exchangeTables.add(exchangeTable);
        }

        return exchangeTables;
    }

}
