module ca.georgiancollege.democomp1011midtermprep {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.georgiancollege.democomp1011midtermprep to javafx.fxml;
    exports ca.georgiancollege.democomp1011midtermprep;
}