package Utility;

import java.util.Set;

/**
 * Helper class for printing debug statements.
 */
public class Util {





    /**
     * fractional error in math formula less than 1.2 * 10 ^ -7.
     * although subject to catastrophic cancellation when z in very close to 0
     * from Chebyshev fitting formula for erf(z) from Numerical Recipes, 6.2
     *
     * Provided by Princeton from following link https://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html
     */
    public static double erf(double z) {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                t * ( 1.00002368 +
                t * ( 0.37409196 +
                t * ( 0.09678418 +
                t * (-0.18628806 +
                t * ( 0.27886807 +
                t * (-1.13520398 +
                t * ( 1.48851587 +
                t * (-0.82215223 +
                t * ( 0.17087277))))))))));
        if (z >= 0)
            return  ans;
        else
            return -ans;
    }

    /**
     * Calculates cumulative log normal probability given  x
     * @param x
     * @param mu
     * @param sigma
     * @return
     */
    public static double log_norm(double x,double mu, double sigma){
        if(x == 0)
            return 0;

        sigma = Math.sqrt(sigma);
        double a = (Math.log(x) - mu)/Math.sqrt(2*Math.pow(sigma,2));
        return 0.5 + 0.5*erf(a);
    }

    /**
     * Generates a random time in seconds given the parameters of a log normal distribution
     * @param mu
     * @param sigma
     * @return
     */
    public static double get_x_of_log_normal(double mu, double sigma){
        double probability = Math.random();
        double i;
        for(i = 0; Util.log_norm(i,mu,sigma) < probability;i+=0.001);

        return i*Configuration.SHIFT;//value obtained shifted to tenths of a minute
    }

}