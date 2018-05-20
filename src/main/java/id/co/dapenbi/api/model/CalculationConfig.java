package id.co.dapenbi.api.model;

public class CalculationConfig {
    private String startAge;
    private String finishAge;
    private double fpr;
    private double nsMM; // ns Male Mariage
    private double nsMN; // ns Male Not mariage

    public CalculationConfig() {
    }

    public CalculationConfig(String startAge, String finishAge, double fpr, double nsMM, double nsMN) {
        this.startAge = startAge;
        this.finishAge = finishAge;
        this.fpr = fpr;
        this.nsMM = nsMM;
        this.nsMN = nsMN;
    }

    public String getStartAge() {
        return startAge;
    }

    public void setStartAge(String startAge) {
        this.startAge = startAge;
    }

    public String getFinishAge() {
        return finishAge;
    }

    public void setFinishAge(String finishAge) {
        this.finishAge = finishAge;
    }

    public double getFpr() {
        return fpr;
    }

    public void setFpr(double fpr) {
        this.fpr = fpr;
    }

    public double getNsMM() {
        return nsMM;
    }

    public void setNsMM(double nsMM) {
        this.nsMM = nsMM;
    }

    public double getNsMN() {
        return nsMN;
    }

    public void setNsMN(double nsMN) {
        this.nsMN = nsMN;
    }
}
