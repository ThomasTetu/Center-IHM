package com.example.thomas.center.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thomas on 18/04/17.
 */
public class Shop implements Parcelable {

    private String name;
    private String imgPath;
    private String previewImgPath;
    private ShopType type;
    private List<Product> productList;
    private boolean event;
    private Date date;
    private String description;
    private boolean registered = false;

    public Shop(String name,String description, String imgPath, String previewImgPath, ShopType type, boolean event){
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.previewImgPath = previewImgPath;
        this.type = type;
        productList = new ArrayList<>();
        this.event = event;
    }

    private Shop(Parcel in) {
        name = in.readString();
        description = in.readString();
        imgPath = in.readString();
        previewImgPath = in.readString();
        productList = in.createTypedArrayList(Product.CREATOR);
        date = (Date) in.readSerializable();
        type = (ShopType) in.readSerializable();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public ShopType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPreviewImgPath() {
        return previewImgPath;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imgPath);
        dest.writeString(previewImgPath);
        dest.writeTypedList(productList);
        dest.writeSerializable(date);
        dest.writeSerializable(type);
    }

    public boolean isEvent() {
        return event;
    }

    public void setDate(String dateString){
        date = new Date(dateString);
    }

    public String getDateString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public Date getDate() {
        return date;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered() {
        this.registered = !registered;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getDescription() {
        return description;
    }
}
