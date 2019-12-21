package com.example.mycafe30.Model;

public class Meja {

    private String id_meja;
    private String no_meja;
    private String jumlah_kursi;
    private String status;
    private String id_user;

    public Meja(){ }

    public Meja(String id_meja, String no_meja, String jumlah_kursi, String status, String id_user){
        this.id_meja = id_meja;
        this.no_meja = no_meja;
        this.jumlah_kursi = jumlah_kursi;
        this.status = status;
        this.id_user = id_user;
    }


    public String getIdMeja() {
        return id_meja;
    }
    public void setIdMeja(String id_meja) {
        this.id_meja = id_meja;
    }

    public String getNoMeja() {
        return no_meja;
    }
    public void setNoMeja(String no_meja) {
        this.no_meja = no_meja;
    }

    public String getJumlahKursi() {
        return jumlah_kursi;
    }
    public void setJumlahKursi(String jumlah_kursi) {
        this.jumlah_kursi = jumlah_kursi;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUser() {return id_user;}
    public void setIdUser(String id_user) {this.id_user = id_user;}
}
