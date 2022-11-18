package org.lazcano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.lazcano.bd.BD;
import org.lazcano.bd.MonDao;
import org.lazcano.databinding.ActivityMainBinding;
import org.lazcano.exceptions.MauvaiseQuestion;
import org.lazcano.modele.VDQuestion;
import org.lazcano.service.Service;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //*01-Init BD
    private Service service;
    private BD maBD;

    QuestionsAdapter adapter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("VoteDroid");

        //*01-Init BD
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);

        /*Add in Oncreate() funtion after setContentView()*/

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

//        for (VDQuestion q: maBD.monDao().toutesLesQuestions()) {
//            adapter.list.add(q);
//        }
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
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//        service = new Service(maBD);
//        creerQuestion();
//    }
//
//    private void creerQuestion (){
//        try{
//            VDQuestion maQuestion = new VDQuestion();
//            maQuestion.texteQuestion = "As-tu hâte au nouveau film The Matrix Resurrections?";
//            service.creerQuestion(maQuestion);
//        }catch (MauvaiseQuestion m){
//            Log.e("CREERQUESTION", "Impossible de créer la question : " + m.getMessage());
//        }
//    }
}