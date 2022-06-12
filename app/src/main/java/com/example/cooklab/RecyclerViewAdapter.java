package com.example.cooklab;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {
    private String CHANNEL_ID;
    private Context mContext;
    private List<Recipes> mData;
    private List<String> selectedIngredients;
    int counter = 0;

    public RecyclerViewAdapter(Context mContext, List<Recipes> mData, List<String> selectedIngredients, String CHANNEL_ID){
        this.CHANNEL_ID=CHANNEL_ID;
        this.mContext = mContext;
        this.mData = mData;
        this.selectedIngredients = selectedIngredients;
        counter = 0;
    }

    public void clearItems() {
        this.selectedIngredients.clear();
        this.notifyDataSetChanged();
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

        Intent intent = new Intent(mContext.getApplicationContext(), DropDownActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (position == mData.size() - 1 && counter == 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext.getApplicationContext(),CHANNEL_ID)
                    .setSmallIcon(R.drawable.cooklab_1)
                    .setContentTitle("CookLab Notification")
                    .setContentText("No reciepes have been found for the ingredients you picked. Top to go back!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat=NotificationManagerCompat.from(mContext.getApplicationContext());
            managerCompat.notify(1,builder.build());
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
