package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.repository.UserRepository;

public class ChangePasswordActivity extends AppCompatActivity {

    UserRepository userRepository;
    EditText etNewPassword, etConfirmNewPassword;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle bundle = getIntent().getExtras();
        String phone = bundle.getString("phone", null);

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
        Toast.makeText(this, "please change password to proceed..!!!", Toast.LENGTH_LONG).show();
    }
}