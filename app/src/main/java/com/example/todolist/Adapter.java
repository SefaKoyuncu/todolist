package com.example.todolist;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.FragmentBottomSheetBinding;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CardviewTasarimNesneleriniTutucu>
{
    private Context mContext;
    private List<Todo> todoArraylist;
    FragmentManager fragmentManager;

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

        holder.textViewTitle.setText(todo.getTitleText());
        holder.textViewDateFrom.setText(todo.getDateFrom());
        holder.textViewDateTo.setText(todo.getDateTo());

        holder.cLUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BottomSheetFragment bottomSheetFragment=new BottomSheetFragment("deneme","29.07.2022");
                bottomSheetFragment.show(fragmentManager,bottomSheetFragment.getTag());
                //viewBottomSheet();
            }
        });

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

    }

    @Override
    public int getItemCount()
    {
        return todoArraylist.size();
    }

    public class CardviewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder
    {

        public TextView textViewTitle,textViewDateFrom,textViewDateTo;
        public CardView cardView;
        public ConstraintLayout clCard,cLexpandable,cLUpdate;


        public CardviewTasarimNesneleriniTutucu(@NonNull View itemView)
        {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.textViewTitle);
            textViewDateFrom=itemView.findViewById(R.id.textViewDateFrom);
            textViewDateTo=itemView.findViewById(R.id.textViewDateTo);
            cardView=itemView.findViewById(R.id.cardView);
            clCard=itemView.findViewById(R.id.clCard);
            cLexpandable=itemView.findViewById(R.id.cLexpandable);
            cLUpdate=itemView.findViewById(R.id.cLUpdate);

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
