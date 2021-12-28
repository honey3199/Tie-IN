package com.example.demo.views;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.system.StructPollfd;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ContactUsFragment extends Fragment {

    EditText etName, etPhone, etEmail, etMessage;
    Button sendMail;

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        etName = view.findViewById(R.id.et_name_contact);
        etEmail = view.findViewById(R.id.et_email_contact);
        etPhone = view.findViewById(R.id.et_phone_contact);
        etMessage = view.findViewById(R.id.et_message_contact);
        sendMail = view.findViewById(R.id.btn_send_mail);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendMail.setOnClickListener(v -> {
            if (etMessage.getText().toString().isEmpty() || etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), "please fill all the Details..!!!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {"prekshasheth1998@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
            intent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString() + "\n\n" + etName.getText().toString() + "\n +91 " + etPhone.getText().toString());
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(requireActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}