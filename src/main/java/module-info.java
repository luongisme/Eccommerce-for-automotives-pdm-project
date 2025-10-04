module com.example.pdm_project {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.pdm_project;                 // cho phép JavaFX khởi tạo LoginApp
    opens   com.example.pdm_project to javafx.fxml;  // cho FXMLLoader truy cập controller
}
