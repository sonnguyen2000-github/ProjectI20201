package main;

public class LichSu{
    protected String tenda, vitri, cn, cm;
    protected int from, to;

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

    public String getVitri(){
        return vitri;
    }

    public String getCn(){
        return cn;
    }

    public String getCm(){
        return cm;
    }

    public int getFrom(){
        return from;
    }

    public int getTo(){
        return to;
    }
}
