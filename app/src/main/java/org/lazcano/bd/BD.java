package org.lazcano.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;

@Database(entities = {VDQuestion.class, VDVote.class}, version = 1,  exportSchema = true)
public abstract class BD extends RoomDatabase {
    public abstract MonDao monDao();
}


