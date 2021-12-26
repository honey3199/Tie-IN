package com.example.demo.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.example.demo.repository.VendorRepository;
import com.example.demo.room.model.Vendor;

public class SellerDetailFragment extends Fragment {

    private static final String VENDOR_ID = "vendor_id";
    private Vendor vendor;
    VendorRepository vendorRepository;
    TextView tvShopName, tvSellerName, tvAddress, tvEmail, tvPhone, tvServices, tvRating;
    RatingBar rbRating;
    ImageView ivCall, ivDirection, ivShare;

    public static SellerDetailFragment newInstance(String vendor) {
        SellerDetailFragment fragment = new SellerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VENDOR_ID, vendor);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_detail, container, false);
        tvShopName = view.findViewById(R.id.tv_shop_title);
        tvSellerName = view.findViewById(R.id.tv_seller_name);
        tvAddress = view.findViewById(R.id.tv_address);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvServices = view.findViewById(R.id.tv_services);
        tvRating = view.findViewById(R.id.tv_rating);
        rbRating = view.findViewById(R.id.rb_rating);
        ivCall = view.findViewById(R.id.iv_call);
        ivDirection = view.findViewById(R.id.iv_direction);
        ivShare = view.findViewById(R.id.iv_share);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vendorRepository = new VendorRepository(requireActivity());
        if (getArguments() != null)
            vendor = vendorRepository.getVendor(getArguments().getString(VENDOR_ID));

        tvShopName.setText(vendor.getShop());
        tvSellerName.setText(vendor.getName());
        tvAddress.setText(vendor.getAddress());
        tvEmail.setText(vendor.getEmail());
        tvPhone.setText(vendor.getPhone());
        tvServices.setText(vendor.getProductType());
        tvRating.setText(String.valueOf(vendor.getRating()));
        rbRating.setRating(vendor.getRating());

        tvEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {vendor.getEmail()};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        });

        ivCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + vendor.getPhone()));
            startActivity(intent);
        });

        ivDirection.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + vendor.getShop());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        ivShare.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = vendor.getShop() + "\nOwner :- " + vendor.getName() + "\nAddress :- " + vendor.getAddress() + "\nServices :- " + vendor.getProductType() + "\n\nContactDetails \nEmail:- " + vendor.getEmail() + "\nPhone :- " + vendor.getPhone();
            String sub = vendor.getShop();
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
            myIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        });
    }

}