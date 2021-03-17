package com.example.noteapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneFragment extends Fragment {
    private EditText editText, editCode;
    private Button buttonForNumber, buttonForCode;
    private String id;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks Callback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editPhone);
        editCode = view.findViewById(R.id.editCode);
        buttonForNumber = view.findViewById(R.id.btnPhone);
        buttonForCode = view.findViewById(R.id.btnCode);
        buttonForNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms();
                buttonForNumber.setVisibility(View.GONE);
                buttonForCode.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
                editCode.setVisibility(View.VISIBLE);
                buttonForCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        check();
                    }
                });
            }
        });
        checkCallBack();
    }

    private void checkCallBack() {
        Callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "Completed");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "Failed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                id = s;
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                close();
            } else {
                Toast.makeText(requireContext(), "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void check() {
        String code = editCode.getText().toString().trim();
        if (code.length() == 6 && TextUtils.isDigitsOnly(code))
            signIn(PhoneAuthProvider.getCredential(id, code));

    }

    private void sms() {
        String phone = editText.getText().toString().trim();
        if (phone.isEmpty()) {
            editText.setError("Your number");
            editText.requestFocus();
            return;
        }
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).
                setPhoneNumber(phone).setTimeout(60L, TimeUnit.SECONDS).setActivity(requireActivity()).setCallbacks(Callback).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}