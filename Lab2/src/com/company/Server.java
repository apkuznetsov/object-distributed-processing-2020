package com.company;

import java.rmi.RemoteException;

import static java.lang.Math.*;

public class Server implements RemoteInterface {

    public static void main(String[] args) {

    }

    @Override
    public double calcSegmentLen(Point p1, Point p2) throws RemoteException {
        return sqrt(pow(p2.getX() - p1.getX(), 2) + pow(p2.getY() - p1.getY(), 2));
    }

    @Override
    public double calcCircleLen(Point centerPoint, Point lastRadiusPoint) throws RemoteException {
        return 2 * PI * calcSegmentLen(centerPoint, lastRadiusPoint);
    }

    @Override
    public double calcCircleArea(Point centerPoint, Point lastRadiusPoint) throws RemoteException {
        return PI * pow(calcSegmentLen(centerPoint, lastRadiusPoint), 2);
    }

    @Override
    public double calcCircleLenByDiameter(Point p1, Point p2) throws RemoteException {
        return PI * calcSegmentLen(p1, p2);
    }

    @Override
    public double calcCircleAreaByDiameter(Point p1, Point p2) throws RemoteException {
        return PI * (calcSegmentLen(p1, p2) / 4);
    }
}
