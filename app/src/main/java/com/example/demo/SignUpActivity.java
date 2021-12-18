package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.data.Status;
import com.example.demo.repository.UserRepository;
import com.google.android.gms.common.api.Response;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtPhone, edtOTP, etName, etEmail;
    private Button verifyOTPBtn, generateOTPBtn;
    private String verificationId;
    private TextView tvGoToSignIn;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository(this);

        edtPhone = findViewById(R.id.et_phone);
        edtOTP = findViewById(R.id.et_otp);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        verifyOTPBtn = findViewById(R.id.btn_verify_otp);
        generateOTPBtn = findViewById(R.id.btn_signup);
        tvGoToSignIn = findViewById(R.id.tv_click_to_signin);

        tvGoToSignIn.setOnClickListener(v -> {
            Intent i = new Intent(SignUpActivity.this, SingInActivity.class);
            startActivity(i);
            finish();
        });

        generateOTPBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                String phone = "+91" + edtPhone.getText().toString();
                edtOTP.setVisibility(View.VISIBLE);
                verifyOTPBtn.setVisibility(View.VISIBLE);
                sendVerificationCode(phone);
            }
        });

        verifyOTPBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(edtOTP.getText().toString());
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String response = userRepository.register(edtPhone.getText().toString(), etName.getText().toString(), etEmail.getText().toString());
                        if (response.contains(Status.ERROR.getCode())) {
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent i = new Intent(SignUpActivity.this, ChangePasswordActivity.class);
                        i.putExtra("phone", edtPhone.getText().toString());
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtOTP.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
}
