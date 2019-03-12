package org.envision.parkai;

/**
 * Created by root on 11/3/19.
 */

public class HostDetails {

    private String hostuid;
    private String name;
    private String address;
    private String phone;
    private String charge;
    private String image;
    private String status;

    public HostDetails(String hostuid,String name, String address, String phone, String charge, String image,String status) {
        this.hostuid=hostuid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.charge = charge;
        this.image = image;
        this.status=status;
    }


    public String getHostuid() {
        return hostuid;
    }

    public void setHostuid(String hostuid) {
        this.hostuid = hostuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
