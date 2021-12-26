package com.example.demo.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.room.model.Vendor;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {

    private final List<Vendor> vendors;
    private VendorCellClickListener vendorCellClickListener;

    public SellerAdapter(List<Vendor> vendors, @NonNull VendorCellClickListener vendorCellClickListener) {
        this.vendors = vendors;
        this.vendorCellClickListener = vendorCellClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.one_seller_view, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Vendor vendor = vendors.get(position);
        holder.tvName.setText(vendor.getShop());
        holder.tvAddress.setText(vendor.getAddress());
        holder.rbRating.setRating(vendor.getRating());
        holder.layout.setOnClickListener(view -> {
            vendorCellClickListener.vendorClicked(vendor);
        });
    }


    @Override
    public int getItemCount() {
        return vendors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivLogo;
        public TextView tvName, tvAddress;
        public RatingBar rbRating;
        public ConstraintLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tvName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_seller_location);
            rbRating = (RatingBar) itemView.findViewById(R.id.rb_seller_rating);
            layout = itemView.findViewById(R.id.layout_one_seller);
        }
    }

    public interface VendorCellClickListener {
        void vendorClicked(Vendor vendor);
    }
}
