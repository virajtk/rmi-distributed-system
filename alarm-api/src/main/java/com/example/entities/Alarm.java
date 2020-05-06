package com.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "Alarms")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate ID
    private int id;
    private String name;
    private int roomNo;
    private int floorNo;
    private int smokeLevel;
    private int co2Level;
    private String status;

    public Alarm() {
    }

    public Alarm(String name, int roomNo, int floorNo, int smokeLevel, int co2Level, String status) {
        this.name = name;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
        this.smokeLevel = smokeLevel;
        this.co2Level = co2Level;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomNo=" + roomNo +
                ", floorNo=" + floorNo +
                ", smokeLevel=" + smokeLevel +
                ", co2Level=" + co2Level +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getSmokeLevel() {
        return smokeLevel;
    }

    public void setSmokeLevel(int smokeLevel) {
        this.smokeLevel = smokeLevel;
    }

    public int getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
