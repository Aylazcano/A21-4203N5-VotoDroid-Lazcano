package org.lazcano;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
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

import java.util.ArrayList;
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



    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKONomVide() throws MauvaisVote {
        VDVote v = new VDVote();
        v.idQuestion = Long.valueOf(1);
        v.nom = "";
        service.creerVote(v, v.idQuestion);

        Assert.fail("Exception MauvaisVote non lancée");
    }


    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKONomCourt() throws MauvaisVote {
        VDVote va = new VDVote();
        va.idQuestion = 1l;
        va.nom = "a";

        VDVote vab = new VDVote();
        vab.idQuestion = 1l;
        vab.nom = "ab";

        VDVote vabc = new VDVote();
        vabc.idQuestion = 1l;
        vabc.nom = "abc";

        service.creerVote(va, va.idQuestion);
        Assert.fail("Exception MauvaisVote non lancée");

        service.creerVote(vab, vab.idQuestion);
        Assert.fail("Exception MauvaisVote non lancée");

        service.creerVote(vabc, vabc.idQuestion);
        Assert.fail("Exception MauvaisVote non lancée");
    }


    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKODejaVote() throws MauvaisVote, MauvaiseQuestion {
        VDVote v = new VDVote();

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        v.idQuestion = question.idQuestion;
        v.nom = "Alexis";
        v.valeurVote = 5;

        VDVote v2 = new VDVote();
        v2.idQuestion = question.idQuestion;
        v2.nom = "Alexis";
        v2.valeurVote = 0;

        service.creerVote(v, v.idQuestion);
        service.creerVote(v2, v2.idQuestion);

        Assert.fail("Exception MauvaisVote non lancée");
    }


    @Test
    public void ajoutVoteOK() throws MauvaisVote, MauvaiseQuestion {
        VDVote v = new VDVote();

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        v.idQuestion = question.idQuestion;
        v.nom = "David";
        v.valeurVote = 5;
        service.creerVote(v, v.idQuestion);

        Assert.assertNotNull(v.idVote);
    }


    @Test
    public void toutesLesQuestionsTriKO() throws MauvaiseQuestion, MauvaisVote {

        //Question 1 = 1 vote
        VDQuestion q1 = new VDQuestion();
        q1.texteQuestion = "Question 1 = 1 vote";
        service.creerQuestion(q1);

        VDVote v1 = new VDVote();
        v1.idQuestion = q1.idQuestion;
        v1.nom = "aaaa";
        service.creerVote(v1, q1.idQuestion);


        //Question 2 = 2 votes
        VDQuestion q2 = new VDQuestion();
        q2.texteQuestion = "Question 2 = 2 votes";
        service.creerQuestion(q2);

        VDVote v2 = new VDVote();
        v2.idQuestion = q2.idQuestion;
        v2.nom = "aaaa";
        service.creerVote(v2, q2.idQuestion);

        VDVote v3 = new VDVote();
        v3.idQuestion = q2.idQuestion;
        v3.nom = "bbbb";
        service.creerVote(v3, q2.idQuestion);


        //Question 3 = 3 votes
        VDQuestion q3 = new VDQuestion();
        q3.texteQuestion = "Question 3 = 3 votes";
        service.creerQuestion(q3);

        VDVote v4 = new VDVote();
        v4.idQuestion = q3.idQuestion;
        v4.nom = "aaaa";
        service.creerVote(v4, q3.idQuestion);

        VDVote v5 = new VDVote();
        v5.idQuestion = q3.idQuestion;
        v5.nom = "bbbb";
        service.creerVote(v5, q3.idQuestion);

        VDVote v6 = new VDVote();
        v6.idQuestion = q3.idQuestion;
        v6.nom = "cccc";
        service.creerVote(v6, q3.idQuestion);

        //Test
        List<VDQuestion> triees = service.toutesLesQuestionsOrdreDescNbVotes();
        Assert.assertEquals("Question 3 = 3 votes", triees.get(0).texteQuestion);
        Assert.assertEquals("Question 2 = 2 votes", triees.get(1).texteQuestion);
        Assert.assertEquals("Question 1 = 1 vote", triees.get(2).texteQuestion);
    }

    @Test
    public void supprimerToutesQuestionsOK() throws MauvaiseQuestion, MauvaisVote {

        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les hommes?";
        question2.texteQuestion = "Aimes-tu les femmes?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        service.supprimerToutesQuestions();

        //Test
        List<VDQuestion> triees = service.toutesLesQuestionsOrdreDescNbVotes();
        Assert.assertTrue(triees.isEmpty());
    }

    @Test
    public void supprimerTousVotesOK() throws MauvaiseQuestion, MauvaisVote {

        //Question 1 = 1 vote
        VDQuestion q1 = new VDQuestion();
        q1.texteQuestion = "Question 1 = 1 vote";
        service.creerQuestion(q1);


        VDVote v1 = new VDVote();
        v1.idQuestion = q1.idQuestion;
        v1.nom = "aaaa";
        service.creerVote(v1, q1.idQuestion);


        //Question 2 = 2 votes
        VDQuestion q2 = new VDQuestion();
        q2.texteQuestion = "Question 2 = 2 votes";
        service.creerQuestion(q2);

        VDVote v2 = new VDVote();
        v2.idQuestion = q2.idQuestion;
        v2.nom = "aaaa";
        service.creerVote(v2, q2.idQuestion);

        VDVote v3 = new VDVote();
        v3.idQuestion = q2.idQuestion;
        v3.nom = "bbbb";
        service.creerVote(v3, q2.idQuestion);


        service.supprimerTousVotes();

        //Test
        List<VDVote> listeVote = bd.monDao().toutesLesVotes();
        Assert.assertTrue(listeVote.isEmpty());
    }


    @After
    public void closeDb() {
        bd.close();
    }

}