package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.todolist.databinding.FragmentBottomSheetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BottomSheetFragment extends BottomSheetDialogFragment
{

    private FragmentBottomSheetBinding binding;
    private int year,month,day;
    private String textTitle,date;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private String onlineUserID;


    public BottomSheetFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);

       // binding.textViewTitleBottomSheet.setText(textTitle);
      //  String[] date1 = date.split("\\.");

        /*year=Integer.parseInt(date1[2]);
        month=Integer.parseInt(date1[1]);
        day=Integer.parseInt(date1[0]);
        binding.editTextDate.setText(day+"."+month+"."+year);

        month--;*/

        Calendar calendar=Calendar.getInstance();
         year=calendar.get(Calendar.YEAR);
         month=calendar.get(Calendar.MONTH);
         day=calendar.get(Calendar.DAY_OF_MONTH);

        auth = FirebaseAuth.getInstance();

        currentUser= auth.getCurrentUser();
        onlineUserID=currentUser.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);


        binding.editTextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
                    {
                        Calendar calendar=Calendar.getInstance();
                        int year=calendar.get(Calendar.YEAR);
                        int month=calendar.get(Calendar.MONTH);
                        int day=calendar.get(Calendar.DAY_OF_MONTH);

                        year=i;
                        month=i1;
                        day=i2;
                        binding.editTextDate.setText(day+"."+(month+1)+"."+year);

                        Log.e("year",String.valueOf(i));
                        Log.e("month",String.valueOf(i1));
                        Log.e("day",String.valueOf(i2));

                    }
                },year,month,day);

                datePickerDialog.setOnShowListener(dialog ->
                {
                    int Color = ContextCompat.getColor(view.getContext(), R.color.acikKirmizi);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color);
                });

                datePickerDialog.show();
            }
        });

        binding.buttonAddBottomSheet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               /* Calendar calendar=Calendar.getInstance();
                int year_now=calendar.get(Calendar.YEAR);
                int month_now=calendar.get(Calendar.MONTH);
                int day_now=calendar.get(Calendar.DAY_OF_MONTH);
                month_now++;

                Log.e("year_now",String.valueOf(year_now));
                Log.e("month_now",String.valueOf(month_now));
                Log.e("day_now",String.valueOf(day_now));
*/

                String id=databaseReference.push().getKey();
                String titleText=binding.editTextTitle.getText().toString();
                String dateFrom=28+"."+ 7 +"."+2022;
                String dateTo=binding.editTextDate.getText().toString();

                Todo todo=new Todo(id,titleText,dateFrom,dateTo);

                Log.e("id",id);
                Log.e("titleText",titleText);
                Log.e("dateFrom",dateFrom);
                Log.e("dateTo",dateTo);
                Log.e("onlineUserID",onlineUserID);

                databaseReference.child(id).setValue(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Log.e("Successful","Successful");
                            //Toast.makeText(getActivity(),"Successful",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String error=task.getException().toString();
                            Log.e("error",error);
                            //Toast.makeText(getActivity(),"Failed"+error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });


               /* databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(todo);
                        dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                dismiss();

                ToDoListActivity.todoArraylist.add(todo);

            }
        });



        return binding.getRoot();
    }
}