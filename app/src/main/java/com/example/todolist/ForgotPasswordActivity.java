package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.todolist.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private String mail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        binding.editTextEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Functions.textChanged(editable.toString(),binding.textInputLayoutEmail,"Valid mail address","Please enter a valid email address",getApplicationContext(),"email");
            }
        });

        binding.buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mail = Functions.whitespaceRemove(binding.editTextEmail);

                if (Functions.isValidMail(mail))
                {
                    resetPassword(mail);
                }
                else if (mail.isEmpty())
                {
                    binding.textInputLayoutEmail.setError("Please enter a valid email address!");
                    binding.textInputLayoutEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.acikKirmizi)));
                }

            }
        });

        binding.cLBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
            }
        });

    }

    private void resetPassword(String email)
    {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Snackbar.make(binding.buttonReset,"Check your email to reset your password!",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    Snackbar.make(binding.buttonReset,"Try again! Something wrong happened!",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}