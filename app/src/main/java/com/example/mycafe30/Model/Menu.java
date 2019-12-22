package com.example.mycafe30.Model;

public class Menu {

    private String id_menu;
    private String nama_menu;
    private String deskripsi;
    private String harga;
    private String gambar;
    private String id_user;

    public Menu(){ }

    public Menu(String id_menu, String nama_menu, String deskripsi, String harga, String gambar, String id_user){
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.gambar = gambar;
        this.id_user = id_user;
    }

    public String getIdMenu() {
        return id_menu;
    }
    public void setIdMenu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getNamaMenu() {
        return nama_menu;
    }
    public void setNamaMenu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {return gambar;}
    public void setGambar(String gambar) {this.gambar = gambar;}

    public String getIdUser() {return id_user;}
    public void setIdUser(String id_user) {this.id_user = id_user;}
}
