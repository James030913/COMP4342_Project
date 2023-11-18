package com.example.comp4342;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Button bookButton;
    TextView detailDesc, detailTitle, location, price;
    ImageView detailImage;

    int hotelID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bookButton = findViewById(R.id.bookButton);
        detailDesc = findViewById(R.id.Desc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        location = findViewById(R.id.Loc);
        price = findViewById(R.id.price);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Desc"));
            detailImage.setImageResource(bundle.getInt("Image"));
            detailTitle.setText(bundle.getString("Title"));
            location.setText(bundle.getString("Loc"));
            price.setText(bundle.getString("Price"));
            hotelID = bundle.getInt("HotelID");
        }

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to execute when the button is clicked
                Intent intent = new Intent(getApplicationContext(), AfterActivity.class);

                // Get the hotelID of the selected RoomType

                // Put hotelID into the Intent
                intent.putExtra("hotelID", hotelID);

                // Start AfterActivity
                startActivity(intent);
            }
        });
    }


}