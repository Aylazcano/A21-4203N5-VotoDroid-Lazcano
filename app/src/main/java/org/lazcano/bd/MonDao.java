package org.lazcano.bd;

import androidx.room.Dao;
import androidx.room.Insert;

import org.lazcano.modele.VDQuestion;

@Dao
public interface MonDao {
    @Insert
    Long insertQuestion(VDQuestion v);

    //TODO Compléter les autres actions

}
