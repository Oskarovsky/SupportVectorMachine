package com.oskarro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
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

public class Driver extends Application {

    public SupportVectorMachine svm = new SupportVectorMachine();

    private static final double[][][] TRAINING_DATA = {{{9.123456, 3.123456}, {+1}},
            {{9.123456, 5.123456}, {+1}},
            {{5.123456, 5.123456}, {-1}},
            {{8.123456, 6.654321}, {+1}},
            {{4.654321, 4.123456}, {-1}},
            {{2.123456, 4.123456}, {-1}},
            {{9.123456, 7.123456}, {+1}},
            {{4.123456, 4.654321}, {-1}},
            {{8.654321, 2.123456}, {+1}},
            {{2.123456, 2.123456}, {-1}},
            {{3.123456, 3.123456}, {-1}},
            {{8.654321, 4.123456}, {+1}},
            {{7.123456, 6.123456}, {+1}},
            {{4.123456, 7.123456}, {-1}},
            {{6.923456, 4.623456}, {-1}},
            {{8.123456, 5.123456}, {+1}},
            {{3.123456, 4.123456}, {-1}}};
    private static final double ZERO = 0.000000009;


    public static void main(String[] args) {
        double[][] xArray = new double[TRAINING_DATA.length][2];
        double[][] yArray = new double[TRAINING_DATA.length][1];
        for (int i = 0; i < TRAINING_DATA.length; i++) {
            xArray[i][0] = TRAINING_DATA[i][0][0];
            xArray[i][1] = TRAINING_DATA[i][0][1];
            yArray[i][0] = TRAINING_DATA[i][1][0];
        }
        displayInfoTables(xArray, yArray);
        launch();
    }

    private static void displayInfoTables(double[][] xArray, double[][] yArray) {
        System.out.println("     Support Vector   | label | alpha");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        for (int i=0; i<xArray.length; i++) {

        }
    }

    public static void handleCommandLine() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n> to classify new candidate enter scores for interviews 1 & 2 (or exit):");
            String[] values = (bufferedReader.readLine()).split(" ");
            if (values[0].equals("exit"))
                System.exit(0);
            else {
                try {
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("invalid input");
                }
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        XYChart.Series<Number, Number> notHiredCandidateSeries = new XYChart.Series<>();
        notHiredCandidateSeries.setName("Candidate Not Hired");
        XYChart.Series<Number, Number> hiredCandidateSeries = new XYChart.Series<>();
        hiredCandidateSeries.setName("Candidate Hired");

/*        IntStream.range(0, Driver.TRAINING_DATA.length)
                .forEach(i -> {
                    double x = Driver.TRAINING_DATA[i][0][0], y = Driver.TRAINING_DATA[i][0][1];

                    if (Driver.TRAINING_DATA[i][0][0] == -1.0) {
                        notHiredCandidateSeries.getData().add(new XYChart.Data<>(x, y));
                    } else {
                        hiredCandidateSeries.getData().add(new XYChart.Data<>(x, y));
                    }
                });*/

        assignData(notHiredCandidateSeries, hiredCandidateSeries);

        NumberAxis xAxis = new NumberAxis(0, 10, 1.0);
        xAxis.setLabel("Score for candidate interview # 1");
        NumberAxis yAxis = new NumberAxis(0, 10, 1.0);
        xAxis.setLabel("Score for candidate interview # 2");

        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        scatterChart.getData().add(notHiredCandidateSeries);
        scatterChart.getData().add(hiredCandidateSeries);

        primaryStage.show();
    }

    private void assignData(XYChart.Series<Number, Number> notHired, XYChart.Series<Number, Number> hired) {
        IntStream.range(0, Driver.TRAINING_DATA.length)
                .forEach(i -> {
                    double x = Driver.TRAINING_DATA[i][0][0], y = Driver.TRAINING_DATA[i][0][1];
                    if (Driver.TRAINING_DATA[i][0][0] == -1.0) {
                        notHired.getData().add(new XYChart.Data<>(x,y));
                    } else {
                        hired.getData().add(new XYChart.Data<>(x,y));
                    }
                });
    }


/*
    private static void displayInfoTables(double[][] xArray, double[][] yArray) {
        System.out.println("    Support Vector    |  label  |  alpha");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        for (int i=0; i<xArray.length; i++) {
        }
    }
*/

}



