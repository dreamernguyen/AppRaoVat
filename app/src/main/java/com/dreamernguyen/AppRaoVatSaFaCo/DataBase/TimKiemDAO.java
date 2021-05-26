package com.dreamernguyen.AppRaoVatSaFaCo.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;

import java.util.List;

@Dao
public interface TimKiemDAO {

    @Insert
    void createTemp(TimKiem timKiem);

    @Query("Delete from timKiem Where trangThai= :trangThai")
    void deleteTemp( String trangThai);

    @Delete
    void delete(TimKiem timKiem);

    @Update
    void UpdateTemp(TimKiem timKiem);

    @Query("Select * from timKiem where trangThai= :trangThai ")
    TimKiem findTemp(String trangThai);

    @Query("Select * from timkiem where trangThai= :trangThai")
    List<TimKiem> findList(String trangThai);

    @Query("Select * from timkiem where tieuDe= :tieuDe and trangThai= :trangThai")
    TimKiem checkExists(String tieuDe, String trangThai);
}
