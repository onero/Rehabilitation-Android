package dk.adamino.rehabilitation.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.ExerciseInfo;
import dk.adamino.rehabilitation.GUI.Model.YoutubeModel;
import dk.adamino.rehabilitation.R;

public class YoutubeListActivity extends AppCompatActivity {

    private YoutubeModel mYoutubeModel = YoutubeModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Recyclerview
         */
        setContentView(R.layout.youtube_recycler);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<ExerciseInfo> exercises = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            exercises.add(new ExerciseInfo("Exercise: " + i));
        }

        mYoutubeModel.setExerciseInfo(exercises);

        /**
         * Incredible recyclerview adapter
         */
        YoutubeRecyclerViewAdapter adapter = new YoutubeRecyclerViewAdapter();

        recyclerView.setAdapter(adapter);
    }

    }
