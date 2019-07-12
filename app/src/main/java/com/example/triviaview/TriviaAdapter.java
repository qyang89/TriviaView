package com.example.triviaview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder> {


        private List<String> triviaUrls;
        private Context context;
        TextView tvQuestion;

        TriviaAdapter(List<String> triviaUrls) {
            this.triviaUrls = triviaUrls;
        }

        @NonNull
        @Override
        public TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.trivia_item, parent, false);

            context = parent.getContext();
            return new TriviaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TriviaViewHolder holder, int position) {
            final String url = triviaUrls.get(position);
          //  Glide.with(context).load(url).into(holder.tvQuestion); //this shouldn't be a photo, replace with text box
            holder.tvQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //declare and init fragmentmanager
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                    //declare and init fragment transaction
                    FragmentTransaction transaction = manager.beginTransaction();

                    //declare and init fragment
                    DynamicFragment dynamicFragment = new DynamicFragment();

                    //***bundle is created to pass URL into Dynamic Fragment***
                    Bundle bundle = new Bundle();
                    bundle.putString("URL", url);
                    dynamicFragment.setArguments(bundle);

                    //add fragment to layout
//                transaction.add(R.id.frame_layout, dynamicFragment, "").commit();

                    //container layout from activity main
                    ConstraintLayout container = ((AppCompatActivity) context).findViewById(R.id.container);
                    transaction.add(container.getId(), dynamicFragment, "").commit();

                }
            });



        }

        @Override
        public int getItemCount() {
            return triviaUrls.size();
        }

        class TriviaViewHolder extends RecyclerView.ViewHolder {
            TextView tvQuestion; ///replace this

            TriviaViewHolder(@NonNull View itemView) {
                super(itemView);
                tvQuestion = itemView.findViewById(R.id.tv_question);  //replace this
            }


        }

    }

