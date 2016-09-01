package pl.parser.nbp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@XmlRootElement(name = "tabela_kursow")
public class ExchangeTable {

    private String tableNum;
    private String listingDate;
    private String publicationDate;
    private Map<String, Position> positionMap;

    public ExchangeTable() {

    }

    @XmlElement(name = "numer_tabeli")
    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    @XmlElement(name = "data_notowania")
    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    @XmlElement(name = "data_publikacji")
    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @XmlElement(name = "pozycja")
    @XmlJavaTypeAdapter(PositionMapAdapter.class)
    public Map<String, Position> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, Position> positionMap) {
        this.positionMap = positionMap;
    }

    @Override
    public String toString() {
        return "ExchangeTable{" +
                "tableNum='" + tableNum + '\'' +
                ", listingDate='" + listingDate + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", positionList=" + positionMap +
                '}';
    }

    static class PositionMapAdapter extends XmlAdapter<Position, Map<String, Position>> {

        private Map<String, Position> map;
        private Iterator<String> iterator;

        public PositionMapAdapter() {
            map = new HashMap<>();
            iterator = map.keySet().iterator();
        }

        @Override
        public Map<String, Position> unmarshal(Position v) throws Exception {
            map.put(v.getCurrencyCode(), v);
            return map;
        }

        @Override
        public Position marshal(Map<String, Position> v) throws Exception {
            return map.remove(iterator.next());
        }

    }

}
