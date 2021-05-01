package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NguoiDung {

    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("dangTheoDoi")
    @Expose
    private List<String> dangTheoDoi = null;
    @SerializedName("duocTheoDoi")
    @Expose
    private List<String> duocTheoDoi = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("matKhau")
    @Expose
    private String matKhau;
    @SerializedName("hoTen")
    @Expose
    private String hoTen;
    @SerializedName("thoiGianTao")
    @Expose
    private String thoiGianTao;
    @SerializedName("thoiGianCapNhat")
    @Expose
    private String thoiGianCapNhat;
    @SerializedName("soDienThoai")
    @Expose
    private String soDienThoai;
    @SerializedName("ngaySinh")
    @Expose
    private String ngaySinh;
    @SerializedName("diaChi")
    @Expose
    private String diaChi;
    @SerializedName("tieuSu")
    @Expose
    private String tieuSu;
    @SerializedName("gioiTinh")
    @Expose
    private String gioiTinh;
    @SerializedName("baoCao")
    @Expose
    private int baoCao;

    //Đăng nhập
    public NguoiDung(String soDienThoai, String matKhau) {
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
    }

    //Đăng ký
    public NguoiDung(String email, String matKhau, String hoTen, String soDienThoai) {
        this.email = email;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }
    //Cập nhật thông tin
    public NguoiDung(String hoTen, String ngaySinh, String diaChi, String tieuSu, String gioiTinh) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.tieuSu = tieuSu;
        this.gioiTinh = gioiTinh;
    }

    public List<String> getDangTheoDoi() {
        return dangTheoDoi;
    }

    public void setDangTheoDoi(List<String> dangTheoDoi) {
        this.dangTheoDoi = dangTheoDoi;
    }

    public List<String> getDuocTheoDoi() {
        return duocTheoDoi;
    }

    public void setDuocTheoDoi(List<String> duocTheoDoi) {
        this.duocTheoDoi = duocTheoDoi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(String thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTieuSu() {
        return tieuSu;
    }

    public void setTieuSu(String tieuSu) {
        this.tieuSu = tieuSu;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public int getBaoCao() {
        return baoCao;
    }

    public void setBaoCao(int baoCao) {
        this.baoCao = baoCao;
    }
}
