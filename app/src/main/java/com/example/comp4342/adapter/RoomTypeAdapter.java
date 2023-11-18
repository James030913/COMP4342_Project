package com.example.comp4342.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4342.R;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolder> {
    private List<RoomType> roomTypes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(RoomType roomType);
    }

    public RoomTypeAdapter(List<RoomType> roomTypes, OnItemClickListener listener) {
        this.roomTypes = roomTypes;
        this.listener = listener;
    }

    @Override
    public RoomTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_type_item, parent, false);
        return new RoomTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoomTypeViewHolder holder, int position) {
        RoomType roomType = roomTypes.get(position);
        holder.bind(roomType, listener);
    }

    @Override
    public int getItemCount() {
        return roomTypes.size();
    }

    public class RoomTypeViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView roomTypeName;
        public TextView description;
        public TextView pricePerNight;
        public TextView totalRooms;

        public RoomTypeViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            roomTypeName = view.findViewById(R.id.roomTypeName);
            description = view.findViewById(R.id.description);
            pricePerNight = view.findViewById(R.id.pricePerNight);
            totalRooms = view.findViewById(R.id.totalRooms);
        }

        public void bind(final RoomType roomType, final OnItemClickListener listener) {
            imageView.setImageResource(roomType.getImageResource());
            roomTypeName.setText(roomType.getRoomTypeName());
            description.setText(roomType.getDescription());
            pricePerNight.setText("Price per night: " + roomType.getPricePerNight());
            totalRooms.setText("Total rooms: " + roomType.getTotalRooms());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(roomType);
                }
            });
        }
    }
}