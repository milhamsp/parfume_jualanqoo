package com.android.parfume_jualanqoo;

import android.os.Parcel;
import android.os.Parcelable;

public class model_parfum implements Parcelable {
    private String nama_produk;
    private String id_produk;
    private String deskripsi_produk;
    private String tipe_produk;
    private Long stok_produk;
    private Long harga_produk;

    public model_parfum() {
    }

    public model_parfum(String nama_produk,String deskripsi_produk,String tipe_produk,String id_produk, Long harga_produk, Long stok_produk) {
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
        this.stok_produk = stok_produk;
        this.tipe_produk = tipe_produk;
        this.id_produk = id_produk;
        this.deskripsi_produk = deskripsi_produk;
    }

    protected model_parfum(Parcel in) {
        nama_produk = in.readString();
        id_produk = in.readString();
        deskripsi_produk = in.readString();
        tipe_produk = in.readString();
        if (in.readByte() == 0) {
            stok_produk = null;
        } else {
            stok_produk = in.readLong();
        }
        if (in.readByte() == 0) {
            harga_produk = null;
        } else {
            harga_produk = in.readLong();
        }
    }

    public static final Creator<model_parfum> CREATOR = new Creator<model_parfum>() {
        @Override
        public model_parfum createFromParcel(Parcel in) {
            return new model_parfum(in);
        }

        @Override
        public model_parfum[] newArray(int size) {
            return new model_parfum[size];
        }
    };

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Long getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Long harga_produk) {
        this.harga_produk = harga_produk;
    }

    public Long getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(Long stok_produk) {
        this.stok_produk = stok_produk;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getDeskripsi_produk() {
        return deskripsi_produk;
    }

    public void setDeskripsi_produk(String deskripsi_produk) {
        this.deskripsi_produk = deskripsi_produk;
    }

    public String getTipe_produk() {
        return tipe_produk;
    }

    public void setTipe_produk(String tipe_produk) {
        this.tipe_produk = tipe_produk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama_produk);
        dest.writeString(id_produk);
        dest.writeString(deskripsi_produk);
        dest.writeString(tipe_produk);
        if (stok_produk == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(stok_produk);
        }
        if (harga_produk == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(harga_produk);
        }
    }
}
