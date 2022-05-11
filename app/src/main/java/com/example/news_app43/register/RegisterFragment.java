package com.example.news_app43.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app43.R;
import com.example.news_app43.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initListener();
        createCallBack();
    }

    private void createCallBack() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String fireCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(fireCode, forceResendingToken);
                binding.okey.setOnClickListener(view -> {
                    initcode(fireCode);

                });
            }

            private void initcode(String fireCode) {
                String myCode = binding.code.getText().toString().trim();
                if (TextUtils.isEmpty(myCode)) {
                    binding.code.setError("Поле пустое");
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(fireCode, myCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            Log.e("TAG", "onComplete: ");
                            Navigation.findNavController(requireView()).navigate(R.id.boardFragment);
                        } else {
                            binding.code.setError("Некорректный код");
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }

    private void initListener() {
        binding.login.setOnClickListener(view1 -> {
            String phone = binding.number.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                binding.number.setError("Поле пустое");
            } else {
                register(phone);
                Log.e("TAG", "initListener: " + phone);
                binding.login.setVisibility(View.GONE);
                binding.okey.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();

    }

    public void register(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
