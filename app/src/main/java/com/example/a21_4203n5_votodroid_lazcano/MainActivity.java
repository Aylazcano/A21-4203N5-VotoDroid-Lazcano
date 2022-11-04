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
        //        chart = findViewById(R.id.chart);
//
//
//        /* Settings for the graph - Change me if you want*/
//        chart.setMaxVisibleValueCount(6);
//        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1); // only intervals of 1 day
//        xAxis.setLabelCount(7);
//        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setLabelCount(4, false);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setGranularity(1);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
//        chart.getDescription().setEnabled(false);
//        chart.getAxisRight().setEnabled(false);
//
//
//
//        /* Data and function call to bind the data to the graph */
//        Map<Integer, Integer> dataGraph = new HashMap<Integer, Integer>() {{
//            put(3, 2);
//            put(4, 1);
//            put(5, 4);
//        }};
//        setData(dataGraph);
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