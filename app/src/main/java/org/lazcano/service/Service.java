package org.lazcano.service;

import org.lazcano.bd.BD;
import org.lazcano.exceptions.MauvaisVote;
import org.lazcano.exceptions.MauvaiseQuestion;
import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;

import java.util.ArrayList;
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
        for (VDQuestion q : toutesLesQuestions()){
            if (q.texteQuestion.toUpperCase().equals(vdQuestion.texteQuestion.toUpperCase())){
                    throw new MauvaiseQuestion("Question existante");
            }
        }

        // Ajout
        vdQuestion.idQuestion = maBD.monDao().insertQuestion(vdQuestion);
    }

    
    public void creerVote(VDVote vdVote) throws MauvaisVote {
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

    
    public List<VDQuestion> toutesLesQuestions() {
        //TODO Présentement :   retourne une liste vide
        //TODO À faire :        trier la liste reçue en BD par le nombre de votes et la retourner
        List<VDQuestion> liste = new ArrayList<>(maBD.monDao().toutesLesQuestions());
        // TODO trier




        return liste;
    }
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
	
	public void supprimerQuestions(){
	}
	
	public void supprimerVotes(){
	}
}
