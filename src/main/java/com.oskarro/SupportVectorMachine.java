package com.oskarro;

import org.apache.commons.math3.linear.RealMatrix;

class SupportVectorMachine {

    private static final double MIN_ALPHA_OPTIMIZATION = 0.00001;
    private static final int MAX_NUMB_OF_ITERATIONS = 50;
    private static final double EPSILON = 0.001;

    private RealMatrix w;
    private RealMatrix x, y;  // x represent features, while y represents labels
    private RealMatrix alpha;

    static final double C = 1.0;
    private double b = 0;

    SupportVectorMachine(RealMatrix xValue, RealMatrix yValue) {
    }


    private int performSMO() {
        return 1;
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

    private  boolean checkIfAlphaViolatesKKT(double alpha, double e) {
        return true;
    }

    private double[] boundAlpha(double alphaI, double alphaJ, double yI, double yJ) {
        double[] bounds = new double[2];

        return bounds;
    }

/*    private RealMatrix calcW() {

    }*/

    private int selectIndexOf2ndAlphaToOptimize(int indexOf1stAlpha, int numbOfRows) {
        return 1;
    }

/*    String classify(RealMatrix entry) {

    }

    private static RealMatrix mult(RealMatrix matrix1, RealMatrix matrix2) {

    }*/

    RealMatrix getAlpha() { return alpha; }
    RealMatrix getW() { return w; }
    double getB() { return b; }
}
