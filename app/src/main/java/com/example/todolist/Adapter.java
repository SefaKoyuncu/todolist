package com.example.todolist;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.FragmentBottomSheetBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CardviewTasarimNesneleriniTutucu>
{
    private Context mContext;
    private List<Todo> todoArraylist;
    FragmentManager fragmentManager;

    private FirebaseDatabase database;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private String onlineUserID;


    public Adapter(Context mContext, List<Todo> todoArraylist, FragmentManager fragmentManager)
    {
        this.mContext = mContext;
        this.todoArraylist = todoArraylist;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public CardviewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_todo_design,parent,false);
        return new CardviewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardviewTasarimNesneleriniTutucu holder, int position)
    {
        final Todo todo=todoArraylist.get(position);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        onlineUserID=currentUser.getUid();


        holder.textViewTitle.setText(todo.getTitleText());
        holder.textViewDateFromCard.setText(todo.getDateFrom());
        holder.textViewDateTo.setText(todo.getDateTo());

        boolean isExpandable=todoArraylist.get(position).isExpandable();

        if (isExpandable)
        {
            TransitionManager.beginDelayedTransition(holder.clCard,new AutoTransition());
            holder.cLexpandable.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cLexpandable.setVisibility(View.GONE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                database.getReference().child("tasks").child(onlineUserID).child(todo.getId()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return todoArraylist.size();
    }

    public class CardviewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder
    {

        public TextView textViewTitle,textViewDateFromCard,textViewDateTo;
        public CardView cardView;
        public ConstraintLayout clCard,cLexpandable;
        public ImageView imageView;


        public CardviewTasarimNesneleriniTutucu(@NonNull View itemView)
        {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.textViewTitle);
            textViewDateFromCard=itemView.findViewById(R.id.textViewDateFromCard);
            textViewDateTo=itemView.findViewById(R.id.textViewDateTo);
            cardView=itemView.findViewById(R.id.cardView);
            clCard=itemView.findViewById(R.id.clCard);
            cLexpandable=itemView.findViewById(R.id.cLexpandable);
            imageView=itemView.findViewById(R.id.imageView);

            clCard.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Todo todo=todoArraylist.get(getAdapterPosition());
                    todo.setExpandable(!todo.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

    private  void viewBottomSheet()
    {
        //View view = activity.getLayoutInflater ().inflate (R.layout.bottom_sheet, null);
        // View view = inflater.inflate( R.layout.bottom_sheet, null );
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, null);

        TextView textViewTitleBottomSheet = view.findViewById(R.id.textViewTitleBottomSheet);
        textViewTitleBottomSheet.setText("deneme");

        //TextView txtBackup = (TextView)view.findViewById(R.id.txt_backup);
        //TextView txtDetail = (TextView)view.findViewById(R.id.txt_detail);
        //TextView txtOpen = (TextView)view.findViewById(R.id.txt_open);
        //final TextView txtUninstall = (TextView)view.findViewById( R.id.txt_backup);

        /*final Dialog mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();


        txtBackup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked Backup", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });

        txtDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked Detail", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });

        txtOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked Open", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });*/
    }
}
