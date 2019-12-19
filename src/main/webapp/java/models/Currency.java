package models;

public class Currency {

    enum CurrencyName {
        BGN, USD, EUR
    }

    private int id;
    private CurrencyName name;

    public Currency(CurrencyName name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyName getName() {
        return name;
    }
}
