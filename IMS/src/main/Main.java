package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import partsproducts.InHouse;
import partsproducts.Inventory;
import partsproducts.Parts;
import partsproducts.Product;

import java.io.IOException;
/** Ashton Burke
* C482 Software 1
* 10/25/2022
*
 * ATTENTION: Please see single line comments for a more in depth description
 *
 * FUTURE ENHANCEMENT: I think a good enhancement to the program would be giving the program the ability to add associated parts on its own.
 * By using an Associated Part ID field in the part forms and an Associated part ID in the products form, one could write a script that automatically pairs associated parts with their respective products rather than forcing the user to do it by hand.
 * For example: If a part is associated with a car then you could give every car part an Associated ID of 1, and then when creating the Car product, it will automatically add all parts with the Associated ID of 1.
 *
 * JAVADOC LOCATION: Javadoc file is located in the base IMS folder
 *
 */



public class Main extends Application {

   /**
    loads the main form
    **/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/PartsProductsMainScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  loads test data to main
     */

    public static void main(String[] args) {
        /**
         *   generates new part id for created part
         */

        int partsID = Inventory.getNewPartsID();
        /**
         * Creates parts for table on  startup and assigns respective values
         */

        InHouse part1 = new InHouse(partsID, "Refractor",10.99, 3, 1, 10, 1);
        /**
         * Continues ID generation
         */

        partsID = Inventory.getNewPartsID();

        InHouse part2 = new InHouse(partsID, "Lazer Focus", 99.99, 5, 1, 10, 2 );
        /**
         * Includes created parts and their values to Parts table
         */

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        /**
         * gives test data new generated id
         */

        int productID = Inventory.getNewProductID();
        /**
         * creates product
         */

        Product product1 = new Product(productID, "Ray gun", 99.99, 5, 1, 5);
        /**
         * gives product an associated part
         */

        product1.addAssociatedPart(part1);

        /**
         * adds product to inventory
         */

        Inventory.addProduct(product1);

        launch(args);
    }
}