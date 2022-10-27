module com.example.ims {
    requires javafx.controls;
    requires javafx.fxml;


exports partsproducts;
        opens partsproducts to javafx.fxml;



    exports main;
    opens main to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}