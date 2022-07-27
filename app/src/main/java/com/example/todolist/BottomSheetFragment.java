package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.todolist.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class BottomSheetFragment extends BottomSheetDialogFragment
{

    private FragmentBottomSheetBinding binding;
    private int year,month,day;
    private String textTitle,date;

    public BottomSheetFragment()
    {
    }

    public BottomSheetFragment(String textTitle, String date)
    {
        this.textTitle = textTitle;
        this.date = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);

        binding.textViewTitleBottomSheet.setText(textTitle);
        String[] date1 = date.split("\\.");

        year=Integer.parseInt(date1[2]);
        month=Integer.parseInt(date1[1]);
        day=Integer.parseInt(date1[0]);
        binding.editTextDate.setText(day+"."+month+"."+year);

        month--;

        binding.editTextDate.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
                    {
                        Calendar calendar=Calendar.getInstance();
                        year=calendar.get(Calendar.YEAR);
                        month=calendar.get(Calendar.MONTH);
                        day=calendar.get(Calendar.DAY_OF_MONTH);

                        year=i;
                        month=i1;
                        day=i2;
                        binding.editTextDate.setText(day+"."+(month+1)+"."+year);
                    }
                },year,month,day);
                int color = ContextCompat.getColor(view.getContext(), R.color.acikKirmizi);

                datePickerDialog.setOnShowListener(dialog ->
                {
                    int Color = ContextCompat.getColor(view.getContext(), R.color.acikKirmizi);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color);
                });

                datePickerDialog.show();
            }
        });


        return binding.getRoot();
    }
}