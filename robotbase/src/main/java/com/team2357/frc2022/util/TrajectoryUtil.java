package com.team2357.frc2022.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public class TrajectoryUtil {

    // Factor that pulls the bezier curve control points to make it curvy
    // Abritary value above 0, should be just enough to create a smooth curve
    private static double m_controlFactor = 1.0 / 3.0;

    // Factor used to determine how many points to return on a bezier curve
    // Based on length of line from point a to point b
    private static double m_pointFactorMeters = 0.25;

    // Generate a series of waypoints for the trajectory to generate spline off of.
    // Can be done by first creating control points for bezier curve
    // Then generating the bezier curve
    // Lastly returning a list of points every-so often on the bezier curve
    public static List<Translation2d> generateWaypoints(Pose2d startPose, Pose2d endPose) {
        double x1 = startPose.getX();
        double y1 = startPose.getY();
        double x2 = endPose.getX();
        double y2 = endPose.getY();

        // Find length of line
        double len = Math.hypot(x2 - x1, y2 - y1);

        // Find vectors along line to use to calculate control points
        double ax1 = startPose.getRotation().getCos() * len * m_controlFactor;
        double ay1 = startPose.getRotation().getSin() * len * m_controlFactor;

        double ax2 = endPose.getRotation().getCos() * len * m_controlFactor;
        double ay2 = endPose.getRotation().getSin() * len * m_controlFactor;

        // Bezier curve control points
        double cx1 = x1 + ax1;
        double cy1 = y1 + ay1;
        double cx2 = x2 - ax1;
        double cy2 = y2 - ay2;
        double cx3 = x2;
        double cy3 = y2;

        System.out.println("x1: " + x1);
        System.out.println("y1: " + y1);
        System.out.println("x2: " + x2);
        System.out.println("y2: " + y2);
        System.out.println("Start pose cos: " + startPose.getRotation().getCos());
        System.out.println("Start pose sin: " + startPose.getRotation().getSin());

        System.out.println("len: " + len);
        System.out.println();
        System.out.println("ax1: " + ax1);
        System.out.println("ay1: " + ay1);
        System.out.println("ax2: " + ax2);
        System.out.println("ay2: " + ay2);
        System.out.println();
        System.out.println("cx1: " + cx1);
        System.out.println("cy1: " + cy1);
        System.out.println("cx2: " + cx2);
        System.out.println("cy2: " + cy2);
        System.out.println("cx3: " + cx3);
        System.out.println("cy3: " + cy3);

        int numOfPoints = (int) (len/m_pointFactorMeters);
        double tIncrement = 1 / numOfPoints;
        double t = 0;

        List<Translation2d> wayPoints = new ArrayList<Translation2d>();
        for (int i = 0; i < numOfPoints; i++) {
            double x = BezierCurveValue(t, cx1, cx2, cx3);
            double y = BezierCurveValue(t, cy1, cy2, cy3);
            Translation2d newWaypoint = new Translation2d(x, y);
            wayPoints.add(newWaypoint);
            t += tIncrement;
        }

        return wayPoints;
    }

    /**
     * Formula for finding a point on a 3-point bezier curve.
     * Just a polynomial. The cp's should either be the x values or y values of the
     * control points. DO NOT intermix x and y
     * 
     * @param t   represents time along the curve, so a t of 0.75 will yeield the
     *            point on the curve 75% of the way to the end point
     * @param cp1 The first control point
     * @param cp2 The second controle point
     * @param cp3 The third control point
     * @return The resulting value represents one partial coordinate a coordiante on the bezier curve
     */
    public static double BezierCurveValue(double t, double cp1, double cp2, double cp3) {
        return (Math.pow((1 - t), 2) * cp1) + (2 * (1 - t) * t * cp2) + (Math.pow(t, 2) * cp3);
    }
}
