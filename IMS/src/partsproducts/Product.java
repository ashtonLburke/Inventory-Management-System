package partsproducts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    /**
     * Creates observable list for associated parts
     */


    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    /**
     * gives attributes their respective data types
     */


    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructs
     *  All parameters for Product class
     */


    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;


    }

    /**
     *  ID getter
     *   @return the id
     */



    public int getId() {
        return id;
    }

    /**
     *
     *  ID setter
     */



    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name getter
     *  returns name
     */



    public String getName() {
        return name;
    }

    /**
     * Name setter
     * sets name to parameter
     */



    public void setName(String name) {
        this.name = name;
    }

    /**
     * price getter
     *  returns price
     */



    public double getPrice() {
        return price;
    }

    /**
     * price setter
     *  sets price to price parameter
     */



    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *  stock getter
     *   returns stock
     */


    public int getStock() {
        return stock;
    }

    /**
     *   stock setter
     *  sets stock to stock parameter
     */



    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * min getter
     *  returns min
     */


    public int getMin() {
        return min;
    }

    /**
     *  minimum setter
     *  sets minimum to minimum parameter
     */



    public void setMin(int min) {
        this.min = min;
    }

    /**
     * max getter
     *  returns max
     */


    public int getMax() {
        return max;
    }

    /**
     *  max setter
     *  sets max to max parameter
     */


    public void setMax(int max) {
        this.max = max;
    }


    /**
     * addAssoicatedPart method that adds the associated parts to the associated parts list
     */

    public void addAssociatedPart(Parts parts) {
       //adds associated parts to associated parts list
        {
            associatedParts.add(parts);
        }
    }

    /**
     * getter for associated parts
     */


    public ObservableList<Parts> getAllAssociatedParts() {
        //returns associated parts list
                return associatedParts;
            }

        }


