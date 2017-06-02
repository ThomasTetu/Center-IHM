package com.example.thomas.center.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.center.R;
import com.example.thomas.center.model.Product;
import com.example.thomas.center.util.DownloadImageTask;

import java.util.List;

/**
 * Created by thomas on 01/05/17.
 */
public class GridProductAdapter extends ArrayAdapter<Product> {

    /**
     * Classe Adapter pour afficher une grille de produits.
     * @param context : Le context.
     * @param products : Le listes des produits à afficher
     **/

    public GridProductAdapter(Context context, List<Product> products){
        super(context,0,products);
    }

    /**
     * Méthode permettant de récupérer une vue pour chaque objets products.
     * @param position : index de position dans la GridView
     * @param convertView : la vue de l'objet Product courant
     * @param parent : la vue contenant les cardviews. (inutilisée ici)
     * @return : la convertView de l'objet Product sous forme de CardView.
     */

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Product item = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.product_card,null);
        }
        CardView card = (CardView) convertView.findViewById(R.id.productCard);
        ImageView productImg = (ImageView) card.findViewById(R.id.productImageView);
        TextView productName = (TextView) card.findViewById(R.id.productName);

        productName.setText(item.getName());

        DownloadImageTask imageTask = new DownloadImageTask(productImg,getContext());
        imageTask.execute(item.getImgPath());
        return convertView;
    }
}
