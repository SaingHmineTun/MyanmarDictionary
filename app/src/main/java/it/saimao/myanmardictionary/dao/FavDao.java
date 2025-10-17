package it.saimao.myanmardictionary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.saimao.myanmardictionary.entities.Fav;

@Dao
public interface FavDao {
    @Insert
    void addToFavorite(Fav fav);

    @Delete
    void removeFromFavorite(Fav fav);

    @Query("SELECT * FROM favorite WHERE id = :id;")
    Fav getFavById(int id);


    @Query("SELECT * FROM favorite;")
    List<Fav> getAllFavorites();

}
