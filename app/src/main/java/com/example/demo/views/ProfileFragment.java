package com.example.demo.views;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.repository.UserRepository;
import com.example.demo.room.model.User;
import com.example.demo.storage.LocalStorage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    UserRepository userRepository;
    User user;
    PopupWindow popupWindow;
    LocalStorage localStorage;

    TextView tvUserName, tvUserEmail, tvUserPhone;
    ImageView editEmail, editName, icCamera, ivProfilePhoto;

    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    String dir;

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
        icCamera = view.findViewById(R.id.iv_camera);
        ivProfilePhoto = view.findViewById(R.id.iv_profile);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRepository = new UserRepository(requireActivity());
        localStorage = new LocalStorage(requireActivity().getApplication());

        userRepository.getUserLD(localStorage.getPhone()).observe(getViewLifecycleOwner(), user -> initializeUser(user));

        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        newdir.mkdirs();

        icCamera.setOnClickListener(v -> selectImage());

        editName.setOnClickListener(v -> openEditPopup(user.getName(), "Edit Name"));
        editEmail.setOnClickListener(v -> openEditPopup(user.getEmail(), "Edit Email"));
    }

    private void initializeUser(User user) {
        this.user = user;
        Glide.with(requireContext())
                .load(user.getProfileImage())
                .placeholder(R.drawable.ic_profile)
                .centerInside()
                .into(ivProfilePhoto);
        tvUserName.setText("Name :- " + user.getName());
        tvUserEmail.setText("Email :- " + user.getEmail());
        tvUserPhone.setText("Phone :- " + user.getPhone());
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

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    submitButton.setEnabled(false);
                    submitButton.getBackground().setAlpha(128);
                }
            }
        });
        submitButton.setOnClickListener(v -> {
            switch (title) {
                case "Edit Name":
                    userRepository.updateUserName(editText.getText().toString(), user.getPhone());
                    break;
                case "Edit Email":
                    userRepository.updateUserEmail(editText.getText().toString(), user.getPhone());
                    break;
            }
            popupWindow.dismiss();
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                count++;
                String file = dir+count+".jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                }
                catch (IOException e)
                {
                }

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, 1);
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);*/
            } else if (options[item].equals("Choose from Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Toast.makeText(requireContext(), "Pic saved", Toast.LENGTH_LONG).show();
            } else if (requestCode == 2) {
                if (data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(requireContext().getContentResolver().openInputStream(selectedImage));
                        storeImage(yourSelectedImage);
                    } catch (IOException e) {
                        Log.e("ProfileActivity", "The image is not loaded : $e");
                    }
                }
            }
        }
    }

    private void storeImage(Bitmap yourSelectedImage) {
        ivProfilePhoto.setImageBitmap(yourSelectedImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        userRepository.updateProfileImage(byteArray, localStorage.getPhone());
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