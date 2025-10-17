package it.saimao.myanmardictionary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.saimao.myanmardictionary.dao.DictDao;
import it.saimao.myanmardictionary.dao.FavDao;
import it.saimao.myanmardictionary.entities.Dict;
import it.saimao.myanmardictionary.entities.Fav;

@Database(entities = {Dict.class, Fav.class}, version = 1)
public abstract class MyanmarDictionary extends RoomDatabase {
    public abstract DictDao dicDao();

    public abstract FavDao favDao();

    private static MyanmarDictionary instance;

    public static MyanmarDictionary getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyanmarDictionary.class, "myanmar_dictionary")
                    .createFromAsset("database/myanmar_dictionary.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
