package org.lazcano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.lazcano.bd.BD;
import org.lazcano.databinding.ActivityVoteBinding;
import org.lazcano.exceptions.MauvaisVote;
import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;
import org.lazcano.service.Service;

public class VoteActivity extends AppCompatActivity {

    //*01-Init BD
    private Service service;
    private BD maBD;

    //*02-Cherche la question (Global variable)
    EditText voteName;


    private ActivityVoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        setTitle("VoteDroid");

        //*01-Init BD
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BD")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);

        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.voteButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    VDVote v = new VDVote(); // aller chercher le texte depuis l'interface

                    //*02-Checher le nom (Global variable)
                    voteName = (EditText) binding.voteName;
                    v.nom = (voteName).getText().toString();
                    service.creerVote(v);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                catch (MauvaisVote e){
                    Log.e("VOTEQUESTION", "Impossible de voter la question : " + e.getMessage());
                    Toast.makeText(VoteActivity.this,"Impossible de voter la question : " + e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}