package com.example.todolist;

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
import android.view.View;
import android.widget.Button;

import com.example.todolist.databinding.ActivityLoginBinding;
import com.example.todolist.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity
{
    private ActivityRegisterBinding binding;
    private String mail,password;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_register);
        dialog=new Dialog(this);

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
                        dialog.setContentView(R.layout.loading);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                startActivity(new Intent(RegisterActivity.this, ToDoListActivity.class));
                                finish();
                            }
                        }, 3000);
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
                Functions.textChanged(editable.toString(),binding.textInputLayoutEmail,"Valid mail address","Please enter a valid email address",getApplicationContext());
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
                Functions.textChanged(editable.toString(),binding.textInputLayoutPassword,"Valid password","Please enter a valid password",getApplicationContext());
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
}