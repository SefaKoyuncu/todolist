package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.todolist.databinding.ActivityLoginBinding;
import com.example.todolist.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private ActivityRegisterBinding binding;
    private String mail,password;
    private Dialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        binding.buttonRegisterPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                mail=Functions.whitespaceRemove(binding.editTextEmail);
                password=Functions.whitespaceRemove(binding.editTextPassword);

                if(!Functions.isNetworkAvailable(getApplicationContext()))
                {
                    dialog.setContentView(R.layout.no_internet_connection);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button buttonOkNoConnection=dialog.findViewById(R.id.buttonOkNoConnection);
                    buttonOkNoConnection.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

                else
                {
                    if (Functions.isValidMail(mail)&&Functions.isValidPassword(password))
                    {
                        registerUser(mail,password);
                    }
                    else if (mail.isEmpty() || password.isEmpty())
                    {
                        Functions.mailPasswordIsEmpty(mail,password, binding.textInputLayoutEmail,binding.textInputLayoutPassword,getApplicationContext());
                    }
                }
            }
        });

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

        binding.editTextPassword.addTextChangedListener(new TextWatcher()
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
                Functions.textChanged(editable.toString(),binding.textInputLayoutPassword,"Valid password","Please enter a valid password",getApplicationContext(),"password");
            }
        });

        binding.imageViewInfoPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.setContentView(R.layout.password_valid_info);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button buttonOkPasswordInfo=dialog.findViewById(R.id.buttonOkPasswordInfo);

                buttonOkPasswordInfo.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        binding.cLBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //onBackPressed();
                startActivity(new Intent(RegisterActivity.this,ToDoListActivity.class));
            }
        });
    }

    private void registerUser(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            User user=new User(email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    showMainActivity();
                                }
                            });
                        }
                        else
                        {
                            Snackbar.make(binding.buttonRegisterPage,"Oopps! An error occurred, please try again",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void showMainActivity()
    {
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
        finish();
    }

}