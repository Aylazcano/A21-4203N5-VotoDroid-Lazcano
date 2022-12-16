package org.lazcano.service;

import org.lazcano.bd.BD;
import org.lazcano.exceptions.MauvaisVote;
import org.lazcano.exceptions.MauvaiseQuestion;
import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Service {

    private BD maBD;

    public Service(BD maBD){
        this.maBD = maBD;
    }

    public void creerQuestion(VDQuestion vdQuestion) throws MauvaiseQuestion {
        // Validation
        if (vdQuestion.texteQuestion == null || vdQuestion.texteQuestion.trim().length() == 0) throw new MauvaiseQuestion("Question vide");
        if (vdQuestion.texteQuestion.trim().length() < 5) throw new MauvaiseQuestion("Question trop courte");
        if (vdQuestion.texteQuestion.trim().length() > 255) throw new MauvaiseQuestion("Question trop longue");
        if (vdQuestion.idQuestion != null) throw new MauvaiseQuestion("Id non nul. La BD doit le gérer");

        // Doublon du texte de la question
        for (VDQuestion q : toutesLesQuestionsOrdreDescNbVotes()){
            if (q.texteQuestion.toUpperCase().equals(vdQuestion.texteQuestion.toUpperCase())){
                    throw new MauvaiseQuestion("Question existante");
            }
        }

        // Ajout
        vdQuestion.idQuestion = maBD.monDao().insertQuestion(vdQuestion);
    }

    
    public void creerVote(VDVote vdVote, long qId) throws MauvaisVote {
        // Validation
        if (vdVote.nom == null || vdVote.nom.length() == 0) throw new MauvaisVote("Nom vide");
        if (vdVote.nom.trim().length() > 0 && vdVote.nom.trim().length() < 4) throw new MauvaisVote("Nom trop courte");

        // *3 Déja voté

//        // *3a Méthode Java (voir Service.creerQuestion)
//        for (VDVote v : toutesLesVotes()){
//            if (v.nom.toUpperCase().equals(vdVote.nom.toUpperCase()) && v.idQuestion.equals(vdVote.idQuestion)){
//                throw new MauvaisVote("Déjà voté");
//            }
//        }

        // *3b Méthode SQL (BD, MonDao, Service)
        if (!maBD.monDao().dejaVote(vdVote.nom, vdVote.idQuestion).isEmpty()) throw new MauvaisVote("Déjà voté"); //HELP!!!

        // Ajout
        vdVote.idVote = maBD.monDao().insertVote(vdVote);
    }

    
    public List<VDQuestion> toutesLesQuestionsOrdreDescNbVotes() {
        //TODO Présentement :   retourne une liste vide
        //TODO À faire :        trier la liste reçue en BD par le nombre de votes et la retourner
        List<VDQuestion> liste = new ArrayList<>(maBD.monDao().toutesLesQuestions());
        // TODO trier
        Collections.sort(liste, new Comparator<VDQuestion>() {
            @Override
            public int compare(VDQuestion q1, VDQuestion q2) {
                // Si q1 doit etre avant q2 (return -1), apres (return 1) ou pareil (return 0)
                int nbVotes1 = maBD.monDao().nbVotesPour(q1.idQuestion);
                int nbVotes2 = maBD.monDao().nbVotesPour(q2.idQuestion);

                if (nbVotes1 > nbVotes2)
                    return -1;

                if (nbVotes1 < nbVotes2)
                    return 1;

                return 0;
            }
        });

        return liste;
    }

//    private int nbVotesPour(VDQuestion q) {
//        return maBD.monDao().lesVotesPour(q.idQuestion).size();
//        return maBD.monDao().nbVotesPour(q.idQuestion);
//    }

//    // *3a Méthode Java (voir Service.creerQuestion)
//    public List<VDVote> toutesLesVotes (String nom, Long qId){
//        List<VDVote> liste = new ArrayList<>(maBD.monDao().toutesLesVotes());
//        return liste;
//    }

    
    public float moyenneVotes(VDQuestion question) {

        return 0;
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {
        return null;
    }
	
	public void supprimerTousQuestions(){
        maBD.monDao().suprimeTousQuestions();
	}
	
	public void supprimerTousVotes(){
        maBD.monDao().suprimeTousVotes();
	}
}
