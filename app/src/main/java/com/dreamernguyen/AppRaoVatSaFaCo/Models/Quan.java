package com.dreamernguyen.AppRaoVatSaFaCo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quan {
    @SerializedName("district_id")
    @Expose
    private String maQuan;
    @SerializedName("district_name")
    @Expose
    private String tenQuan;

    public Quan(String maQuan, String tenQuan) {
        this.maQuan = maQuan;
        this.tenQuan = tenQuan;
    }

    public String getMaQuan() {
        return maQuan;
    }

    public void setMaQuan(String maQuan) {
        this.maQuan = maQuan;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }
}
