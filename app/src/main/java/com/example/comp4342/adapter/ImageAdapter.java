package com.example.comp4342.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4342.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Item> items;

    public ImageAdapter(List<Item> items) {
        this.items = items;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(items.get(position).getImageResource());
        holder.textView.setText(items.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Item {
        private int imageResource;
        private String text;

        public Item(int imageResource, String text) {
            this.imageResource = imageResource;
            this.text = text;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String getText() {
            return text;
        }
    }
}