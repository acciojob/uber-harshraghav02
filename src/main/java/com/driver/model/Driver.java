package com.driver.model;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int driverId;

    String mobile;

    String password;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();

    public Driver(String mobile, String password) {

        this.mobile = mobile;
        this.password = password;
    }

    public Driver() {
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }
}