package com.example.thomas.center.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thomas.center.R;
import com.example.thomas.center.model.Shop;
import com.example.thomas.center.util.CalendarManager;
import com.example.thomas.center.util.DownloadImageTask;
import com.example.thomas.center.util.ShopDb;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by thomas on 29/04/17.
 * Classe personnalisée de la vue d'un objetShop
 */
class ShopViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private ImageView imageView;
    private LinearLayout textBanner;
    private TextView textEvent;
    private FloatingActionButton button;
    private Context context;
    private CalendarManager calendarManager;

    /**
     * Constructeur de la classe, récupérant les différents containers et éléments de la vue passée en paramètre.
     * @param itemView : la vue personnalisée à remplir.
     */
    ShopViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        this.imageView = (ImageView) itemView.findViewById(R.id.coverImageView);
        this.textBanner = (LinearLayout) itemView.findViewById(R.id.textBanner);
        this.textEvent = (TextView) itemView.findViewById(R.id.textEvent);
        this.button = (FloatingActionButton) itemView.findViewById(R.id.addNotifButton);
        calendarManager = new CalendarManager(context);
    }

    /**
     * @return : La CardView de l'objet Shop
     */
    CardView getCardView() {
        return cardView;
    }

    /**
     * @return : le context.
     */
    Context getContext() {
        return context;
    }

    /**
     * Méthode permettant d'aller chercher l'image de l'objet Shop en tâche de fond.
     * @param shop : l'objet shop à visualiser.
     */
    void setImage(Shop shop) {
        DownloadImageTask imageTask = new DownloadImageTask(imageView, context);
        imageTask.execute(shop.getPreviewImgPath());
    }

    /**
     * Méthode permettant de configurer le bouton de favoris/ajout d'évènement.
     * @param shop : l'objet Shop correspondant.
     */
    void setEvent(final Shop shop) {
        if (shop.isEvent()) {
            textBanner.setVisibility(View.VISIBLE);
            textEvent.setText("Evenement : " + shop.getName() + " " + shop.getDateString());
            if(shop.isRegistered()){
                setButton(R.drawable.ic_clear_black_12dp);
            }
            else{
                setButton(R.drawable.ic_add_black_12dp);
            }
        }else {
            if(shop.isRegistered()){
                setButton(R.drawable.ic_star_black_12dp);
            }else{
                setButton(R.drawable.ic_star_border_black_12dp);
            }
        }

        //Listener sur le bouton de réservation/favoris.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDb shopDb = new ShopDb(context);
                try {
                    shopDb.createDataBase();
                    shopDb.openDataBase();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                if(shop.isEvent()){
                    setBookedEvent(shopDb,shop);
                }else{
                    setFavorites(shopDb,shop);
                }
                shopDb.close();
                shop.setRegistered();
            }
        });
    }
    /**
     * Méthode permetant de modifier l'image resource du bouton de favoris/ajout d'évènement.
     * @param res : l'image ressource.
     */
    private void setButton(int res) {
        button.setImageResource(res);
    }

    /**
     * Méthode permettant de gérer l'interaction du bouton d'un objet Shop.
     * (Ajout ou suppression d'un Shop dans les favoris)
     * @param shopDb : Notre base de données.
     * @param shop : L'objet correspondant.
     */
    private void setFavorites(ShopDb shopDb,Shop shop){
        if(shop.isRegistered()){
            shopDb.unSetBooked(shop.getName());
            setButton(R.drawable.ic_star_border_black_12dp);
        }else{
            shopDb.setBooked(shop.getName());
            setButton(R.drawable.ic_star_black_12dp);
        }
    }

    /**
     * Méthode permettant de gérer l'interaction du bouton d'un objet Shop correspondant à un évènement.
     * (L'ajout ou la suppression d'un évènement dans le calendrier.
     * @param shopDb : Notre base de données
     * @param shop : l'objet correspondant.
     */
    private void setBookedEvent(ShopDb shopDb , Shop shop){
        calendarManager.interact(shop);
        if(shop.isRegistered()){
            shopDb.unSetBooked(shop.getName());
            setButton(R.drawable.ic_add_black_12dp);
        }else{
            shopDb.setBooked(shop.getName());
            setButton(R.drawable.ic_clear_black_12dp);
        }
    }
}

