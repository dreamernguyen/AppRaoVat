package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tinh {
    @SerializedName("province_id")
    @Expose
    private String maTinh;
    @SerializedName("province_name")
    @Expose
    private String tenTinh;

    public Tinh(String maTinh, String tenTinh) {
        this.maTinh = maTinh;
        this.tenTinh = tenTinh;
    }

    public String getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(String maTinh) {
        this.maTinh = maTinh;
    }

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }
}
