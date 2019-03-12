package org.envision.parkai;

/**
 * Created by root on 12/3/19.
 */

public class LoginDetails2 {

    private String hostname;
    private String uid;
    private String name;
    private String email;
    private String phno;
    private String img;
    private String bank;
    private String numberPlate;


    public LoginDetails2(String hostname,String uid, String name, String email, String phno, String img, String bank, String numberPlate) {
        this.hostname=hostname;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.img = img;
        this.bank = bank;
        this.numberPlate = numberPlate;
    }

    public LoginDetails2()
    {

    }


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
