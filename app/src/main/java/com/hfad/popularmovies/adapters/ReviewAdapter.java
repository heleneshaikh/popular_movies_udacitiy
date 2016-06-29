package com.hfad.popularmovies.adapters;

import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private static final String TAG = "app";
    private ArrayList<Review> reviewList;
    private Context context;
    TextView tvReview;

    public void setReviewList(ArrayList<Review> movieList) {
        this.reviewList.clear();
        this.reviewList.addAll(movieList);
        notifyDataSetChanged();
    }

    public ReviewAdapter(Context context, ArrayList<Review> reviewList) {
        this.context = context;
        this.reviewList = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.review_cardview);
        }
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        Review review = reviewList.get(position);
        tvReview = (TextView) cardView.findViewById(R.id.tv_review);
        tvReview.setText(review.getContent());
        TextView tvAuthor = (TextView) cardView.findViewById(R.id.tv_author);
        tvAuthor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviewList.isEmpty() ? 0 : reviewList.size();
    }


}
