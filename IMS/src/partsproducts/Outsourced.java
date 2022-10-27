package partsproducts;

/**
 * public class constructor for Outsourced for use when Outsourced radio button is selected
 */


public class Outsourced extends Parts {

    /**
     * string of company name
     */


    private String companyName;

    /**
     * all In House parameters
     */


    public Outsourced (int id, String name, double  price, int stock, int min, int max, String companyName){
        //sets company name to company name parameter
        this.companyName = companyName;

/**
 * setter for Outsourced attributes
 */


        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    /**
     * getter for company name
     */


    public String getCompanyName() {
        //returns company name class
        return companyName;
    }

    /**
     * setter for company name
     */


    public void setCompanyName(String companyName) {

        //assigns company name private class to company name
        this.companyName = companyName;
    }
}