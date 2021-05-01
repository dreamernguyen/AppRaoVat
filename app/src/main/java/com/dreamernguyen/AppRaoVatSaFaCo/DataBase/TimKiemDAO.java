package com.dreamernguyen.AppRaoVatSaFaCo.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;

@Dao
public interface TimKiemDAO {

    @Insert
    void createTemp(TimKiem timKiem);

    @Query("Delete from timKiem Where trangThai= :trangThai")
    void deleteTemp( String trangThai);

    @Update
    void UpdateTemp(TimKiem timKiem);

    @Query("Select * from timKiem where trangThai= :trangThai ")
    TimKiem findTemp(String trangThai);
}
