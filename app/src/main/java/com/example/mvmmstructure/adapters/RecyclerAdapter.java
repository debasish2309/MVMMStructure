package com.example.mvmmstructure.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvmmstructure.R;
import com.example.mvmmstructure.models.NicePlace;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<NicePlace> mNicePlace  = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(List<NicePlace> mNicePlace, Context context) {
        this.mNicePlace = mNicePlace;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).mName.setText(mNicePlace.get(position).getTitle());

        RequestOptions defaultOptions = new RequestOptions().error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(mNicePlace.get(position).getImageUrl())
                .into(((ViewHolder)holder).mImage);

    }

    @Override
    public int getItemCount() {
        return mNicePlace.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mImage;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.image_name);
        }
    }
}
