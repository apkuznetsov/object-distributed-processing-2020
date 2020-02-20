package com.company;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final String BINDING_NAME = "circlemath";

    public static void main(String[] args) {
        try {
            // создание объекта для удалённого доступа
            final CircleMathImpl service = new CircleMathImpl();

            // создание реестра расшаренных объектов
            final Registry registry = LocateRegistry.createRegistry(2099);

            // создание "заглушки" -– приёмника удалённых вызовов
            Remote stub = UnicastRemoteObject.exportObject(service, 0);

            // регистрация "заглушки" в реестре
            registry.bind(BINDING_NAME, stub);

            // усыпляем главный поток, иначе программа завершится
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
