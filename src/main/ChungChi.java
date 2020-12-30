package main;

public class ChungChi{
    protected String tencc, cosocap, minhchung, cn, cm, mucdo;
    protected int nam, hsd;

    public ChungChi(){
    }

    public ChungChi(String tencc, String mucdo, String cosocap, String minhchung, String cn, String cm, int nam,
                    int hsd){
        this.tencc = tencc;
        this.mucdo = mucdo;
        this.cosocap = cosocap;
        this.minhchung = minhchung;
        this.cn = cn;
        this.cm = cm;
        this.nam = nam;
        this.hsd = hsd;
    }

    @Override
    public String toString(){
        return "Chứng chỉ: " + this.tencc + " " + this.mucdo;
    }

    public String getTencc(){
        return tencc;
    }

    public void setTencc(String tencc){
        this.tencc = tencc;
    }

    public String getMinhchung(){
        return minhchung;
    }

    public void setMinhchung(String minhchung){
        this.minhchung = minhchung;
    }

    public String getCosocap(){
        return cosocap;
    }

    public void setCosocap(String cosocap){
        this.cosocap = cosocap;
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

    public int getNam(){
        return nam;
    }

    public void setNam(int nam){
        this.nam = nam;
    }

    public int getHsd(){
        return hsd;
    }

    public void setHsd(int hsd){
        this.hsd = hsd;
    }

    public String getMucdo(){
        return mucdo;
    }

    public void setMucdo(String mucdo){
        this.mucdo = mucdo;
    }

}
