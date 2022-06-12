package com.example.cooklab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class DropDownActivity extends AppCompatActivity {

    TextView tvIngredients;
    boolean[] selectedIngredients;
    ArrayList<Integer> IngredientList = new ArrayList<>();
    String[] ingredientArray = {"Flour 1kg","Flour 5kg","Oil 1l","Oil 2l","10 eggs","25 eggs"};
    ArrayList<String> allSelectedIngredients = new ArrayList<String>();
    ArrayList<Boolean> selectedPositions = new ArrayList<>();
    public Button button;
    private Button grocery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown);


        tvIngredients = findViewById(R.id.tv_ingredients);
        grocery=findViewById(R.id.grocerystore);
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.google.com/search?q=grocery+stores+near+me");
            }
        });

        selectedIngredients= new boolean[ingredientArray.length];

        tvIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(DropDownActivity.this);

                builder.setTitle("Select Ingredients");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(ingredientArray, selectedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked)
                        {
                            IngredientList.add(position);
                            Collections.sort(IngredientList);
                            // Memorized selected ingredients.
                            allSelectedIngredients.add(ingredientArray[position]);
                        }else {
                            IngredientList.remove(position);
                            if (allSelectedIngredients.contains(ingredientArray[position])) {
                                allSelectedIngredients.remove(ingredientArray[position]);
                            }
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder= new StringBuilder();

                        for(int j=0;j<IngredientList.size();j++)
                        {
                            stringBuilder.append(ingredientArray[IngredientList.get(j)]);

                            if(j != IngredientList.size()-1)
                            {
                                stringBuilder.append(", ");
                            }
                        }
                        Log.i("TAG", "onClick: " + tvIngredients);
                        tvIngredients.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j=0;j<selectedIngredients.length;j++)
                        {
                            selectedIngredients[j]=false;
                            IngredientList.clear();
                            tvIngredients.setText("");
                        }
                    }
                });

                builder.show();
            }
        });

        button=(Button) findViewById(R.id.signupbtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DropDownActivity.this,RecipeList.class);
                intent.putExtra("selectedIngredients", allSelectedIngredients);
                startActivity(intent);
            }
        });
    }
    public void gotoUrl(String url){
        Uri uri= Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}