package com.example.demo.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo.room.model.Vendor;

import java.util.List;

@Dao
public interface VendorDao {

    @Insert
    Long insertVendor(Vendor Vendor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVendors(List<Vendor> vendors);

    @Delete
    void deleteVendor(Vendor Vendor);

    @Query("SELECT * FROM vendor WHERE id = :id")
    Vendor getVendor(String id);

    @Query("SELECT * FROM vendor")
    List<Vendor> getVendors();
}
