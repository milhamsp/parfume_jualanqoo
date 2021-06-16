package com.android.parfume_jualanqoo;

public class model_transaksi {
    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    private String alamat_user;

    public String getTelp_user() {
        return telp_user;
    }

    public void setTelp_user(String telp_user) {
        this.telp_user = telp_user;
    }

    private String telp_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    private String id_user;

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    private String nama_user;

    public Long getJumlah_pesanan() {
        return jumlah_pesanan;
    }

    public void setJumlah_pesanan(Long jumlah_pesanan) {
        this.jumlah_pesanan = jumlah_pesanan;
    }

    private Long jumlah_pesanan;

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    private String nama_produk;

    public String getTipe_produk() {
        return tipe_produk;
    }

    public void setTipe_produk(String tipe_produk) {
        this.tipe_produk = tipe_produk;
    }

    private String tipe_produk;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    private Long total;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    private String id_transaksi;

    public String getTanggal_pesan() {
        return tanggal_pesan;
    }

    public void setTanggal_pesan(String tanggal_pesan) {
        this.tanggal_pesan = tanggal_pesan;
    }

    private String tanggal_pesan;

    public model_transaksi() {
    }

    public model_transaksi(String alamat_user, String id_user, String nama_user, Long jumlah_pesanan, String telp_user, String nama_produk, String tipe_produk, Long total, String id_transaksi, String tanggal_pesan) {
        this.alamat_user = alamat_user;
        this.id_transaksi = id_transaksi;
        this.id_user = id_user;
        this.nama_produk = nama_produk;
        this.jumlah_pesanan = jumlah_pesanan;
        this.tanggal_pesan = tanggal_pesan;
        this.total = total;
        this.tipe_produk = tipe_produk;
        this.nama_user = nama_user;
        this.telp_user = telp_user;
    }

}
