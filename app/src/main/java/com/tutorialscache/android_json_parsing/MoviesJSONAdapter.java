package com.tutorialscache.android_json_parsing;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesJSONAdapter extends BaseAdapter {
    MoviesModel moviesModel;
    ArrayList<MoviesModel> moviesJsonData;
    LayoutInflater layoutInflater;
    Context context;

    public MoviesJSONAdapter(Context context, ArrayList<MoviesModel> moviesJsonData) {
        this.context=context;
        this.moviesJsonData=moviesJsonData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return moviesJsonData.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesJsonData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moviesJsonData.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.custom_row,null);
            TextView movieNameTv = convertView.findViewById(R.id.movieNameTv);
            ImageView movieImg = convertView.findViewById(R.id.movieImg);
            RatingBar ratingBar = convertView.findViewById(R.id.movieRating);

            moviesModel = moviesJsonData.get(position);
            movieNameTv.setText(moviesModel.getName());

            //Picasso load image
            Picasso.with(context).load(moviesModel.getImage()).into(movieImg);

            //set ratting
            ratingBar.setRating((float) moviesModel.getRatting());
        }
        return convertView;
    }
}
