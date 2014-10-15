package ygrenzinger.models;

/**
 * Created by ygrenzinger on 15/10/2014.
 */
public class Stock {

    private String sector;
    private String industry;
    private String symbol;
    private String headquarters;
    private String company;

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
