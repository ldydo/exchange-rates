package pl.parser.nbp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@XmlRootElement(name = "pozycja")
public class Position {
    private String currencyName;
    private int converter;
    private String currencyCode;
    private BigDecimal buyingRate;
    private BigDecimal sellingRate;

    static class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

        public BigDecimal unmarshal(String v) throws Exception {
            return new BigDecimal(v.replaceAll(",", "."));
        }

        public String marshal(BigDecimal v) throws Exception {
            return v.toString();
        }
    }

    @XmlElement(name = "nazwa_waluty")
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @XmlElement(name = "przelicznik")
    public int getConverter() {
        return converter;
    }

    public void setConverter(int converter) {
        this.converter = converter;
    }

    @XmlElement(name = "kod_waluty")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @XmlElement(name = "kurs_kupna")
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    public BigDecimal getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(BigDecimal buyingRate) {
        this.buyingRate = buyingRate;
    }

    @XmlElement(name = "kurs_sprzedazy")
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    public BigDecimal getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(BigDecimal sellingRate) {
        this.sellingRate = sellingRate;
    }

    @Override
    public String toString() {
        return "Position{" +
                "currencyName='" + currencyName + '\'' +
                ", converter=" + converter +
                ", currencyCode='" + currencyCode + '\'' +
                ", buyingRate=" + buyingRate +
                ", sellingRate=" + sellingRate +
                '}';
    }
}
