package partsproducts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class constructor
 */


public class Inventory {

    /**
     *  creates observable lists for all parts and all products
     */


    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProduct = FXCollections.observableArrayList();

    /**
     * sets initial parts IDs to 0 so ID auto generator has a starting point
     */


    private static int partsID = 0;

    /**
     * sets initial products id to 0 so ID auto generator has a starting point
     */


    private static int productID = 0;

    /**
     * observable list getters that return the lists of parts and products
     */


    public static ObservableList<Product> getAllProduct()
    {
        return allProduct;
    }
    public static ObservableList<Parts> getAllParts()
    {
        return allParts;
    }

    public static void addPart (Parts newPart){
        if (newPart != null){
            //adds part to all parts list
        allParts.add(newPart);
    }}

    /**
     * remove Parts boolean used to determine if a copy exists and if so, the copy is deleted
     */

    public static boolean removeParts(Parts selectedPart){
        //if all parts list constains selected part
        if (allParts.contains(selectedPart)){
            //removes selected part
            allParts.remove(selectedPart);
            return true;
        }
        //returns false if all parts doesnt contain selected part
        else {
            return false;
        }
    }

    /**
     * getter for new parts ID
     */


    public static int getNewPartsID()
    {
        //returns a new ID in intervals of +1
        return ++partsID;
    }

    /**
     * add product method used to add new products to the allProducts list
     */

    public static void addProduct (Product newProduct) {
        if (newProduct != null) {
            //adds product to all product inventory list
            allProduct.add(newProduct);
        }
    }

    /**
     * remove product boolean used to determine if a copy of a product exists, and if so the copy is removed
     */

    public static boolean removeProduct(Product selectedProduct){
        //if all products list contains selected product
        if (allProduct.contains(selectedProduct)){
            //removes selected product
            allProduct.remove(selectedProduct);
            return true;
        }
        //returns false if all products doesnt contain selected product
        else {
            return false;
        }
    }

    /**
     * getter for new products ID
     */


    public static int getNewProductID()
    {
        //returns new ID in intervals of +1
        return ++productID;
    }
}
