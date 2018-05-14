package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeRecyclerViewAdapter extends RecyclerView.Adapter<YoutubeRecyclerViewAdapter.RecycleHolder> {

    private FirebaseExerciseModel mFirebaseExerciseModel = FirebaseExerciseModel.getInstance();

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RecycleHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        Exercise info = mFirebaseExerciseModel.getExercises().get(position);
        holder.bind(info);
    }

    @Override
    public int getItemCount() {
        return mFirebaseExerciseModel.getExercises().size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mAmount, mTitle, mRepetitions;
        private ImageView mImage;


        private Exercise mInfo;

        public RecycleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_item, parent, false));
            itemView.setOnClickListener(this);

            mAmount = itemView.findViewById(R.id.txtAmount);
            mTitle = itemView.findViewById(R.id.txtTitle);
            mImage = itemView.findViewById(R.id.imgView);
            mRepetitions = itemView.findViewById(R.id.txtRepetitions);
        }

        public void bind(Exercise info) {
            mInfo = info;
            mAmount.setText(mInfo.getAmount());
            mTitle.setText(mInfo.getTitle());
            mRepetitions.setText(mInfo.getRepetitions());
            InputStream imageStream = itemView.getResources().openRawResource(R.raw.youtube_img);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImage.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), mInfo.getAmount(), Toast.LENGTH_SHORT).show();
            Context context = v.getContext();
            Intent intent = new Intent(context, YoutubeActivity.class);
            context.startActivity(intent);
        }
    }
}
