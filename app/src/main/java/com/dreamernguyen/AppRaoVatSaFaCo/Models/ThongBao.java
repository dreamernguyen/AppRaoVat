package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongBao {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idNguoiDung")
    @Expose
    private NguoiDung idNguoiDung;
    @SerializedName("idTruyXuat")
    @Expose
    private String idTruyXuat;
    @SerializedName("noiDung")
    @Expose
    private String noiDung;
    @SerializedName("loaiThongBao")
    @Expose
    private String loaiThongBao;
    @SerializedName("thoiGianTao")
    @Expose
    private String thoiGianTao;
    @SerializedName("thoiGianCapNhat")
    @Expose
    private String thoiGianCapNhat;

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

    public String getIdTruyXuat() {
        return idTruyXuat;
    }

    public void setIdTruyXuat(String idTruyXuat) {
        this.idTruyXuat = idTruyXuat;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getLoaiThongBao() {
        return loaiThongBao;
    }

    public void setLoaiThongBao(String loaiThongBao) {
        this.loaiThongBao = loaiThongBao;
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
