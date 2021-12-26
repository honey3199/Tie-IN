package com.example.demo.repository;

import android.content.Context;

import com.example.demo.room.dao.VendorDao;
import com.example.demo.room.model.Vendor;

import java.io.Serializable;
import java.util.List;

public class VendorRepository extends Repository {

    VendorDao vendorDao;

    public VendorRepository(Context context) {
        super(context);
        vendorDao = database.getVendorDao();
    }

    public List<Vendor> getVendors() {
        return vendorDao.getVendors();
    }

    public void insertVendors(List<Vendor> vendors) {
        vendorDao.insertVendors(vendors);
    }

    public Vendor getVendor(String vendorId) {
        return vendorDao.getVendor(vendorId);
    }
}
