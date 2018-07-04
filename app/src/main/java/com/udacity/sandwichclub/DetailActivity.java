package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    private final String TAG=getClass().getName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView mImageView;
    private TextView mAlsoKnowTv;
    private TextView mIngredientTv;
    private TextView mOriginTv;
    private TextView mDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView =findViewById(R.id.image_iv);
        mAlsoKnowTv= (TextView) findViewById(R.id.also_known_tv);
        mIngredientTv=findViewById(R.id.ingredients_tv);
        mOriginTv=findViewById(R.id.origin_tv);
        mDescriptionTv=findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
       }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        //Log.d(TAG, "position : "+position);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        //Log.d(TAG, "++++Message : "+ sandwich.getAlsoKnownAs().size());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String knowAs=" ";
        for (int i=0;i<sandwich.getAlsoKnownAs().size();i++){
            knowAs=knowAs+sandwich.getAlsoKnownAs().get(i) + "\n";
        }
        mAlsoKnowTv.setText(knowAs);
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
        String ingredient=" ";
        for (int i=0;i<sandwich.getIngredients().size();i++){
            ingredient=ingredient+sandwich.getIngredients().get(i) + "\n";
        }
        mIngredientTv.setText(ingredient);
        mDescriptionTv.setText(sandwich.getDescription());
    }

}
