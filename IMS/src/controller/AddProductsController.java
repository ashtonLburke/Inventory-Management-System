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

public class AddProductsController implements Initializable {

    Stage stage;
    Parent scene;

    /**
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
     * Forms a list to place the associated parts in
     */

private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    /**
     * The add associated part button action
     */

    @FXML
    void OnActionAddAssociatedPart(ActionEvent event) {
//identifies part to move to associated parts table from the general parts table
        Parts selectedPart = AddPartListTable.getSelectionModel().getSelectedItem();
//ensures a part is highlighted. will give an error if no part is highlighted
        if (selectedPart == null) {
            showAlerts(5);
        } else {
            //adds the highlighted part to the associated parts table for the respective product
            associatedParts.add(selectedPart);
            AddAssociatedPartTable.setItems(associatedParts);
        }

    }

    /**
     * Cancel button that returns to the main form
     */

    @FXML
    void OnActionCancelAssociatedPart(ActionEvent event) throws IOException {
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
     * Remove associated part action event
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {
        Parts selectedPart = AddAssociatedPartTable.getSelectionModel().getSelectedItem();
        //Confirmation message to ensure user wants to quit back to main form
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Removing selected part. Are you sure?");
//Waits for user input before continuing
        Optional<ButtonType> result = alert.showAndWait();
//if statement- "if OK is pressed then load Main Form"
        if (result.isPresent() && result.get() == ButtonType.OK)

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
     * Add Product to Main Form product table action event
     */

    @FXML
    void OnActionAddProduct (ActionEvent event) throws IOException {
    //tests code for errors
        try {
            boolean successfulAddition = false;
            int id = 0;
            //retrieves inputted text and pairs it with its respective value
            String name = NameProductsField.getText();
            int stock = Integer.parseInt(InvProductsField.getText());
            double price = Double.parseDouble(PriceProductsField.getText());
            int max = Integer.parseInt(MaxProductsField.getText());
            int min = Integer.parseInt(MinProductsField.getText());

                // if maximum input is less than minimum input then show error message
                if (max < min) {
                    showAlerts(4);
                    //else if max is less than stock and stock is less than minimum then show error message
                } else if (max < stock || stock < min) {
                    showAlerts(3);
                }if (name.isEmpty()){
                    showAlerts(2);
            }
                else {
                    try {
                    //pairs classes to new variable
                        Product newProduct = new Product(id, name, price, stock, min, max);
                        //Includes the associated parts data with the assigned product
                        for (Parts parts : associatedParts){
                            newProduct.addAssociatedPart(parts);
                        }
                        newProduct.setId(Inventory.getNewProductID());
                        Inventory.addProduct(newProduct);
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

                }
                //if error is caught then shows error messaage
            } catch (NumberFormatException nfe) {
                showAlerts(1);
            }
        }

    /**
     * Java FXML form FX IDs
     */
    /**
     * fx:id for cost column on above table *
     */
    @FXML
    private TableColumn<Parts, Double> baseCostColumn;
    /**
     *  fx:id for ID column on above table
     */
    @FXML
    private TableColumn<Parts, Integer> basePartIDColumn;
    /**
     * fx:id for inventory column on above table
     */
    @FXML
    private TableColumn<Parts, Integer> baseInventoryLevelColumn;
    /**
     * fx:id for Name column on above table
     */
    @FXML
    private TableColumn<Parts, String> basePartNameColumn;
    /**
     *  fx:id for cost column on above table *
     */
    @FXML
    private TableColumn<Parts, Double> partsCostAssociated;
    /**
     * fx:id for ID column on above table
     */
    @FXML
    private TableColumn<Parts, Integer> partsIDAssociated;
    /**
     * fx:id for inventory column on above table
     */
    @FXML
    private TableColumn<Parts, Integer> partsInventoryLevelAssociated;
    /**
     * fx:id for Name column on above table
     */
    @FXML
    private TableColumn<Parts, String> partsNameAssociated;
    /**
     * table view for associated parts table
     */
    @FXML
    private TableView<Parts> AddAssociatedPartTable;

    @FXML
    private ScrollBar AssociatedPartTableSlider;


    @FXML
    private TextField IDProductsField;
    /**
     * fx id for text field for inventory
     */
    @FXML
    private TextField InvProductsField;
    /**
     * fx id for text field for maximum value
     */
    @FXML
    private TextField MaxProductsField;
    /**
     * fx id for text field for minimum value
     */
    @FXML
    private TextField MinProductsField;
    /**
     * fx id for text field for name
     */
    @FXML
    private TextField NameProductsField;
    /**
     * table view for part listing table
     */
    @FXML
    private TableView<Parts> AddPartListTable;

    @FXML
    private ScrollBar PartListTableSlider;
    /**
     * fx id for search bar
     */
    @FXML
    private TextField PartSearchProducts;
    /**
     * text field for price
     */
    @FXML
    private TextField PriceProductsField;

    /**
     * Search function action event
     */


    /**
     *
     * action event for search bar
     */
    @FXML
    void searchAddProduct (ActionEvent event) {
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
        AddPartListTable.setItems(parts);
//Error box if no parts are found with listed values
        if (parts.size() == 0) {
            showAlerts(6);
        }
    }
    /**
     *  Name search that search funtion utilizes to look for matching name string
     */

    private ObservableList<Parts> searchbyPartsName (String partialName){

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
     * ID search that search function utilizes to look for matching ID integer
     */

    private Parts getPartsID (int id){

        ObservableList<Parts> allParts = Inventory.getAllParts();
// simple For loop that differs from name search for loop
        //searches in increments of 1 looking for matching integer values then returns said values
        for(int i = 0; i < allParts.size(); i++) {

            Parts pt = allParts.get(i);

            if(pt.getId() == id){
                return pt;
            }
        }
        return null;
    }




    /**
     * Initializes values to their respective columns for the Add Parts table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       //gets and sets parts to the top table
        AddPartListTable.setItems(Inventory.getAllParts());
        basePartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        basePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        baseCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        baseInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
//gets all parts from parts table and adds them to Add Part table
        ObservableList<Parts> parts = Inventory.getAllParts();
        AddPartListTable.setItems(parts);

        //Initializes values to their respective columns for the Associated Parts table
        AddAssociatedPartTable.setItems(associatedParts);
        partsIDAssociated.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameAssociated.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsCostAssociated.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsInventoryLevelAssociated.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }
}

