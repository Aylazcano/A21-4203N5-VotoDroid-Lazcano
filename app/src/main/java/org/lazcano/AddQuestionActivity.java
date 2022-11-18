package org.lazcano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.lazcano.bd.BD;
import org.lazcano.exceptions.MauvaiseQuestion;
import org.lazcano.modele.VDQuestion;
import org.lazcano.service.Service;

public class AddQuestionActivity extends AppCompatActivity {

    //*01-Init BD
    private Service service;
    private BD maBD;

    //*02-Checher la question (Global variable)
    EditText edit_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        setTitle("VoteDroid");

        //*01-Init BD
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);


        Button b = findViewById(R.id.button_pose_la_question);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO ajouter la question dans le service

                try {
                    VDQuestion q = new VDQuestion(); // aller cherche le texte depuis l'interface

                    //*02-Checher la question (Global variable)
                    edit_question = (EditText) findViewById(R.id.edit_question);
                    q.texteQuestion = (edit_question).getText().toString();
                    service.creerQuestion(q);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (MauvaiseQuestion e) {
                    Log.e("CREERQUESTION", "Impossible de créer la question : " + e.getMessage());
                    Toast.makeText(AddQuestionActivity.this,"Impossible de créer la question : " + e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}