package pl.parser.nbp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

public class MainClass {
    public static void main(String[] args) throws JAXBException, IOException, ParseException {
        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
        context.register(CurrencyConfig.class);
        context.refresh();
        CurrencyService currencyService = context.getBean("currencyService", CurrencyService.class);
        BigDecimal[] results = currencyService.getExchangeRateMeanAndStdDev();
        System.out.println(results[0]);
        System.out.println(results[1]);
    }
}
