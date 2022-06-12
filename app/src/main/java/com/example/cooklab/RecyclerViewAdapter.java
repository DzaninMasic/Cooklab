package com.example.cooklab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private Context mContext;
    private List<Recipes> mData;
    private List<String> selectedIngredients;

    public RecyclerViewAdapter(Context mContext, List<Recipes> mData, List<String> selectedIngredients){
        this.mContext = mContext;
        this.mData = mData;
        this.selectedIngredients = selectedIngredients;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.cardview_recipe,viewGroup,false);
        return  new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        int counter = 0;
        if (selectedIngredients.containsAll(mData.get(position).getRecipeIngredients())) {
            holder.recipeTitle.setText(mData.get(position).getRecipeName());
            holder.img_recipe_thumbnail.setImageResource(mData.get(position).getThumbnail());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecipeActivity.class);

                    intent.putExtra("RecipeName", mData.get(position).getRecipeName());
                    intent.putExtra("RecipeIngredients", mData.get(position).getRecipeIngredients());
                    intent.putExtra("RecipeMethodTitle", mData.get(position).getRecipeMethodTitle());
                    intent.putExtra("Recipe", mData.get(position).getRecipe());
                    intent.putExtra("Thumbnail", mData.get(position).getThumbnail());

                    mContext.startActivity(intent);
                }
            });
            counter++;
        } else {
            holder.itemView.setVisibility(View.GONE);
        }

        if (position == mData.size() - 1 && counter == 0) {
            Toast.makeText(holder.itemView.getContext(), "No recipes found.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle;
        ImageView img_recipe_thumbnail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_text);
            img_recipe_thumbnail = (ImageView)itemView.findViewById(R.id.recipe_img_id);

        }
    }
}
