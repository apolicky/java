package cz.cuni.mff.java.semestr4.hnetCviceni.c08xmldatab.xmldb;

public class Data {
    @Key
    private String id;
    private String value;

    public Data(String id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
