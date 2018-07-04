package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    private final String TAG=getClass().getName();
    private static final String DEFAULT_PLACE_OF_ORIGIN="Cameroon";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich=new Sandwich();
        try {
            JSONObject jsonRoot=new JSONObject(json);

            //Extracting of MainName
            JSONObject name=jsonRoot.getJSONObject("name");
            String mainName=name.getString("mainName");

            //Extracting "AlsoKnownAs"

            //List<String> knownAsArray=(List<String>) name.getJSONArray("alsoKnownAs");
            JSONArray knownAs= name.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnowAs=new ArrayList<>();
            for (int i=0;i<knownAs.length();i++){
                alsoKnowAs.add(knownAs.getString(i));
            }

            //Extracting Place Of Origin
            String placeOfOrigin = jsonRoot.optString("placeOfOrigin", DEFAULT_PLACE_OF_ORIGIN);

            //Extracting Image Url
            String imageUrl=jsonRoot.getString("image");

            //Extracting Description
            String description = jsonRoot.getString("description");

            //Extracting Ingredients

            //List<String> knownAsArray=(List<String>) name.getJSONArray("alsoKnownAs");
            JSONArray ingredArray= jsonRoot.getJSONArray("ingredients");
            ArrayList<String> ingredients=new ArrayList<>();
            for (int i=0;i<ingredArray.length();i++){
                ingredients.add(ingredArray.getString(i));
            }

            sandwich =new Sandwich(mainName,alsoKnowAs,placeOfOrigin,description,imageUrl,ingredients);

        }catch (JSONException ex){
            //Display an Error message on the screen;
            Log.e("JsonUtils", "Error parsing Json String: \t"+ex);
        }
        return sandwich;
    }
}


