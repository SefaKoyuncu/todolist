package com.example.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class Functions
{
    public static String whitespaceRemove(EditText editText)
    {
        return editText.getText().toString().replaceAll("\\s","");
    }

    public static boolean isValidMail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password)
    {
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[.#?!@$%^&*-]).{8,}$";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pat.matcher(password).matches();
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void mailPasswordIsEmpty(String mail, String password, TextInputLayout textInputLayoutEmail, TextInputLayout textInputLayoutPassword, Context mContext)
    {
        if (mail.isEmpty() && password.isEmpty()) {
            textInputLayoutEmail.setError("Please enter a valid email address");
            textInputLayoutEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.acikKirmizi)));
            textInputLayoutPassword.setError("Please enter a valid password");
            textInputLayoutPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.acikKirmizi)));
        } else if (password.isEmpty()) {
            textInputLayoutPassword.setError("Please enter a valid password");
            textInputLayoutPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.acikKirmizi)));
        } else {
            textInputLayoutEmail.setError("Please enter a valid email address");
            textInputLayoutEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.acikKirmizi)));
        }
    }

    public static void textChanged(String text, TextInputLayout textInputLayout, String helperText, String errorText, Context mContext)
    {
        if (isValidPassword(text))
        {
            textInputLayout.setHelperText(helperText);
        }
        else
        {
            textInputLayout.setError(errorText);
            textInputLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.acikKirmizi)));
        }
    }


}
