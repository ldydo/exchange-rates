package pl.parser.nbp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyService {

    private String currencyCode;
    private String startDate;
    private String endDate;
    private List<ExchangeTable> tableList;
    private Date sDate;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date eDate;

    @Autowired
    private ExchangeTablesRepository exchangeTablesRepository;


    public CurrencyService(String currencyCode, String startDate, String endDate) {
        this.currencyCode = currencyCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void parseDates() throws ParseException {
        sDate = simpleDateFormat.parse(startDate);
        eDate = simpleDateFormat.parse(endDate);
    }

    private void loadExchangeTables() throws ParseException, IOException, JAXBException {
        parseDates();
        assert sDate.before(eDate);
        tableList = exchangeTablesRepository.getExchangeTablesBetweenDates(sDate, eDate);
    }

    public BigDecimal[] getExchangeRateMeanAndStdDev() throws ParseException, JAXBException, IOException {
        loadExchangeTables();
        BigDecimal currencySum = new BigDecimal(0.0d);
        int currencyCount = 0;
        for (ExchangeTable et : tableList) {
            currencySum = currencySum.add(et.getPositionMap().get(currencyCode).getBuyingRate());
            currencyCount++;
        }

        if (currencyCount == 0) {
            return new BigDecimal[]{new BigDecimal(0), new BigDecimal(0)};
        }

        MathContext mc = new MathContext(5, RoundingMode.HALF_UP);
        BigDecimal count = new BigDecimal(currencyCount);
        BigDecimal mean = currencySum.divide(count, mc);

        BigDecimal stdDevSum = new BigDecimal(0);

        for (ExchangeTable et : tableList) {
            stdDevSum = stdDevSum.add(et.getPositionMap().get(currencyCode).getBuyingRate().subtract(mean).pow(2));
        }

        BigDecimal stdDev = stdDevSum.divide(count, mc);

        return new BigDecimal[]{mean, new BigDecimal(Math.sqrt(stdDev.doubleValue()))};
    }

//    public void uselessMethod() throws IOException, ParseException, JAXBException {
//        System.out.println("currencyCode = " + currencyCode);
//        System.out.println("startDate = " + startDate);
//        System.out.println("endDate = " + endDate);
//        tableList.forEach(System.out::println);
//    }
}
