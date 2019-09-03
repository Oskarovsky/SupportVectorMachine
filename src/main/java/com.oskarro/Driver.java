package com.oskarro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.math3.linear.MatrixUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The SupportVectorMachine program implements an application
 * that simply handles machine learning based on SVM
 *
 * This application presents the events when students want to
 * pass the exam. This SVM algorithm decide if students passed or not
 *
 * @author  Oskar Slyk
 * @version 1.0
 * @since   2019-08-13
 */


public class Driver extends Application {

    // { {xValue, yValue}, {hired(+1)/notHired(-1)} }
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

    private static SupportVectorMachine svm = null;

    public static void main(String[] args) {
        double[][] xArray = new double[TRAINING_DATA.length][2];
        double[][] yArray = new double[TRAINING_DATA.length][1];
        for (int i = 0; i < TRAINING_DATA.length; i++) {
            xArray[i][0] = TRAINING_DATA[i][0][0];
            xArray[i][1] = TRAINING_DATA[i][0][1];
            yArray[i][0] = TRAINING_DATA[i][1][0];
        }
        svm = new SupportVectorMachine(MatrixUtils.createRealMatrix(xArray), MatrixUtils.createRealMatrix(yArray));
        displayInfoTables(xArray, yArray);
        launch();
    }

    private static void displayInfoTables(double[][] xArray, double[][] yArray) {
        System.out.println("     Support Vector   | label | alpha");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        for (int i = 0; i < xArray.length; i++) {
            if (svm.getAlpha().getData()[i][0] > ZERO && svm.getAlpha().getData()[i][0] != SupportVectorMachine.C) {
                StringBuilder yValueInString = new StringBuilder(String.valueOf(yArray[i][0]));
                yValueInString.setLength(5);
                System.out.println(Arrays.toString(xArray[i]) +" | "+ yValueInString +" | "+
                        new String(String.format("%.10f", svm.getAlpha().getData()[i][0])));
            }
        }
        System.out.println("\n             wT              |  b  ");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        System.out.println("<"+ (new String(String.format("%.9f", svm.getW().getData()[0][0])) + ", "
                +  new String(String.format("%.9f", svm.getW().getData()[1][0]))) +">   | "+svm.getB());
    }

    private static void handleCommandLine() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n> to classify students enter scores for exams 1 & 2 (or exit):");
            String[] values = (bufferedReader.readLine()).split(" ");
            if (values[0].equals("exit"))
                System.exit(0);
            else {
                try {
                    System.out.println();
                    svm.classify(MatrixUtils.createRealMatrix(new double[][] {{ Double.parseDouble(values[0]), Double.parseDouble(values[1])}}));
                } catch (Exception e) {
                    System.out.println("invalid input");
                }
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        XYChart.Series<Number, Number> notPassedStudentSeries = new XYChart.Series<Number, Number>();
        notPassedStudentSeries.setName("Student not passed");
        XYChart.Series<Number, Number> passedStudentSeries = new XYChart.Series<Number, Number>();
        passedStudentSeries.setName("Student passed");

        IntStream.range(0, Driver.TRAINING_DATA.length).forEach(i -> {
            double x = Driver.TRAINING_DATA[i][0][0], y = Driver.TRAINING_DATA[i][0][1];
            if (Driver.TRAINING_DATA[i][1][0] == -1.0)
                notPassedStudentSeries.getData().add(new XYChart.Data<Number, Number>(x, y));
            else passedStudentSeries.getData().add(new XYChart.Data<Number, Number>(x, y));
        });

        NumberAxis xAxis = new NumberAxis(0, 10, 1.0);
        xAxis.setLabel("Score for student exam # 1");
        NumberAxis yAxis = new NumberAxis(0, 10, 1.0);
        yAxis.setLabel("Score for student exam # 2");

        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        scatterChart.getData().add(notPassedStudentSeries);
        scatterChart.getData().add(passedStudentSeries);

        double m = -(svm.getW().getData()[0][0]/svm.getW().getData()[1][0]);
        double b = -(svm.getB()/svm.getW().getData()[1][0]);
        double score1X = 0.00, score1Y = m*score1X + b, score2X = 10.00, score2Y = m*score2X + b;

        XYChart.Series<Number, Number> studentSeriesInResult = new XYChart.Series<Number, Number>();
        studentSeriesInResult.getData().add(new XYChart.Data<Number, Number>(score1X, score1Y));
        studentSeriesInResult.getData().add(new XYChart.Data<Number, Number>(score2X, score2Y));

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.getData().add(studentSeriesInResult);
        lineChart.setOpacity(0.4);

        Pane pane = new Pane();
        pane.getChildren().addAll(scatterChart, lineChart);
        primaryStage.setScene(new Scene(pane, 550, 420));
        primaryStage.setOnHidden(e -> {
            try {
                handleCommandLine();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        System.out.println("\nClose display window to proceed");
        primaryStage.setTitle("Support Vector Machines (01) - w/ SMO (Sequential Minimal Optimization)");
        primaryStage.show();
    }

}
