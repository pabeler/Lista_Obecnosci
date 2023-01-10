module com.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;

    opens com.common to org.hibernate.orm.core;
//    opens com.frontend.demo1 to javafx.fxml;
//    exports com.frontend.demo1;
    exports com.frontend;
    opens com.frontend to javafx.fxml;
}
