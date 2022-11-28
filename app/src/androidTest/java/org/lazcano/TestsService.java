package org.lazcano;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lazcano.bd.BD;
import org.lazcano.exceptions.MauvaisVote;
import org.lazcano.exceptions.MauvaiseQuestion;
import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;
import org.lazcano.service.Service;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestsService {

    private BD bd;
    private Service service;

    // S'exécute avant chacun des tests. Crée une BD en mémoire
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        bd = Room.inMemoryDatabaseBuilder(context, BD.class).build();
        service = new Service(bd);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOVide() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOCourte() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOLongue() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        for (int i = 0 ; i < 256 ; i ++) question.texteQuestion += "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOIDFixe() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aaaaaaaaaaaaaaaa";
        question.idQuestion = 5L;
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test
    public void ajoutQuestionOK() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        Assert.assertNotNull(question.idQuestion);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOExiste() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesQuestions() dans ServiceImplementation
        Assert.fail("Exception MauvaiseQuestion non lancée");
    }

    @Test
    public void toutesLesQuestionsTri() throws MauvaiseQuestion, MauvaisVote {

        //Question 1 = 1 vote
        VDQuestion q1 = new VDQuestion();
        q1.texteQuestion = "Question 1 = 1 vote";
        service.creerQuestion(q1);

        VDVote v1 = new VDVote();
        v1.idQuestion = q1.idQuestion;
        v1.nom = "aaaa";
        service.creerVote(v1);


        //Question 2 = 2 votes
        VDQuestion q2 = new VDQuestion();
        q2.texteQuestion = "Question 2 = 2 votes";
        service.creerQuestion(q2);

        VDVote v2 = new VDVote();
        v2.idQuestion = q2.idQuestion;
        v2.nom = "aaaa";
        service.creerVote(v2);

        VDVote v3 = new VDVote();
        v3.idQuestion = q2.idQuestion;
        v3.nom = "bbbb";
        service.creerVote(v3);


        //Question 3 = 3 votes
        VDQuestion q3 = new VDQuestion();
        q3.texteQuestion = "Question 3 = 3 votes";
        service.creerQuestion(q3);

        VDVote v4 = new VDVote();
        v4.idQuestion = q3.idQuestion;
        v4.nom = "aaaa";
        service.creerVote(v4);

        VDVote v5 = new VDVote();
        v5.idQuestion = q3.idQuestion;
        v5.nom = "bbbb";
        service.creerVote(v5);

        VDVote v6 = new VDVote();
        v6.idQuestion = q3.idQuestion;
        v6.nom = "cccc";
        service.creerVote(v6);

        //Test
        List<VDQuestion> triees = service.toutesLesQuestionsOrdreDescNbVotes();
        Assert.assertEquals("Question 3 = 3 votes", triees.get(0).texteQuestion);
        Assert.assertEquals("Question 2 = 2 votes", triees.get(1).texteQuestion);
        Assert.assertEquals("Question 1 = 1 vote", triees.get(2).texteQuestion);
    }


    /*
    @After
    public void closeDb() {
        bd.close();
    }
    */
}