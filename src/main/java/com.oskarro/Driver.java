package com.oskarro;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

/**
 * The SupportVectorMachine program implements an application
 * that simply handles machine learning based on SVM
 *
 * @author  Oskar Slyk
 * @version 1.0
 * @since   2019-08-13
 */

class Driver extends Application {
    public static void main(String[] args) {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    private static void displayInfoTables(double[][] xArray, double[][] yArray) {
        System.out.println("    Support Vector    |  label  |  alpha");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        for (int i=0; i<xArray.length; i++) {
        }
    }

    private static void handleCommandLine() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

}



