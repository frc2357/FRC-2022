package com.team2357.frc2022.util;

public class Utility {
    public static boolean isWithinTolerance(double currentValue, double targetValue, double tolerance){
        return Math.abs(currentValue - targetValue) <= tolerance;
    }
}
