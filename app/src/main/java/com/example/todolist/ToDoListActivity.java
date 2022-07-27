package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.databinding.ActivityToDoListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity
{
    private ActivityToDoListBinding binding;
    private Adapter adapter;
    private ArrayList<Todo> todoArraylist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_to_do_list);

        Todo todo=new Todo("read a book","12.12.2022","13.12.2022");
        Todo todo2=new Todo("market","14.12.2022","15.12.2022");
        Todo todo3=new Todo("cleaning","16.12.2022","17.12.2022");

        todoArraylist.add(todo);
        todoArraylist.add(todo2);
        todoArraylist.add(todo3);

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter=new Adapter(getApplicationContext(),todoArraylist,getSupportFragmentManager());
        binding.rv.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BottomSheetFragment bottomSheetFragment=new BottomSheetFragment("Add your to do","17.12.2022");
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                logoutUser();
            }
        });
    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}