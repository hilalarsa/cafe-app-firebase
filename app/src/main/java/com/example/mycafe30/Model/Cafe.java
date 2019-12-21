package com.example.mycafe30.Model;

public class Cafe {

    private String id_cafe;
    private String nama_cafe;
    private String lokasi;
    private String deskripsi;
    private String id_user;

    public Cafe(){
    }

    public Cafe(String id_cafe, String nama_cafe, String lokasi, String deskripsi, String id_user){
        this.id_cafe = id_cafe;
        this.nama_cafe = nama_cafe;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.id_user = id_user;
    }


    public String getIdCafe() {
        return id_cafe;
    }
    public void setIdCafe(String id_cafe) {
        this.id_cafe = id_cafe;
    }

    public String getNamaCafe() {
        return nama_cafe;
    }
    public void setNamaCafe(String nama_cafe) {
        this.nama_cafe = nama_cafe;
    }

    public String getLokasi() {
        return lokasi;
    }
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIdUser() {return id_user;}
    public void setIdUser(String id_user) {this.id_user = id_user;}
}
