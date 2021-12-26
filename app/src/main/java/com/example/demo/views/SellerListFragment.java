package com.example.demo.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.demo.repository.VendorRepository;
import com.example.demo.room.model.Vendor;

import java.util.List;

public class SellerListFragment extends Fragment implements SellerAdapter.VendorCellClickListener {

    RecyclerView rvSellersList;
    List<Vendor> vendors;
    VendorRepository vendorRepository;

    public static SellerListFragment newInstance() {
        return new SellerListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_list, container, false);
        if (view != null) {
            rvSellersList = view.findViewById(R.id.rv_sellers_list);
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                exitConfirmationDialog();
            }
            return false;
        });
        return view;
    }

    private void exitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Confirm Exit");
        builder.setMessage("Are you sure You want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> requireActivity().finish());
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        builder.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vendorRepository = new VendorRepository(requireActivity());
        vendors = vendorRepository.getVendors();

        rvSellersList.setHasFixedSize(true);
        rvSellersList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvSellersList.setAdapter(new SellerAdapter(vendors, this));
    }

    @Override
    public void vendorClicked(Vendor vendor) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SellerDetailFragment.newInstance(vendor.getId())).addToBackStack("VendorDetailPage").commit();
    }

}