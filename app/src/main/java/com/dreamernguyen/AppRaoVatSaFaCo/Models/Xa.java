package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Xa {
    @SerializedName("ward_id")
    @Expose
    private String maXa;
    @SerializedName("ward_name")
    @Expose
    private String tenXa;

    public String getMaXa() {
        return maXa;
    }

    public void setMaXa(String maXa) {
        this.maXa = maXa;
    }

    public String getTenXa() {
        return tenXa;
    }

    public void setTenXa(String tenXa) {
        this.tenXa = tenXa;
    }
}
