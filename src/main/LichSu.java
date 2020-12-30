package main;

public class LichSu{
    protected String tenda, vitri, cn, cm;
    protected int from, to;

    public LichSu(){
    }

    public LichSu(String tenda, String vitri, String cn, String cm, int from, int to){
        this.tenda = tenda;
        this.vitri = vitri;
        this.cn = cn;
        this.cm = cm;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return this.vitri + " " + this.tenda + " " + this.cn;
    }

    public String getTenda(){
        return tenda;
    }

    public void setTenda(String tenda){
        this.tenda = tenda;
    }

    public String getVitri(){
        return vitri;
    }

    public void setVitri(String vitri){
        this.vitri = vitri;
    }

    public String getCn(){
        return cn;
    }

    public void setCn(String cn){
        this.cn = cn;
    }

    public String getCm(){
        return cm;
    }

    public void setCm(String cm){
        this.cm = cm;
    }

    public int getFrom(){
        return from;
    }

    public void setFrom(int from){
        this.from = from;
    }

    public int getTo(){
        return to;
    }

    public void setTo(int to){
        this.to = to;
    }
}
