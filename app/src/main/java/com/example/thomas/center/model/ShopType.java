package com.example.thomas.center.model;

/**
 * Created by thomas on 18/04/17.
 */
public enum ShopType {

    MODE("mode", new String[]{"Homme", "Femme", "Enfant"}),
    DECO("deco", new String[]{"Jardin", "Salon", "Chambre","Salle de bains"}),
    SPORT("sport", new String[]{"Indoor", "Outdoor", "Winter","Summer"});

    private String typeString;
    private String[] strings;

    ShopType(String type, String[] strings) {
        typeString = type;
        this.strings=strings;
    }

    public String getTypeString() {
        return typeString;
    }

    public String[] getStrings() {
        return strings;
    }
}
