package com.example.messenger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messenger.utils.AndroidUtil;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginOTPActivity extends AppCompatActivity {
    String phoneNumber;
    EditText otpInput;
    Button nextButton;
    ProgressBar progressBar;
    TextView resendOtpTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        phoneNumber = getIntent().getExtras().getString("phone");
        sendOTP(phoneNumber,false);
        //Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_SHORT).show();

//        Map<String,String> data = new HashMap<>();
//        FirebaseFirestore.getInstance().collection("test").add(data);
        setWidget();
    }
    public void setWidget(){
        otpInput = findViewById(R.id.login_otp);
        nextButton = findViewById(R.id.login_next_btn);
        progressBar = findViewById(R.id.login_progress_bar);
        resendOtpTextView = findViewById(R.id.resend_otp_textview);
    }
    public void sendOTP(String phoneNumber, boolean isResend){
        setInProgress(true);
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(timeoutSeconds, TimeUnit.SECONDS).setActivity(this).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
                setInProgress(false);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                setInProgress(false);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                resendingToken = forceResendingToken;
                AndroidUtil.showToast(getApplicationContext(),"OTP sent successful");
                setInProgress(false);
            }
        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }
    public void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }
    public void signIn(PhoneAuthCredential phoneAuthCredential){

    }
}