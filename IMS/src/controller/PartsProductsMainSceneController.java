package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import partsproducts.InHouse;
import partsproducts.Inventory;
import partsproducts.Parts;
import partsproducts.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PartsProductsMainSceneController implements Initializable {

    Stage stage;
    Parent scene;

    private static Product productforMod;
    private static  Parts partforMod;

    /**
     * container for all different error messages used in the controller
     */

    private void showAlerts(int alertType) {
//forms two different types of error messages, information and error
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
//allows to switch between each different case of alert
        switch (alertType) {
            //assigns an identifier number (case number), title, and header
            //Error message for failed search
            case 1:
                alert.setTitle("Attention!");
                alert.setHeaderText("Product could not be located");
                //Waits for user input before continuing
                alert.showAndWait();
                break;
                //Error message for failed search
            case 2:
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("Part could not be located");
                alert.showAndWait();
                break;
                //Error message for failure to select product for modification
            case 3:
                alertError.setTitle("ATTENTION!");
                alertError.setHeaderText("Please select a product.");
                alertError.showAndWait();
                break;
            //Error message for failure to select part for modification
            case 4:
                alertError.setTitle("ATTENTION!");
                alertError.setHeaderText("Please select a part.");
                alertError.showAndWait();
                break;
            //Error message for attempting to delete a product with parts still associated
            case 5:
                alertError.setTitle("ATTENTION!");
                alertError.setHeaderText("Part Association Detected!");
                alertError.setContentText("Please remove any associated parts before deleting product.");
                alertError.showAndWait();
                break;
        }
    }
    /**
     * Add Part button event
     */

    @FXML
    void OnActionAddPartsMain(ActionEvent event) throws IOException {
    //Sends user to Add Part form when button is pressed
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     * Add Product button event
     */

    @FXML
    void OnActionAddProductsMain(ActionEvent event) throws IOException {
        //Sends user to Add Products form when pressed
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProducts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Delete Part button event
     */

    @FXML
    void OnActionDeletePartsMain(ActionEvent event) {
        //selects part from  table
        Parts selectedPart = PartsTable.getSelectionModel().getSelectedItem();
        // if no part is selected, gives error message
        if (selectedPart == null) {
            showAlerts(4);
        } else
        {
            //pops up confirmation box to insure deletion is intended
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Alert");
          alert.setContentText("Deleting Selected Part! Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                //actual deletion statement
                Inventory.removeParts(selectedPart);
            }
        }

    }
    /**
     * Exit button event
     */

    @FXML
    void exitProgram(ActionEvent event){
        //Shows confirmation message before running system.exit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Exiting the program! Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        //If OK button is pressed then runs System.exit
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**
     * Delete button action event
     */
    @FXML
    void OnActionDeleteProductsMain(ActionEvent event) {
        //selects part from  table
        Product selectedProduct = ProductsTable.getSelectionModel().getSelectedItem();

        // if no part is selected, gives error message
        if (selectedProduct == null) {
            showAlerts(4);
        } else {
           // ObservableList<Parts> associatedPart = selectedProduct.getAllAssociatedParts();

            } {
                //pops up confirmation box to insure deletion is intended
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Deleting Selected Product! Are you sure?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    {if (selectedProduct.getAllAssociatedParts().isEmpty())

                        //actual deletion statement
                        Inventory.removeProduct(selectedProduct);
                    else {showAlerts(5);

                    }
                }
            }
        }
    }
    /**
     * getter for sending selected product to modify product form
     */

public static Product getProductforMod(){
        return productforMod;
}
    /**
     * getter for sending selected part to modify part form
     */

    public static Parts getPartforMod()
    {
        return partforMod;
    }
    /**
     * action event for modify parts button
     */

    @FXML
    void OnActionModifyPartsMain(ActionEvent event) throws IOException {
        //gets selected part for modification
        partforMod = PartsTable.getSelectionModel().getSelectedItem();
        //if no part is selecetd then gives error
            if (partforMod == null) {
                showAlerts(4);
            }
            //else sends user to modify parts form
            else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyPartsForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            }

    }
    /**
     * modify products button action event
     */

    @FXML
    void OnActionModifyProductsMain(ActionEvent event) throws IOException {
    //gets selected product for modification
        productforMod = ProductsTable.getSelectionModel().getSelectedItem();
        //if no part is selected then gives error
        if (productforMod == null) {
            showAlerts(3);
            //else sends user to modify products form
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProducts.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     *  Table view columns and their respective data types
     *   fx:id for part cost column on part table
     */

    @FXML
    private TableColumn<Parts, Double> PartCostColumn;
    /**
     *  fx:id for part ID column on part table
     */

    @FXML
    private TableColumn<Parts, Integer> PartIDColumn;
    /**
     * fx:id for part inventory column on part table
     */


    @FXML
    private TableColumn<Parts, Integer> PartILC;
    /**
     * fx:id for part name column on part table
     */


    @FXML
    private TableColumn<Parts, String> PartNameColumn;
    /**
     * text field for parts search
     */

    @FXML
    private TextField PartsSearch;
    /**
     * table view for parts table
     */

    @FXML
    private TableView<Parts> PartsTable;


    @FXML
    private AnchorPane PartsTable1;
    /**
     * More Table columns and their respective data types
     *     fx:id for product cost column on product table
     */


    @FXML
    private TableColumn<Product, Double> ProductCostColumn;
    /**
     * fx:id for product ID column on product table
     */

    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;
    /**
     * fx:id for product inventory column on product table
     */

    @FXML
    private TableColumn<Product, Integer> ProductILC;
    /**
     * fx:id for product name column on product table
     */

    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    /**
     * fx id for product search text field
     */

    @FXML
    private TextField ProductSearch;
    /**
     * fx id for products table tableview
     */

    @FXML
    private TableView<Product> ProductsTable;
    /**
     *
     * Search function action event
     */

    @FXML
    void getResultsParts(ActionEvent event) {
        String p = PartsSearch.getText();
// Searching by part name returns a list
        ObservableList<Parts> parts = searchbyPartsName(p);
// Searches for part names first, then id number
        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(p);
                //getter for parts id
                Parts pt = getPartsID(id);
                //if getter values are not null then adds them to results
                if (pt != null)
                    parts.add(pt);
            } catch (NumberFormatException nfe) {

            }
        }
        //sets parts table with results
        PartsTable.setItems(parts);
//Error box if no parts are found with listed values
        if (parts.size() == 0) {
            showAlerts(2);
        }
    }
    /**
     * Name search that search funtion utilizes to look for matching name string
     */

  private ObservableList<Parts> searchbyPartsName (String partialName){
        //creates observale lists for named parts and a getter from inventory model
      ObservableList<Parts> namedParts = FXCollections.observableArrayList();
      ObservableList<Parts> allParts = Inventory.getAllParts();
// simple For loop that differs from ID search For Loop
      //for loop that looks for partial matches
      for(Parts pt : allParts){
          if(pt.getName().contains(partialName)){
              namedParts.add(pt);
          }

      }
      //returns any matches including partial matches
      return namedParts;
  }
    /**
     * ID search that search function utilizes to look for matching ID integer
     */

  private Parts getPartsID (int id){
//creates observable list from get all parts getter from inventory model
        ObservableList<Parts> allParts = Inventory.getAllParts();
// simple For loop that differs from name search for loop
      //searches in increments of 1 for matching ID integers
        for(int i = 0; i < allParts.size(); i++) {

        Parts pt = allParts.get(i);
//returns matches
        if(pt.getId() == id){
            return pt;
        }
        }
        return null;
  }
    /**
     * ID search that search function utilizes to look for matching ID integer
     */

    private Product getProductID (int id){
//creates observable list from get all parts getter from inventory model
        ObservableList<Product> allProduct = Inventory.getAllProduct();
// simple For loop that differs from name search for loop
        //searches in increments of 1 for matching ID integers
        for(int i = 0; i < allProduct.size(); i++) {

            Product pr = allProduct.get(i);
//returns matches
            if(pr.getId() == id){
                return pr;
            }
        }
        return null;
    }
    /**
     * Name search that search funtion utilizes to look for matching name string
     */

    private ObservableList<Product> searchbyProductName (String partialName){
        //creates observale lists for named parts and a getter from inventory model
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();
        ObservableList<Product> allProduct = Inventory.getAllProduct();
// simple For loop that differs from ID search
        // For Loop that looks for partial matches
        for(Product pr : allProduct){
            if(pr.getName().contains(partialName)){
                namedProduct.add(pr);
            }
        }
        //returns any matches including partial matches
        return namedProduct;
    }
    /**
     * search method getter
     */
    @FXML
    void getResultsProduct(ActionEvent event) {
        String p = ProductSearch.getText();
// Searching by part name returns a list
        ObservableList<Product> products = searchbyProductName(p);
// Searches for part names first, then id number
        if (products.size() == 0) {
            //tries search
            try {
                int id = Integer.parseInt(p);
                Product pr = getProductID(id);
                if (pr != null)
                    products.add(pr);
            } catch (NumberFormatException nfe) {

            }
        }
        //sends results to table
        ProductsTable.setItems(products);
//Error box if no parts are found with listed values
        if (products.size() == 0) {
            showAlerts(1);
        }
    }

    /**
     * Initializes controller class
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //getter for all parts - sends to all parts table
        PartsTable.setItems(Inventory.getAllParts());
        //assigns table columns to respective values
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartILC.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //getter for all products - sends all products to products table
        ProductsTable.setItems(Inventory.getAllProduct());
        //assigns table columns to respective values
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductILC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}



