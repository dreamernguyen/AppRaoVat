package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaiViet {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idNguoiDung")
    @Expose
    private NguoiDung idNguoiDung;
    @SerializedName("noiDung")
    @Expose
    private String noiDung;
    @SerializedName("linkAnh")
    @Expose
    private List<String> linkAnh = null;
    @SerializedName("luotThich")
    @Expose
    private List<NguoiDung> luotThich = null;
    @SerializedName("anBaiVoi")
    @Expose
    private List<String> anBaiVoi = null;
    @SerializedName("daAn")
    @Expose
    private Boolean daAn;
    @SerializedName("daXoa")
    @Expose
    private Boolean daXoa;
    @SerializedName("baoCao")
    @Expose
    private int baoCao;
    @SerializedName("thoiGianTao")
    @Expose
    private String thoiGianTao;
    @SerializedName("thoiGianCapNhat")
    @Expose
    private String thoiGianCapNhat;


    //Đăng bài
    public BaiViet(String noiDung,List<String> linkAnh) {
        this.noiDung = noiDung;
        this.linkAnh = linkAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NguoiDung getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(NguoiDung idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public List<String> getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(List<String> linkAnh) {
        this.linkAnh = linkAnh;
    }

    public List<NguoiDung> getLuotThich() {
        return luotThich;
    }

    public void setLuotThich(List<NguoiDung> luotThich) {
        this.luotThich = luotThich;
    }

    public List<String> getAnBaiVoi() {
        return anBaiVoi;
    }

    public void setAnBaiVoi(List<String> anBaiVoi) {
        this.anBaiVoi = anBaiVoi;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
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

    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }
}
