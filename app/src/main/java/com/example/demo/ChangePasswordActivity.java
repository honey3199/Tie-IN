package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.repository.UserRepository;
import com.example.demo.storage.LocalStorage;

public class ChangePasswordActivity extends AppCompatActivity {

    UserRepository userRepository;
    EditText etNewPassword, etConfirmNewPassword;
    Button btnChangePassword;
    LocalStorage localStorage;
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        localStorage = new LocalStorage(getApplication());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("phone"))
            phone = bundle.getString("phone", null);

        userRepository = new UserRepository(this);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        btnChangePassword = findViewById(R.id.btn_change_password);

        btnChangePassword.setOnClickListener(v -> {
            if (etNewPassword.getText().toString().equals("")) {
                Toast.makeText(this, "please enter password to change", Toast.LENGTH_LONG).show();
            } else if (!etConfirmNewPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                Toast.makeText(this, "password and confirm password fields should be match.", Toast.LENGTH_LONG).show();
            } else {
                if (phone.isEmpty()) {
                    userRepository.updatePassword(etNewPassword.getText().toString(), localStorage.getPhone());
                    localStorage.setPassword(etNewPassword.getText().toString());
                    onBackPressed();
                    return;
                }
                userRepository.updatePassword(etNewPassword.getText().toString(), phone);
                Intent i = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                i.putExtra("phone", phone);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (phone.isEmpty()) {
            Intent i = new Intent(ChangePasswordActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        } else
            Toast.makeText(this, "please change password to proceed..!!!", Toast.LENGTH_LONG).show();
    }
}