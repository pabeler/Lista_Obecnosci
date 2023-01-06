module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jandex;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;

    opens com.common to org.hibernate.orm.core;
    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}
