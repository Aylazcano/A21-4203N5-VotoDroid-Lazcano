package com.example.a21_4203n5_votodroid_lazcano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.example.a21_4203n5_votodroid_lazcano.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    QuestionsAdapter adapter;
    private ActivityMainBinding binding;
//    Barchart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //*1* Redondant?
        setTitle("VoteDroid");

        /*Add in Oncreate() funtion after setContentView()*/

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view); //*1* Redondant?

        // initialisation du recycler
        this.initRecycler();
        this.remplirRecycler();

        binding.buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    private void remplirRecycler() {
        for (int i = 0 ; i < 100 ; i++) {
            Questions q = new Questions();
            q.question ="Que penses-tu de Disney? " +i;
            adapter.list.add(q);
        }
        adapter.notifyDataSetChanged();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new QuestionsAdapter();
        recyclerView.setAdapter(adapter);
    }

}