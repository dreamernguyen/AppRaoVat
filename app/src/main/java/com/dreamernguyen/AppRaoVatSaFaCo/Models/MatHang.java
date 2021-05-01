package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatHang {
    @SerializedName("linkAnh")
    @Expose
    private List<String> linkAnh = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("hangMuc")
    @Expose
    private String hangMuc;
    @SerializedName("tieuDe")
    @Expose
    private String tieuDe;
    @SerializedName("moTa")
    @Expose
    private String moTa;
    @SerializedName("giaBan")
    @Expose
    private Integer giaBan;
    @SerializedName("diaChi")
    @Expose
    private String diaChi;
    @SerializedName("idNguoiDung")
    @Expose
    private NguoiDung idNguoiDung;
    @SerializedName("daAn")
    @Expose
    private Boolean daAn;
    @SerializedName("daDuyet")
    @Expose
    private Boolean daDuyet;
    @SerializedName("daXoa")
    @Expose
    private Boolean daXoa;
    @SerializedName("baoCao")
    @Expose
    private int baoCao;
    @SerializedName("nguoiQuanTam")
    @Expose
    private List<NguoiDung> nguoiQuanTam = null;
    @SerializedName("thoiGianTao")
    @Expose
    private String thoiGianTao;
    @SerializedName("thoiGianCapNhat")
    @Expose
    private String thoiGianCapNhat;

    //Đăng bán
    public MatHang(String tieuDe, String moTa, String hangMuc, Integer giaBan, List<String> linkAnh, String diaChi) {
        this.linkAnh = linkAnh;
        this.hangMuc = hangMuc;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.diaChi = diaChi;
    }

    public List<String> getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(List<String> linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHangMuc() {
        return hangMuc;
    }

    public void setHangMuc(String hangMuc) {
        this.hangMuc = hangMuc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setNoiDung(String noiDung) {
        this.moTa = moTa;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public NguoiDung getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(NguoiDung idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public Boolean getDaAn() {
        return daAn;
    }

    public void setDaAn(Boolean daAn) {
        this.daAn = daAn;
    }

    public Boolean getDaXoa() {
        return daXoa;
    }

    public void setDaXoa(Boolean daXoa) {
        this.daXoa = daXoa;
    }

    public int getBaoCao() {
        return baoCao;
    }

    public void setBaoCao(int baoCao) {
        this.baoCao = baoCao;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Boolean getDaDuyet() {
        return daDuyet;
    }

    public void setDaDuyet(Boolean daDuyet) {
        this.daDuyet = daDuyet;
    }

    public List<NguoiDung> getNguoiQuanTam() {
        return nguoiQuanTam;
    }

    public void setNguoiQuanTam(List<NguoiDung> nguoiQuanTam) {
        this.nguoiQuanTam = nguoiQuanTam;
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
}
