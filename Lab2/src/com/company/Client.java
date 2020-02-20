package com.company;

import com.company.CircleMath.CircleMath;
import com.company.CircleMath.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static com.company.Server.BINDING_NAME;
import static com.company.Server.REGISTRY_PORT;

public class Client {
    private static BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            // создание реестра расшаренных объектов
            final Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            // получаем объект (на самом деле это proxy-объект)
            CircleMath service = (CircleMath) registry.lookup(BINDING_NAME);

            Point p1, p2;
            p1 = printGetPoint();
            p2 = printGetPoint();

            System.out.println("расчёт длины отрезка между точками = " + service.calcSegmentLen(p1, p2));
            System.out.println("расчёт длины окружности, центром которой является одна из точек, а радиусом -- расстояние между точками = " + service.calcCircleLen(p1, p2));
            System.out.println("расчёт площади круга, центром которого является одна из точек, а радиусом -- расстояние между точками = " + service.calcCircleArea(p1, p2));
            System.out.println("расчёт длины окружности, диаметром которой является расстояние между точками = " + service.calcCircleLenByDiameter(p1, p2));
            System.out.println("расчёт площади круга, диаметром которого является расстояние между точками = " + service.calcCircleAreaByDiameter(p1, p2));

            keyboardReader.close();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }

    private static Point printGetPoint() throws IOException {
        System.out.println("ВВЕДИТЕ ТОЧКУ:");
        System.out.println("-------------");

        System.out.println("введите x ... ");
        double x = Double.parseDouble(keyboardReader.readLine());

        System.out.println("введите y ... ");
        double y = Double.parseDouble(keyboardReader.readLine());

        Point p = new Point(x, y);
        System.out.println("точка " + p);
        System.out.println();

        return p;
    }
}
