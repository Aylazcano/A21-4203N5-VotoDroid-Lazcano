package org.lazcano;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.lazcano.modele.VDQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> {
    public List<VDQuestion> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView question;
        public ImageButton button;
        public MyViewHolder(LinearLayout v) {
            super(v);
            button = v.findViewById(R.id.resultatImageButton);
            question = v.findViewById(R.id.question);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionsAdapter() {
        list = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        VDQuestion questionCourante = list.get(position);
        holder.question.setText(questionCourante.texteQuestion);// TODO setText sur un integer crash

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "coucou " + questionCourante.question, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ResultActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VoteActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    // renvoie la taille de la liste
    @Override
    public int getItemCount() {
        return list.size();
    }
}
