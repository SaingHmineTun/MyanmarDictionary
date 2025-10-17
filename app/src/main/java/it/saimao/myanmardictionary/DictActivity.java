package it.saimao.myanmardictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.ChipGroup;

import java.util.Locale;

import it.saimao.myanmardictionary.dao.DictDao;
import it.saimao.myanmardictionary.dao.FavDao;
import it.saimao.myanmardictionary.database.MyanmarDictionary;
import it.saimao.myanmardictionary.databinding.ActivityDictBinding;
import it.saimao.myanmardictionary.entities.Dict;
import it.saimao.myanmardictionary.entities.Fav;

public class DictActivity extends AppCompatActivity {

    private ActivityDictBinding binding;
    private DictDao dictDao;
    private FavDao favDao;
    private TextToSpeech textToSpeech;

    private Dict dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initDatabase();
        initView();
        initTextToSpeech();
        initActions();
    }

    private void initActions() {
        binding.ivSpeak.setOnClickListener(v -> {
            if (textToSpeech != null) {
                textToSpeech.speak(dict.getStripWord(), TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });

        binding.ivFavorite.setOnClickListener(v -> {
            addOrRemoveFavorite(dict);
        });

        binding.ivBack.setOnClickListener(v -> finish());

    }


    private void addOrRemoveFavorite(Dict dict) {
        Fav fav = favDao.getFavById(dict.getId());
        if (fav == null) {
            // Add to favorite
            fav = new Fav(dict.getId());
            favDao.addToFavorite(fav);
            binding.ivFavorite.setImageResource(R.drawable.ic_remove_favorite);

        } else {
            // Remove from favorite
            favDao.removeFromFavorite(fav);
            binding.ivFavorite.setImageResource(R.drawable.ic_add_favorite);
        }
    }


    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Language not supported
                }
            }
        });
    }

    private void initDatabase() {
        dictDao = MyanmarDictionary.getInstance(this).dicDao();
        favDao = MyanmarDictionary.getInstance(this).favDao();
    }

    private void initDictById(Dict dict) {

        binding.tvWord.setText(dict.getWord());
        binding.tvContent.setText(Html.fromHtml(dict.getDefinition()));
        if (!dict.getKeywords().isEmpty()) {
            binding.lyKeywords.setVisibility(View.VISIBLE);
            showKeywords(dict.getKeywords().split(","));
        } else {
            binding.lyKeywords.setVisibility(View.GONE);
        }
        if (!dict.getSynonyms().isEmpty()) {
            binding.lySynonyms.setVisibility(View.VISIBLE);
            showSynonyms(dict.getSynonyms().split(","));
        } else {
            binding.lySynonyms.setVisibility(View.GONE);
        }
    }

    private void initView() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if (id != -1) {
            dict = dictDao.getDictById(id);
            initDictById(dict);
        }
        Fav fav = favDao.getFavById(id);
        binding.ivFavorite.setImageResource(fav == null ? R.drawable.ic_add_favorite : R.drawable.ic_remove_favorite);
    }

    private void addToChipGroup(ChipGroup group, String[] words) {
        group.removeAllViews();
        for (String word : words) {
            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.chip, null);
            tv.setOnClickListener(v -> {
                dict = dictDao.getSingleDictByWord(word);
                if (dict != null) {
                    Utils.setSavedWord(dict.getStripWord());
                    initDictById(dict);
                }
            });
            tv.setText(word);
            group.addView(tv);
        }
    }

    private void showKeywords(String... keywords) {
        addToChipGroup(binding.cgKeywords, keywords);
    }

    private void showSynonyms(String... synonyms) {
        addToChipGroup(binding.cgSynonyms, synonyms);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();

    }
}