package pl.parser.nbp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;

@Configuration
public class CurrencyConfig {

    @Autowired
    private Environment environment;

    @Bean
    public CurrencyService currencyService() throws ParseException, JAXBException, IOException {
        String[] args = environment.getProperty("nonOptionArgs", String[].class);
        assert args.length == 3;
        return new CurrencyService(args[0], args[1], args[2]);
    }

    @Bean
    public FileNamesRepository fileNamesRepository() {
        return new FileNamesRepository();
    }

    @Bean
    ExchangeTablesRepository exchangeTablesRepository() {
        return new ExchangeTablesRepository();
    }
}
