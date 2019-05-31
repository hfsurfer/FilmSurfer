package com.hugof.filmsurfer;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG ="RecyclerViewAdapter";

    private ArrayList<String> lMovieImageNames = new ArrayList<>();
    private ArrayList<String> lMovieImages = new ArrayList<>();
    private Context lContext;

    private OnMovieListener lOnMovieListener;

    public RecyclerViewAdapter(ArrayList<String> lMovieImageNames, ArrayList<String> lMovieImages, Context lContext, OnMovieListener onMovieListener) {
        this.lMovieImageNames = lMovieImageNames;
        this.lMovieImages = lMovieImages;
        this.lContext = lContext;
        this.lOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listmovies,parent,false);
        ViewHolder holder = new ViewHolder(view, lOnMovieListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called.");

        Glide.with(lContext)
                .asBitmap()
                .load(lMovieImages.get(position))
                .into(holder.MovieImage);
        holder.MovieImageName.setText(lMovieImageNames.get(position));



    }

    @Override
    public int getItemCount() {
        return lMovieImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnMovieListener, View.OnClickListener {
        CircleImageView MovieImage;
        TextView MovieImageName;
        RelativeLayout ParentMovieList;
        OnMovieListener onMovieListener;
        public ViewHolder(View itemView, OnMovieListener onMovieListener){
            super(itemView);
            MovieImage = itemView.findViewById(R.id.MovieImage);
            MovieImageName = itemView.findViewById(R.id.MovieImageName);
            ParentMovieList = itemView.findViewById(R.id.ParentMovieList);
            this.onMovieListener = onMovieListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void OnMovieClick(int position) {
            onMovieListener.OnMovieClick(getAdapterPosition());

        }

        @Override
        public void onClick(View view) {
            onMovieListener.OnMovieClick(getAdapterPosition());
        }
    }

    public interface OnMovieListener{
        void OnMovieClick(int position);
    }

    public void appendMovies(List<Movies> moviesToAppend) {
        MoviesList.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

}
