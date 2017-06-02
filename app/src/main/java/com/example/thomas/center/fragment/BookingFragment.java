package com.example.thomas.center.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thomas.center.MainActivity;
import com.example.thomas.center.R;
import com.example.thomas.center.adapter.RecyclerAdapter;
import com.example.thomas.center.model.Shop;
import com.example.thomas.center.util.ShopDb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by thomas on 14/05/17.
 * Classe Fragment pour afficher les objets Shop favoris ou réservé
 */
public class BookingFragment extends Fragment{

    private boolean isFavoriteOrEvent;

    /**
     * Constructeur.
     * @param isFavoriteOrEvent : determine si il s'agit d'un fragment affichant les favoris ou les réservations.
     */
    public BookingFragment(boolean isFavoriteOrEvent){
        this.isFavoriteOrEvent = isFavoriteOrEvent;
    }

    /**
     * Méthode permettant d'instancier la vue contenue dans le fragment.
     * @param inflater : utilisé pour instancier la vue avec le layout (XML) voulu.
     * @return : la vue principal du fragment.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    /***
     * Méthode permettant de lire les données du modeles et de les charger dans la vue.
     * Ici la liste des boutiques favorites ou la liste des évènements réservés.
     * @param view : affichant les objets models.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Shop> shopLists;
        ShopDb shopDb = new ShopDb(getContext());
        try {
            shopDb.createDataBase();
            shopDb.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        if(isFavoriteOrEvent){
            shopLists = shopDb.getFavorites();
        }else {
            shopLists = shopDb.getBookedEvent();
        }
        try {
            shopDb.setProducts(shopLists);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        shopDb.close();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.configButton);
        fab.setVisibility(View.INVISIBLE);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rvShops);

        final RecyclerAdapter adapter = new RecyclerAdapter(shopLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
    }
}