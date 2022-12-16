package org.lazcano.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;

import java.util.List;

@Dao
public interface MonDao {
    @Insert
    Long insertQuestion(VDQuestion q);

    //TODO Compléter les autres actions

    @Query("SELECT * FROM VDQuestion")
    List<VDQuestion> toutesLesQuestions();


    @Insert
    Long insertVote(VDVote v);

//    @Query("SELECT * FROM VDVote")
//    List<VDVote> tousLesVotes();


    @Query("SELECT * FROM VDVote WHERE idQuestion = :qId")
    List<VDVote> lesVotesPour(Long qId);

    @Query("SELECT count(*) FROM VDVote WHERE idQuestion = :qId")
    int nbVotesPour(Long qId);

//    // *3a Méthode Java (voir Service.creerQuestion)
    @Query("SELECT * FROM VDVote")
    public List<VDVote> toutesLesVotes();

    // *3b Méthode SQL (BD, MonDao, Service)
    @Query("SELECT * FROM VDVote WHERE nom = :nom AND idQuestion = :qId")
    public List<VDVote> dejaVote (String nom, Long qId);

    //Chercher qId
    @Query("SELECT idQuestion FROM VDQuestion WHERE texteQuestion = :question ")
    public long qId(String question);

    //Supprimer question
    @Query("DELETE FROM VDQuestion")
    void suprimeToutesQuestions();

    //Supprimer vote
    @Query("DELETE FROM vdvote")
    void suprimeTousVotes();

}
