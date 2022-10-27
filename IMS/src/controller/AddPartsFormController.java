package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import partsproducts.InHouse;
import partsproducts.Inventory;
import partsproducts.Outsourced;
import partsproducts.Parts;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartsFormController implements Initializable {

    public RadioButton inHouse;
    public RadioButton outSourced;
    public Label machineLabel;

    Stage stage;
    Parent scene;
    /**
     * Alert container so I didnt have to keep writing out the error boxes
     */

    private void showAlerts(int alertType) {
        /**
         * Sets alert type
         */

        Alert alert = new Alert(Alert.AlertType.ERROR);
        /**
         * allows ability to switch between the different alert cases
         */

        switch (alertType) {
            /**
             * identifies and creates the first alert case
             */

            case 1:
                //Error title
                alert.setTitle("Warning!");
                //Error header
                alert.setHeaderText("Failed to add new part");
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
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();

                break;
        }
    }

    /**
     * Action event for dropping changes and going back to main form
     */

    @FXML
    public void OnActionCancelAddParts(ActionEvent event) throws IOException {
//sets alert type to Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You will lose unsaved values. Do you want to continue?");
    //waits for user input before continuing
        Optional<ButtonType> result = alert.showAndWait();
        //if OK button is pressed then executes code block
        if(result.isPresent() && result.get() == ButtonType.OK)
        //sends user back to main form
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
    /**
     *  changes bottom value label to machine id when In House radio button is pressed
     */

    @FXML
    public void OnActionAddInHouseRadio(ActionEvent actionEvent) {
       //sets the text
        machineLabel.setText("Machine ID");

    }
    /**
     *  changes bottom value label to company name when Outsourced radio button is pressed
     */

    @FXML
    public void OnActionAddOutsourcedRadio(ActionEvent actionEvent) {
       //sets text
        machineLabel.setText("Company Name");

    }
    /**
     * Save button action event - saves values to main form partstable
     */

    @FXML
    public void OnActionSaveAddParts(ActionEvent event) throws IOException {
        //tries to execute block of texting while attempting to catch exceptions
        try {
            //boolean to mark a successful addition or not - helps execute actions for a failed addition
            boolean successfulAddition = false;
            //value identifiers that collects inputs from their respective fields
            int id = 0;
            String name = NamePartsField.getText();
            int stock = Integer.parseInt(InvPartsField.getText());
            double price = Double.parseDouble(PriceCostPartsField.getText());
            int max = Integer.parseInt(MaxPartsField.getText());
            int min = Integer.parseInt(MinPartsField.getText());
            int machineID;
            String companyName;

            // if statement that ensures max is not less than min
                if (max < min) {
                    //alert type declared at top of the page
                    showAlerts(4);
                    //else if stock is greater than max or less than min
                } else if (max < stock || stock < min) {
                    //alert type declared at beginnign
                    showAlerts(3);
                } else {

                    // if In House radio button is selected
                    if (inHouse.isSelected()) {
                        //tries adding part with machine ID value
                        try {
                            //gets text from machine ID field in the form of an integer only when In House is selected
                            machineID = Integer.parseInt(MachineIDPartsField.getText());
                            //sets In House part values
                            InHouse newIHPart = new InHouse(id, name, price, stock, min, max, machineID);
                            //gets new auto generated ID for new part
                            newIHPart.setId(Inventory.getNewPartsID());
                            //adds part to the inventory list
                            Inventory.addPart(newIHPart);
                            successfulAddition = true;
                        } catch (Exception nfe) {
                            showAlerts(5);
                        }
                        // if Out sourced radio button is selected
                    }
                    if (outSourced.isSelected()) {
                        //gets text from machine ID field in the form of a string only when Out Sourced is selected
                        companyName = MachineIDPartsField.getText();
                        //assigns Out sourced part values
                        Outsourced newOSpart = new Outsourced(id, name, price, stock, min, max, companyName);
                        //gets new auto generated ID for new part
                        newOSpart.setId(Inventory.getNewPartsID());
                        //adds part to inventory list
                        Inventory.addPart(newOSpart);
                        successfulAddition = true;

                    }
                    // if part addition is successful (without error) then sends user back to main form
                    if (successfulAddition) {
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }

            } catch (NumberFormatException nfe) {
                showAlerts(1);
            }
        }




    /**
     * ID field (not used since ID field is disabled)
     */

    @FXML
    private TextField IDPartsField;
    /**
     * Stock/Inventory input field
     */

    @FXML
    private TextField InvPartsField;
    /**
     * Machine ID / Company name input field
     */

    @FXML
    private TextField MachineIDPartsField;
    /**
     * Maximum input field
     */

    @FXML
    private TextField MaxPartsField;
    /**
     * Minimum input field
     */

    @FXML
    private TextField MinPartsField;
    /**
     * Names input field
     */

    @FXML
    private TextField NamePartsField;
    /**
     * Cost input field
     */

    @FXML
    private TextField PriceCostPartsField;
    /**
     * Initializer (nothing needs to be initialized for this form)
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }





}
