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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.jar.Attributes;

public class ModifyPartsFormController implements Initializable {

    Stage stage;
    Parent scene;
    /**
     * class identifiers for the controller
     */

    private Parts selectedParts;
    public RadioButton inHouseModify;
    public RadioButton outSourcedModify;

    public Label modifymachine;

    Inventory inv;
    Parts part;

    /**
     * Alert container so I didnt have to keep writing out the error boxes
     */

    private void showAlert(int alertType) {
//Sets alert type
        Alert alert = new Alert(Alert.AlertType.ERROR);
//allows ability to switch between the different alert cases
        switch (alertType) {
            //identifies and creates the first alert case
            case 1:
                //Error title
                alert.setTitle("ATTENTION!");
                //Error header
                alert.setHeaderText("Part Modification Failed!");
                //Error content
                alert.setContentText("Please input correct, non-null values.");
                //ensures that the method waits for user input before continuing
                alert.showAndWait();
                break;
            //identifies and creates the second alert case
            case 2:
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("Incorrect Value Input for Inventory");
                alert.setContentText("Inventory Value must be within range set by Min and Max values.");
                alert.showAndWait();
                break;
//identifies and creates the third alert case
            case 3:
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("Incorrect Value Input for Machine ID");
                alert.setContentText("Machine ID value can only contain numbers.");
                alert.showAndWait();
                break;
            //identifies and creates the fourth alert case
            case 4:
                alert.setTitle("ATTENTION!");
                alert.setHeaderText("Incorrect Value Input for Min");
                alert.setContentText("Value input for Min cannot be greater than Max or zero.");
                alert.showAndWait();
                break;

        }
    }
    /**
     * Cancel button action event
     */
    @FXML
    void OnActionCancelModifyParts(ActionEvent event) throws IOException {
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
     * changes bottom value label to machine id when In House radio button is pressed
     */

    @FXML
    void OnActionInHouseRadio(ActionEvent event)
    //sets the text
    {
        modifymachine.setText("Machine ID");
    }
    /**
     * changes bottom value label to company name when Outsourced radio button is pressed
     */

    @FXML
    void OnActionOutsourced(ActionEvent event)
    //sets the text
    {
        modifymachine.setText("Company Name");
    }
    /**
     * Save button action event - saves values to main form partstable
     */

    @FXML
    void OnActionSaveModifyParts(ActionEvent event) throws IOException {
        //tries to execute block of texting while attempting to catch exceptions

        try {
            //boolean to mark a successful addition or not - helps execute actions for a failed addition
            boolean successfulAddition = false;
            //value identifiers that collects inputs from their respective fields
            int id = selectedParts.getId();
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
                    showAlert(4);
                    //else if stock is greater than max or less than min
                } else if (max < stock || stock < min) {
                    //alert type declared at top of the page
                    showAlert(2);

                } else {
                    // if In House radio button is selected
                    if (inHouseModify.isSelected()) {
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
                            showAlert(5);
                        }
                        // if Out sourced radio button is selected
                    }
                    if (outSourcedModify.isSelected()) {
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

                        Inventory.removeParts(selectedParts);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }

            } catch (Exception nfe) {
                showAlert(1);
            }
        }
    /**
     * ID field
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
    private TextField MachineIDPartsField;/**
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
     *  Initializer
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gets selected part for modification
        selectedParts = PartsProductsMainSceneController.getPartforMod();
// fills fields with values from the part that was selected at the main screen
        //both if statements supply info per radiobutton for respective fields
        if (selectedParts instanceof InHouse) {
            inHouseModify.setSelected(true);
            modifymachine.setText("Machine ID");
            MachineIDPartsField.setText(String.valueOf(((InHouse) selectedParts).getMachineID()));
        }

        if (selectedParts instanceof Outsourced){
            outSourcedModify.setSelected(true);
            modifymachine.setText("Company Name");
            MachineIDPartsField.setText(((Outsourced) selectedParts).getCompanyName());
        }
//sends values to their respective fields
        IDPartsField.setText(String.valueOf(selectedParts.getId()));
        NamePartsField.setText(selectedParts.getName());
        InvPartsField.setText(String.valueOf(selectedParts.getStock()));
        PriceCostPartsField.setText(String.valueOf(selectedParts.getPrice()));
        MaxPartsField.setText(String.valueOf(selectedParts.getMax()));
        MinPartsField.setText(String.valueOf(selectedParts.getMin()));

    }
        }




