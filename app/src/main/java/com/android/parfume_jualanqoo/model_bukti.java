package com.android.parfume_jualanqoo;

public class model_bukti {
    public model_bukti(){

    }
    private String nama_gambar,url_gambar;

    public String getNama_gambar() {
        return nama_gambar;
    }

    public void setNama_gambar(String nama_gambar) {
        this.nama_gambar = nama_gambar;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }

    public void setUrl_gambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public model_bukti(String nama_gambar, String url_gambar){
        nama_gambar = nama_gambar;
        url_gambar = url_gambar;
    }
}
