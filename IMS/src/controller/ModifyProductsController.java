package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import partsproducts.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/**
 * RUNTIME ERROR: When saving a modified product, a weird error would occur where itd pop alert case 5 and duplicate the selected associated part in the associated parts table
 * I adjusted several things but I think the main cause of the problem was that my addAssociatedPart method was listed as Static. After removing the static keyword, the products were saving as intended
 */

public class ModifyProductsController implements Initializable {
//creates observables list for associated parts
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    //class identifiers
    Product selectedProduct;
    Stage stage;
    Parent scene;

    /**
     *
     * Alert container so I didnt have to keep writing out the error boxes
     */



    private void showAlerts(int alertType) {
//Sets alert type
        Alert alert = new Alert(Alert.AlertType.ERROR);
//allows ability to switch between the different alert cases
        switch (alertType) {
            //identifies and creates the first alert case
            case 1:
                //Error title
                alert.setTitle("Warning!");
                //Error header
                alert.setHeaderText("Failed to add new product");
                //Error content
                alert.setContentText("Form contains invalid or null values");
                //ensures that the method waits for user input before continuing
                alert.showAndWait();
                break;
            //identifies and creates the second alert case
            case 2:
                alert.setTitle("Warning!");
                alert.setHeaderText("Name field has been left empty");
                alert.setContentText("Name field needs values. Cannot be null.");
                alert.showAndWait();
                break;
            //identifies and creates the third alert case
            case 3:
                alert.setTitle("Warning!");
                alert.setHeaderText("Incorrect value for Inventory");
                alert.setContentText("Inventory value must be between the range set by Min and Max.");
                alert.showAndWait();
                break;
            //identifies and creates the fourth alert case
            case 4:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min cannot be zero and must be set lesser than the Max value.");
                alert.showAndWait();
                break;
            //identifies and creates the fifth alert case
            case 5:
                alert.setTitle("Warning!");
                alert.setHeaderText("Associated Parts Needed");
                alert.setContentText("Please add associated parts to the product.");
                alert.showAndWait();
                break;
            //Error message for failed search
            case 6:
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("Part could not be located");
                alert.showAndWait();
                break;
        }
    }



    /**
     * Add associated part action event
     *
     */

    public void OnActionModifyAssociatedPart(ActionEvent actionEvent) {
        //identifies part to move to associated parts table from the general parts table
        Parts selectedPart = PartListTable.getSelectionModel().getSelectedItem();
//ensures a part is highlighted. will give an error if no part is highlighted
        if (selectedPart == null) {
            showAlerts(5);
        } else {
            //adds the highlighted part to the associated parts table for the respective product
            associatedParts.add(selectedPart);
            AssociatedPartTable.setItems(associatedParts);
        }
    }
    /**
     * Remove associated parts button event
     *
     */

    public void OnActionRemoveModifyAssociatedPart(ActionEvent actionEvent) {
        Parts selectedPart = AssociatedPartTable.getSelectionModel().getSelectedItem();
        //Confirmation message to ensure user wants to quit back to main form
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Removing selected part. Are you sure?");
//Waits for user input before continuing
        Optional<ButtonType> result = alert.showAndWait();
//if statement- "if OK is pressed then load Main Form"
        if (result.isPresent() && result.get() == ButtonType.OK)
            //if nothing is selected then gives error message
        {
            if (selectedPart == null) {
                showAlerts(5);
            } else {
                associatedParts.remove(selectedPart);
                // AddAssociatedPartTable.setItems(associatedParts);
            }
        }
    }
    /**
     * save button action event
     *
     */


    public void OnActionSaveModifyAssociatedPart(ActionEvent event) throws IOException {
       //tries saving modified product
        try {
            boolean successfulAddition = false;
            //gets auto generated id
            int id = selectedProduct.getId();
            //retrieves inputted text and pairs it with its respective value
            String name = NameProductsField.getText();
            int stock = Integer.parseInt(InvProductsField.getText());
            double price = Double.parseDouble(PriceProductsField.getText());
            int max = Integer.parseInt(MaxProductsField.getText());
            int min = Integer.parseInt(MinProductsField.getText());

            // if maximum input is less than minimum input then show error message
            if (max < min) {
                //alert declared up top
                showAlerts(4);
                //else if max is less than stock and stock is less than minimum then show error message
            } else if (max < stock || stock < min) {
                showAlerts(3);
            }
            if (name.isEmpty()) {
            showAlerts(2);}
            else {
                try {
                    //pairs classes to new variable
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    //Includes the associated parts data with the assigned product
                    for (Parts parts : associatedParts) {
                        //adds
                        newProduct.addAssociatedPart(parts);
                    }
                    //adds the modified product
                    Inventory.addProduct(newProduct);
                    //deletes the pre-modified product so there aren't duplicates
                    Inventory.removeProduct(selectedProduct);
                    successfulAddition = true;
                } catch (Exception nfe) {
                    showAlerts(5);
                }

                //if product addition is successful then takes user back to main form
                if (successfulAddition) {
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            }}
            //if error is caught then shows error messaage
         catch(NumberFormatException nfe){
                showAlerts(1);
            }
        }
    /**
     *
     *  Search function action event
     */


    @FXML
    void searchModifyParts (ActionEvent event) {
        String p = PartSearchProducts.getText();
// Searching by part name returns a list
        ObservableList<Parts> parts = searchbyPartsName(p);
// Searches for part names first, then id number
        if (parts.size() == 0) {
            try {
                //Searches by ID number
                int id = Integer.parseInt(p);
                Parts pt = getPartsID(id);
                if (pt != null)
                    parts.add(pt);
            } catch (NumberFormatException nfe) {

            }
        }
        //Assigns search results to table
        PartListTable.setItems(parts);
//Error box if no parts are found with listed values
        if (parts.size() == 0) {
            showAlerts(6);
        }
    }
    /**
     * Name search that search funtion utilizes to look for matching name string
     *
     */


    private ObservableList<Parts> searchbyPartsName (String partialName){
    //creates observable lists for named parts and all parts (from inventory model)
        ObservableList<Parts> namedParts = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = Inventory.getAllParts();
// For loop that retrieves all matching string letters and assigns them a variable name
        for(Parts pt : allParts){
            if(pt.getName().contains(partialName)){
                namedParts.add(pt);
            }
        }
        //returns matches
        return namedParts;
    }
    /**
     *
     * ID search that search function utilizes to look for matching ID integer
     */


    private Parts getPartsID (int id){
//creates observable list for all parts from inventory model (gets parts)
        ObservableList<Parts> allParts = Inventory.getAllParts();
// simple For loop that differs from name search for loop
        //searches ID number in increments of 1
        for(int i = 0; i < allParts.size(); i++) {
        //gets results
            Parts pt = allParts.get(i);
        //returns results
            if(pt.getId() == id){
                return pt;
            }
        }
        //returns null if no matches are found
        return null;
    }
    /**
     *
     * Cancel buttons action event
     */


    public void OnActionCancelModifyAssociatedPart(ActionEvent event) throws IOException {
        //Confirmation message to ensure user wants to quit back to main form
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You will lose unsaved values. Do you want to continue?");
//Waits for user input before continuing
        Optional<ButtonType> result = alert.showAndWait();
//if statement- "if OK is pressed then load Main Form"
        if(result.isPresent() && result.get() == ButtonType.OK)
        //FXML stage/scene loader
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     *  fx:id for cost column on above table
     *
     */


    @FXML
    private TableColumn<Parts, Double> addCostColumn;
    /**
     * fx:id for inventory column on above table
     */


    @FXML
    private TableColumn<Parts, Integer> addInventoryColumn;
    /**
     *
     * fx:id for ID column on above table
     */

    @FXML
    private TableColumn<Parts, Integer> addPartIDColumn;
    /**
     * fx:id for Name column on above table
     *
     */


    @FXML
    private TableColumn<Parts, String> addPartNameColumn;
    /**
     * fx:id for cost column on above table
     *
     */


    @FXML
    private TableColumn<Parts, Double> assocCostColumn;
    /**
     * fx:id for associated part inventory column on above table
     *
     */


    @FXML
    private TableColumn<Parts, Integer> assocInventoryColumn;
    /**
     * fx:id for associated part id column on above table
     *
     */


    @FXML
    private TableColumn<Parts, Integer> assocPartIDColumn;
    /**
     * fx:id for associated part name column on above table
     *
     */


    @FXML
    private TableColumn<Parts, String> assocPartNameColumn;
    /**
     *
     * table view for associated parts table
     */


    @FXML
    private TableView<Parts> AssociatedPartTable;
    /**
     *
     * fx id for scroll bar
     */


    @FXML
    private ScrollBar AssociatedPartTableSlider;
    /**
     * fx id for cancel button
     *
     */


    @FXML
    private Button CancelModifyProducts;
    /**
     *
     * fx id for text field for ID (auto generated)
     */


    @FXML
    private TextField IDProductsField;
    /**
     * fx id for text field for inventory
     *
     */


    @FXML
    private TextField InvProductsField;
    /**
     *
     * fx id for text field for maximum value
     */


    @FXML
    private TextField MaxProductsField;
    /**
     * fx id for text field for minimum value
     *
     */


    @FXML
    private TextField MinProductsField;
    /**
     * fx id for text field for name
     *
     */


    @FXML
    private TextField NameProductsField;
    /**
     *
     * table view for part listing table
     */


    @FXML
    private TableView<Parts> PartListTable;
    /**
     *
     * scroll bar fx id
     */


    @FXML
    private ScrollBar PartListTableSlider;
    /**
     * fx id for search bar
     *
     */


    @FXML
    private TextField PartSearchProducts;
    /**
     *
     * fx id for price text field
     */


    @FXML
    private TextField PriceProductsField;

    /**
     *
     * initializer
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializes getter for selectedproduct to modify
        selectedProduct = PartsProductsMainSceneController.getProductforMod();
        //getter for associated parts
        associatedParts = selectedProduct.getAllAssociatedParts();



        //retrieves product values for all text fields
        IDProductsField.setText(String.valueOf(selectedProduct.getId()));
        NameProductsField.setText(selectedProduct.getName());
        InvProductsField.setText(String.valueOf(selectedProduct.getStock()));
        PriceProductsField.setText(String.valueOf(selectedProduct.getPrice()));
        MaxProductsField.setText(String.valueOf(selectedProduct.getMax()));
        MinProductsField.setText(String.valueOf(selectedProduct.getMin()));
        //sends associated parts to associated parts table
        AssociatedPartTable.setItems(associatedParts);
        //assigns values to associated part table columns
        assocPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //sends part values to parts table
        PartListTable.setItems(Inventory.getAllParts());
        //assigns values to parts table columns
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));



    }


}

