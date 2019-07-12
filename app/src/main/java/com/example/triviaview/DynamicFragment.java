package com.example.triviaview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class DynamicFragment extends Fragment {

    //declare

    Button btnBack;

    //new full size fragment


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment, container, false);

        //get image view from layout
      //  ImageView fullimg = view.findViewById(R.id.frame_layout);  //need to replace this


        //passing the url to get to image
      //  Glide.with(getContext()).load(this.getArguments().getString("URL")).into(fullimg);
        return view;


    }
}
