package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.databinding.ActivityToDoListBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoListActivity extends AppCompatActivity
{
    private ActivityToDoListBinding binding;
    private Adapter adapter;
    static  ArrayList<Todo> todoArraylist=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private String onlineUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_to_do_list);

        firebaseDatabase=FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        onlineUserID=currentUser.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

        holdRecyclerView();
        getDatas();

        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BottomSheetFragment bottomSheetFragment=new BottomSheetFragment();
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

    public void getDatas(){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                todoArraylist.clear();     // this is important, because if we dont clear the list same object again add
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String id = ds.child("id").getValue(String.class);
                    String titleText = ds.child("titleText").getValue(String.class);
                    String dateFrom = ds.child("dateFrom").getValue(String.class);
                    String dateTo = ds.child("dateTo").getValue(String.class);

                    Todo todo=new Todo(id,titleText,dateFrom,dateTo);
                    todoArraylist.add(todo);
                }
                Collections.reverse(todoArraylist);

                adapter = new Adapter(ToDoListActivity.this,todoArraylist,getSupportFragmentManager());
                binding.rv.setAdapter(adapter);

                if (todoArraylist.isEmpty())
                {
                    binding.imageViewAddHere.setVisibility(View.VISIBLE);
                    binding.textViewAddHere.setVisibility(View.VISIBLE);
                    binding.rv.setVisibility(View.INVISIBLE);
                }
                else
                {
                    binding.imageViewAddHere.setVisibility(View.INVISIBLE);
                    binding.textViewAddHere.setVisibility(View.INVISIBLE);
                    binding.rv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Snackbar.make(binding.rv,"Fail to get data.",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void holdRecyclerView(){
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = null;
    }

}