package com.example.thomas.center.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe model représentant un produit
 * Created by thomas on 21/04/17.
 */
public class Product implements Parcelable{

    private String name;
    private String imgPath;
    private String typeShop;
    private String typeProduct;

    /**
     * Constructeur.
     * @param name : le nom
     * @param imgPath : le lien de l'image
     * @param typeShop : le type de magasin auquel il appartient
     */
    public Product(String name, String imgPath,String typeShop){
        this.name = name;
        this.imgPath = imgPath;
        this.typeShop = typeShop;
    }

    /**
     * Constructeur parcelable
     * @param in : les données "parcelées"
     */
    private Product(Parcel in) {
        name = in.readString();
        imgPath = in.readString();
        typeShop = in.readString();
        typeProduct = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getTypeShop() {
        return typeShop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgPath);
        dest.writeString(typeShop);
        dest.writeString(typeProduct);
    }


}
