package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TinNhan {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idNguoiGui")
    @Expose
    private NguoiDung idNguoiGui;
    @SerializedName("idNguoiNhan")
    @Expose
    private NguoiDung idNguoiNhan;
    @SerializedName("noiDung")
    @Expose
    private String noiDung;
    @SerializedName("thoiGianGui")
    @Expose
    private String thoiGianGui;
    @SerializedName("thoiGianCapNhat")
    @Expose
    private String thoiGianCapNhat;
    @SerializedName("linkAnh")
    @Expose
    private String linkAnh;
    @SerializedName("xoaTinVoi")
    @Expose
    private List<String> xoaTinVoi = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NguoiDung getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(NguoiDung idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public NguoiDung getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(NguoiDung idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGianGui() {
        return thoiGianGui;
    }

    public void setThoiGianGui(String thoiGianGui) {
        this.thoiGianGui = thoiGianGui;
    }

    public String getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(String thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public List<String> getXoaTinVoi() {
        return xoaTinVoi;
    }

    public void setXoaTinVoi(List<String> xoaTinVoi) {
        this.xoaTinVoi = xoaTinVoi;
    }
}
