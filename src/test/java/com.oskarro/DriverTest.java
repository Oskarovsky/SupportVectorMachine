package com.oskarro;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;

public class DriverTest extends ApplicationTest {

    private SupportVectorMachine svm = new SupportVectorMachine();

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        stage.toFront();
    }


    @Test
    void main() {
    }

    @Test
    void handleCommandLine() {
    }

    @Test
    void testStart() {
    }
}
