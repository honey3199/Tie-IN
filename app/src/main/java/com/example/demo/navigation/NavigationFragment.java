package com.example.demo.navigation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo.R;
import com.example.demo.SignInActivity;
import com.example.demo.views.ProfileFragment;


public class NavigationFragment extends Fragment {

    private Callbacks callbacks;

    public interface Callbacks {
        void onMasterItemClicked(Fragment fragment);
    }

    TextView tvHome, tvAboutUs, tvContactUs, tvChangePassword, tvLogout;

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer, container, false);
        tvHome = view.findViewById(R.id.tv_home);
        tvAboutUs = view.findViewById(R.id.tv_about_us);
        tvContactUs = view.findViewById(R.id.tv_contact_us);
        tvChangePassword = view.findViewById(R.id.tv_change_password);
        tvLogout = view.findViewById(R.id.tv_sign_out);

        view.findViewById(R.id.navigation_header).setOnClickListener(v -> {
            callbacks.onMasterItemClicked(ProfileFragment.newInstance());
            /*FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment.newInstance());
            fragmentTransaction.commit();*/
        });

        tvHome.setOnClickListener(v -> {
            callbacks.onMasterItemClicked(ProfileFragment.newInstance());
            /*FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SellerListFragment.newInstance());
            fragmentTransaction.commit();*/
        });

        tvAboutUs.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment.newInstance());
            fragmentTransaction.commit();
        });

        tvContactUs.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment.newInstance());
            fragmentTransaction.commit();
        });

        tvChangePassword.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment.newInstance());
            fragmentTransaction.commit();
        });

        tvLogout.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), SignInActivity.class);
            startActivity(i);
            requireActivity().finish();
        });

        return view;
    }
}
