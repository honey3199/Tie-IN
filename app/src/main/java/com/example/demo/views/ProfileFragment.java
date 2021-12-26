package com.example.demo.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.example.demo.repository.UserRepository;
import com.example.demo.room.model.User;
import com.example.demo.storage.LocalStorage;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {
    UserRepository userRepository;
    User user;
    PopupWindow popupWindow;
    LocalStorage localStorage;

    TextView tvUserName, tvUserEmail, tvUserPhone;
    ImageView editEmail, editName;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvUserEmail = view.findViewById(R.id.tv_user_email);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserPhone = view.findViewById(R.id.tv_user_phone);
        editName = view.findViewById(R.id.edit_name);
        editEmail = view.findViewById(R.id.edit_email);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRepository = new UserRepository(requireActivity());
        localStorage = new LocalStorage(requireActivity().getApplication());

        user = userRepository.getUser(localStorage.getPhone());

        tvUserName.setText(tvUserName.getText().toString() + user.getName());
        tvUserEmail.setText(tvUserEmail.getText().toString() + user.getEmail());
        tvUserPhone.setText(tvUserPhone.getText().toString() + user.getPhone());

        editName.setOnClickListener(v -> openEditPopup(user.getName(), "Edit Name"));
        editEmail.setOnClickListener(v -> openEditPopup(user.getEmail(), "Edit Email"));
    }

    private void openEditPopup(String name, String title) {
        View customView = getLayoutInflater().inflate(R.layout.edit_popup, null);
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(this.getView(), Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dimBehind(popupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        TextView tvTitle = customView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        EditText editText = customView.findViewById(R.id.et_edit_details);
        editText.setText(name);
        Button submitButton = customView.findViewById(R.id.btn_edit_submit);
        submitButton.setOnClickListener(v -> {
            switch (title) {
                case "Edit Name":
                    userRepository.updateUserName(editText.getText().toString(), user.getPhone());
                case "Edit Email":
                    userRepository.updateUserEmail(editText.getText().toString(), user.getPhone());
            }
        });
    }

    private void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }
}