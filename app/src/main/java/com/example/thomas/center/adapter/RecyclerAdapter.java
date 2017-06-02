package com.example.thomas.center.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thomas.center.EventActivity;
import com.example.thomas.center.ProductsActivity;
import com.example.thomas.center.R;
import com.example.thomas.center.model.Product;
import com.example.thomas.center.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by thomas on 29/04/17.
 * Classe personalisé d'adapter de RecyclerView
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private List<Shop> mShops = new ArrayList<>();
    private List<Shop> filterList = null;

    /**
     * Constructeur de l'adapter
     * @param mShops : la liste des objets Shop à afficher.
     */

    public RecyclerAdapter(List<Shop> mShops) {
        this.mShops =mShops;
        filterList = new ArrayList<>();
        filterList.addAll(mShops);
    }

    /**
     * Méthode permettant de créer une vue pour un objet Shop.
     * @param parent : la vue parent qui contiendra toutes les vues des objets Shop
     * @param viewType : le type de vue retourné
     * @return : La vue personnalisée d'un objet Shop.
     */
    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {

        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View shopsView = inflater.inflate(R.layout.row_shop_layout, parent, false);

        // Return a new holder instance
        return new ShopViewHolder(shopsView);
    }

    /**
     * Permet d'afficher les données d'un objet Shop à la position spécifiée.
     * @param holder : la vue personnalisée à remplir
     * @param position : La position dans la recyclerView.
     */
    @Override
    public void onBindViewHolder(final ShopViewHolder holder, final int position) {
        final Shop shop = mShops.get(position);
        holder.setImage(shop);
        holder.setEvent(shop);
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shop shop = mShops.get(position);
                Context context = holder.getContext();
                Intent intent;
                if(shop.isEvent()){
                    intent = new Intent(context, EventActivity.class);
                }else{
                    intent = new Intent(context, ProductsActivity.class);
                }
                intent.putExtra("shop", shop);
                intent.putExtra("title", shop.getName());
                context.startActivity(intent);
            }
        });
    }

    /**
     * Méthode retournant le nombre d'objets à afficher.
     * @return : La taille de notre liste de Shop.
     */
    @Override
    public int getItemCount() {
        return mShops.size();
    }

    /**
     * Méthode permettant de filtrer la les objets Shop à afficher en fonction du types des produits
     * qu'ils contiennent.
     * @param charText : le type de produits sous forme de String.
     */
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mShops.clear();
        if (charText.length() == 0 || charText.equals("tout voir")) {
            mShops.addAll(filterList);
        }
        else
        {
            for (Shop s : filterList){
                for(Product p : s.getProductList()){
                    if(p.getType().toLowerCase().equals(charText)){
                        mShops.add(s);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}

