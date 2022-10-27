package partsproducts;

/**
 * public class constructor In House for use when In House radio button is selected
 */


public class InHouse extends Parts {

    /**
     * integer of machine ID
     */


    private int machineID;

    /**
     * all In House parameters
     */


    public InHouse (int id, String name, double  price, int stock, int min, int max, int machineID){

/**
 * sets machine ID to machine ID parameter
 */


        this.machineID = machineID;

/**
 * setters for In House attributes
 */


     setId(id);
     setName(name);
     setPrice(price);
     setStock(stock);
     setMin(min);
     setMax(max);
     setMachineID(machineID);
    }

    /**
     * getter for machine ID
     */


    public int getMachineID() {
        return machineID;
    }

    /**
     * setter for machine ID
     */


    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
