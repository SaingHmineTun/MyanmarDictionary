package it.saimao.myanmardictionary.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import it.saimao.myanmardictionary.entities.Dict;

@Dao
public interface DictDao {
    @Query("SELECT * FROM dictionary WHERE LOWER(stripword) LIKE LOWER(:word) || '%'")
    List<Dict> getDictByWord(String word);

    @Query("SELECT * FROM dictionary WHERE _id = :id;")
    Dict getDictById(int id);

    @Query("SELECT * FROM dictionary WHERE LOWER(stripword) = LOWER(:word);")
    Dict getSingleDictByWord(String word);

}
