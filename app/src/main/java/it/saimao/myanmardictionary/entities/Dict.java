package it.saimao.myanmardictionary.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "dictionary",
        indices = {
                @Index(value = {"stripword"}),
                @Index(value = {"word"})
        })
public class Dict {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;
    private String word;
    @ColumnInfo(name = "stripword")
    private String stripWord;
    private String title;
    private String definition;
    private String keywords;
    @ColumnInfo(name = "synonym")
    private String synonyms;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStripWord() {
        return stripWord;
    }

    public void setStripWord(String stripWord) {
        this.stripWord = stripWord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dict)) return false;
        Dict dict = (Dict) o;
        return id == dict.id && Objects.equals(word, dict.word) && Objects.equals(stripWord, dict.stripWord) && Objects.equals(title, dict.title) && Objects.equals(definition, dict.definition) && Objects.equals(keywords, dict.keywords) && Objects.equals(synonyms, dict.synonyms) && Objects.equals(picture, dict.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, stripWord, title, definition, keywords, synonyms, picture);
    }
}
