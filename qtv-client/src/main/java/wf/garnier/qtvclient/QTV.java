package wf.garnier.qtvclient;

public class QTV {
    private String localisation;
    private Integer debit;
    private Integer temps;
    private Integer vitesse;

    public QTV(String localisation, Integer debit, Integer temps, Integer vitesse) {
        this.localisation = localisation;
        this.debit = debit;
        this.temps = temps;
        this.vitesse = vitesse;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public Integer getTemps() {
        return temps;
    }

    public void setTemps(Integer temps) {
        this.temps = temps;
    }

    public Integer getVitesse() {
        return vitesse;
    }

    public void setVitesse(Integer vitesse) {
        this.vitesse = vitesse;
    }
}
