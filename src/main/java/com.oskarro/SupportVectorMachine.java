package com.oskarro;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class SupportVectorMachine {

    private static final double MIN_ALPHA_OPTIMIZATION = 0.00001;
    private static final int MAX_NUMB_OF_ITERATIONS = 50;
    private static final double EPSILON = 0.001;

    private RealMatrix w;
    private RealMatrix xValue, yValue;  // x represent features, while y represents labels
    private RealMatrix alpha;

    static final double C = 1.0;
    private double b = 0;

    SupportVectorMachine(RealMatrix xValue, RealMatrix yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        double[] alphaArray = new double[xValue.getData().length];
        IntStream.range(0, alphaArray.length).forEach(i -> alphaArray[i] = 0);
        alpha = MatrixUtils.createColumnRealMatrix(alphaArray);
        int i=0;
        while (i < MAX_NUMB_OF_ITERATIONS) {
            if (performSMO() == 0) {
                i += 1;
            } else {
                i = 0;
            }
        }
    }


    private int performSMO() {
        int numberOfAlphaPairsOptimized = 0;
        for (int i=0; i<xValue.getData().length; i++) {
            // here we calculate the error for the first alpha (the value of error)
            RealMatrix Ei = multiplyItems(yValue, alpha)
                    .transpose()
                    .multiply(xValue.multiply(xValue.getRowMatrix(i).transpose()))
                    .scalarAdd(b)
                    .subtract(yValue.getRowMatrix(i));

            if (checkIfAlphaViolatesKKT(alpha.getEntry(i, 0), Ei.getEntry(0, 0))) {
                // here we calculate the error for the second alpha (the value of error)
                int j = selectIndexOf2ndAlphaToOptimize(i, xValue.getData().length);
                RealMatrix Ej = multiplyItems(yValue, alpha)
                        .transpose()
                        .multiply(xValue.multiply(xValue.getRowMatrix(j).transpose()))
                        .scalarAdd(b)
                        .subtract(yValue.getRowMatrix(j));
                double alphaIOld = alpha.getRowMatrix(i).getEntry(0, 0);
                double alphaJOld = alpha.getRowMatrix(j).getEntry(0, 0);
                double[] bounds = boundAlpha(alpha.getEntry(i, 0), alpha.getEntry(j, 0),
                        yValue.getEntry(i, 0), yValue.getEntry(j, 0));

            }
        }
        return numberOfAlphaPairsOptimized;
    }

    private static RealMatrix multiplyItems(RealMatrix matrix1, RealMatrix matrix2) {
        double[][] returnData = new double[matrix1.getData().length][matrix1.getData()[0].length];
        IntStream.range(0, matrix1.getData().length).forEach(r ->
                IntStream.range(0, matrix1.getData()[0].length).forEach(c ->
                        returnData[r][c] = matrix1.getEntry(r, c) * matrix2.getEntry(r, c)));
        return MatrixUtils.createRealMatrix(returnData);
    }

    private boolean optimizeAlphaPair(int i, int j, double Ei, double Ej, double ETA,
                                      double[] bounds, double alphaIold, double alphaJold) {
     return true;
    }

    private  void optimizeAlphaISameAsAlphaJOppositeDirection(int i, int j, double alphaJold) {

    }

    private  void optimizeB(double Ei, double Ej, double Iold, double alphaJold, int i, int j) {

    }

    private void clipAlphaJ(int i, double highBound, double lowBound) {

    }

    private boolean checkIfAlphaViolatesKKT(double alpha, double e) {
        return (alpha > 0 && Math.abs(e) < EPSILON) || (alpha < C && Math.abs(e) > EPSILON);
    }
    private double[] boundAlpha(double alphaForI, double alphaForJ, double yForI, double yForJ) {
        double[] bounds = new double[2];
        if (yForI == alphaForJ) {
            bounds[0] = Math.max(0, alphaForI + alphaForJ - C);
            bounds[1] = Math.min(C, alphaForI + alphaForJ);
        } else {
            bounds[0] = Math.max(0, alphaForJ - alphaForI);
            bounds[1] = Math.min(C, alphaForJ - alphaForI + C);
        }
        return bounds;
    }

/*    private RealMatrix calcW() {

    }*/

    private int selectIndexOf2ndAlphaToOptimize(int indexOf1stAlpha, int numbOfRows) {
        int indexOf2ndAlpha = indexOf1stAlpha;
        while (indexOf1stAlpha==indexOf2ndAlpha) {
            indexOf2ndAlpha = ThreadLocalRandom.current().nextInt(0, numbOfRows-1);
        }
        return indexOf2ndAlpha;
    }

/*    String classify(RealMatrix entry) {

    }

    private static RealMatrix mult(RealMatrix matrix1, RealMatrix matrix2) {

    }*/

    RealMatrix getAlpha() { return alpha; }
    RealMatrix getW() { return w; }
    double getB() { return b; }
}
