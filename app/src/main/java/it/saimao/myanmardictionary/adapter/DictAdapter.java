package it.saimao.myanmardictionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import it.saimao.myanmardictionary.DictActivity;
import it.saimao.myanmardictionary.R;
import it.saimao.myanmardictionary.dao.FavDao;
import it.saimao.myanmardictionary.database.MyanmarDictionary;
import it.saimao.myanmardictionary.databinding.ItemDictBinding;
import it.saimao.myanmardictionary.entities.Dict;
import it.saimao.myanmardictionary.entities.Fav;

public class DictAdapter extends ListAdapter<Dict, DictAdapter.DictViewHolder> {

    private static final DiffUtil.ItemCallback<Dict> DIFF_UTIL = new DiffUtil.ItemCallback<Dict>() {
        @Override
        public boolean areItemsTheSame(@NonNull Dict oldItem, @NonNull Dict newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Dict oldItem, @NonNull Dict newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final Context context;
    private TextToSpeech textToSpeech;
    private final FavDao favDao;

    private OnFavItemClickListener onFavItemClickListener;

    public interface OnFavItemClickListener {
        void onFavItemClick(Dict ict);
    }


    public DictAdapter(Context context) {
        super(DIFF_UTIL);
        this.context = context;
        this.textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
        favDao = MyanmarDictionary.getInstance(context).favDao();
    }

    @NonNull
    @Override
    public DictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDictBinding binding = ItemDictBinding.inflate(LayoutInflater.from(context), parent, false);
        return new DictViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DictViewHolder holder, int position) {
        var dict = getCurrentList().get(position);
        holder.bind(dict);
    }

    public void setOnFavItemClickListener(OnFavItemClickListener listener) {
        this.onFavItemClickListener = listener;
    }

    public class DictViewHolder extends RecyclerView.ViewHolder {

        private final ItemDictBinding binding;

        public DictViewHolder(ItemDictBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Dict dict) {
            binding.tvWord.setText(dict.getWord());
            binding.tvMeaning.setText(dict.getTitle());

            Fav fav = favDao.getFavById(dict.getId());
            binding.ibFavorite.setImageResource(fav != null ? R.drawable.ic_remove_favorite : R.drawable.ic_add_favorite);

            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(context, DictActivity.class);
                intent.putExtra("id", dict.getId());
                context.startActivity(intent);
            });

            binding.ibSpeak.setOnClickListener(v -> {
                if (textToSpeech != null) {
                    textToSpeech.speak(dict.getStripWord(), TextToSpeech.QUEUE_FLUSH, null, "");
                }
            });

            binding.ibFavorite.setOnClickListener(v -> {
                addOrRemoveFavorite(dict);
            });
        }

        private void addOrRemoveFavorite(Dict dict) {
            Fav fav = favDao.getFavById(dict.getId());
            if (fav == null) {
                // Add to favorite
                fav = new Fav(dict.getId());
                favDao.addToFavorite(fav);
                binding.ibFavorite.setImageResource(R.drawable.ic_remove_favorite);

            } else {
                // Remove from favorite
                favDao.removeFromFavorite(fav);
                binding.ibFavorite.setImageResource(R.drawable.ic_add_favorite);

                if (onFavItemClickListener != null) {
                    onFavItemClickListener.onFavItemClick(dict);
                }
            }
        }
    }
}
