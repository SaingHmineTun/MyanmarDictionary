package it.saimao.myanmardictionary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.saimao.myanmardictionary.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initToolbar();
        initAboutContent();
        initContactCards();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("About");
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initAboutContent() {
        // About App content
        String aboutApp = "<p>Myanmar Dictionary is a comprehensive offline dictionary application for Android " +
                "that provides definitions and explanations for Myanmar language words.</p>" +
                "<p><b>Features:</b></p>" +
                "<ul>" +
                "<li>Offline dictionary with extensive word database</li>" +
                "<li>Search functionality for quick word lookup</li>" +
                "<li>Favorite words management</li>" +
                "<li>Clean and intuitive user interface</li>" +
                "</ul>";

        binding.tvAboutApp.setText(Html.fromHtml(aboutApp));

        // About Developer content
        String aboutDeveloper = "<p>A passionate Android developer with expertise in creating user-friendly mobile applications. " +
                "With a strong foundation in Java and Android development, Sai has focused on creating applications " +
                "that serve the Myanmar community with useful tools and resources.</p>";

        TextView tvAboutDeveloper = findViewById(R.id.tv_about_developer);
        tvAboutDeveloper.setText(Html.fromHtml(aboutDeveloper));
    }

    private void initContactCards() {
        // GitHub Card
        CardView githubCard = findViewById(R.id.card_github);
        githubCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SaingHmineTun"));
            startActivity(intent);
        });

        // WordPress Card
        CardView wordpressCard = findViewById(R.id.card_wordpress);
        wordpressCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itsaimao.wordpress.com/"));
            startActivity(intent);
        });

        // Facebook Card
        CardView facebookCard = findViewById(R.id.card_facebook);
        facebookCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/saing.hmine.tun/"));
            startActivity(intent);
        });

        // LinkedIn Card
        CardView linkedinCard = findViewById(R.id.card_linkedin);
        linkedinCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sai-saing-hmine-tun-08b67114b/"));
            startActivity(intent);
        });

        // Email Card
        CardView emailCard = findViewById(R.id.card_email);
        emailCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:saimao.muse@gmail.com"));
            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }
}