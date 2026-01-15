package com.example.midterms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.login_username);
        EditText pinEditText = findViewById(R.id.login_pin);
        Button loginButton = findViewById(R.id.login_button);
        TextView registerTextView = findViewById(R.id.registerText);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String pin = pinEditText.getText().toString();

            String rfid = null;

            if (username.equalsIgnoreCase("user") && pin.equals("123456")) {
                rfid = "26-0002";
            } else if (username.equalsIgnoreCase("Admin") && pin.equals("123456")) {
                rfid = "26-0001";
            }

            if (rfid != null) {
                Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("rfid", rfid);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or PIN", Toast.LENGTH_SHORT).show();
            }
        });

        String text = getString(R.string.log_in_text);
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#DDA0FF"));
                ds.setUnderlineText(true);
            }
        };

        int startIndex = text.indexOf("Sign up");
        int endIndex = startIndex + "Sign up".length();

        if (startIndex != -1) {
            ss.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            registerTextView.setText(ss);
            registerTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
