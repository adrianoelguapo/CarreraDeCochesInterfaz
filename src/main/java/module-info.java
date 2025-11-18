module com.akadoblee.carreradecochesinterfaz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.akadoblee.carreradecochesinterfaz to javafx.fxml;
    exports com.akadoblee.carreradecochesinterfaz;
}