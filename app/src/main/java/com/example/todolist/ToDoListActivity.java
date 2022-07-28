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
    static ArrayList<Todo> todoArraylist=new ArrayList<>();

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

      /*  Todo todo=new Todo("read a book","12.12.2022","13.12.2022");
        Todo todo2=new Todo("market","14.12.2022","15.12.2022");
        Todo todo3=new Todo("cleaning","16.12.2022","17.12.2022");

        todoArraylist.add(todo);
        todoArraylist.add(todo2);
        todoArraylist.add(todo3);*/

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

       // Log.e("currentuser1",currentUser.getUid());

        holdRecyclerView();
        getDatas();


        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BottomSheetFragment bottomSheetFragment=new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
                //Log.e("currentuser2",currentUser.getUid());
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

        //getAllCourses();
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
                for(DataSnapshot ds : snapshot.getChildren()) {

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

               /* if (todoArraylist.isEmpty()){
                    mainBinding.imageViewAddTask.setVisibility(View.VISIBLE);
                }
                else {
                    mainBinding.imageViewAddTask.setVisibility(View.INVISIBLE);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ToDoListActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void holdRecyclerView(){
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = null;
    }

}