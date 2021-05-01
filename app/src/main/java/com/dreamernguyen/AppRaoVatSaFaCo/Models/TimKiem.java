package com.dreamernguyen.AppRaoVatSaFaCo.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TimKiem {

    @PrimaryKey(autoGenerate = true)
    private int idTimKiem;
    private String tieuDe;
    private String danhMuc;
    private String diaChi;
    private String sapXepThoiGian;
    private String sapXepGiaBan;
    private String trangThai;

    public TimKiem( String tieuDe, String danhMuc, String diaChi, String sapXepThoiGian, String sapXepGiaBan, String trangThai) {
        this.tieuDe = tieuDe;
        this.danhMuc = danhMuc;
        this.diaChi=diaChi;
        this.sapXepThoiGian = sapXepThoiGian;
        this.sapXepGiaBan = sapXepGiaBan;
        this.trangThai = trangThai;
    }

    public TimKiem() {
    }

    public int getIdTimKiem() {
        return idTimKiem;
    }

    public void setIdTimKiem(int idTimKiem) {
        this.idTimKiem = idTimKiem;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSapXepThoiGian() {
        return sapXepThoiGian;
    }

    public void setSapXepThoiGian(String sapXepThoiGian) {
        this.sapXepThoiGian = sapXepThoiGian;
    }

    public String getSapXepGiaBan() {
        return sapXepGiaBan;
    }

    public void setSapXepGiaBan(String sapXepGiaBan) {
        this.sapXepGiaBan = sapXepGiaBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
