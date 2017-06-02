package com.example.thomas.center.fragment;

/**
 * Classe Fragements permettant d'afficher la liste des boutiques/évènements selon un type choisi.
 * Created by thomas on 18/04/17.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.thomas.center.R;
import com.example.thomas.center.adapter.RecyclerAdapter;
import com.example.thomas.center.model.Shop;
import com.example.thomas.center.model.ShopType;
import com.example.thomas.center.util.ShopDb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopTypeFragment extends Fragment {

    private ShopType type;

    /**
     * Constucteur permettant de déterminé le type de boutiques/évènements à afficher.
     * @param type : Le type.
     */
    public ShopTypeFragment(ShopType type) {
        this.type = type;
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
     * Ici la listes des boutiques/évènements du type choisi, ainsi que de déterminé l'action du bouton de tri.
     * @param view : affichant les objets models.
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Shop> shopLists = new ArrayList<>();
        ShopDb db = new ShopDb(getActivity());
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        try {
            shopLists = db.getAllShops();
            db.setProducts(shopLists);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        db.close();
        shopLists = checkType(shopLists);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rvShops);

        final RecyclerAdapter adapter = new RecyclerAdapter(shopLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.configButton);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.filter_dialog);
                for(String s : type.getStrings()){
                    arrayAdapter.add(s);
                }
                arrayAdapter.add("Tout voir");
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        adapter.filter(strName);
                    }
                });
                builderSingle.show();
            }

        });

    }

    /**
     * Méthode permettant de vérifier si les boutiques/évènements sont du bon type
     * et de les ajoutant dans la liste à afficher.
     * @param shopList : la liste des toutes les boutiques/évènements
     * @return : La list des boutiques/évènements à afficher.
     */
    private List<Shop> checkType(List<Shop> shopList){
        List<Shop> filteredShops = new ArrayList<>();
        for(Shop s : shopList){
            if(s.getType().equals(type)){
                filteredShops.add(s);
            }
        }
        return filteredShops;
    }
}
