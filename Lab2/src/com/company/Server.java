package com.company;

import com.company.CircleMath.CircleMathImpl;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int REGISTRY_PORT = 2099;
    public static final String BINDING_NAME = "circlemath";

    public static void main(String[] args) {
        try {
            // создание объекта для удалённого доступа
            final CircleMathImpl service = new CircleMathImpl();
            // создание "заглушки" -– приёмника удалённых вызовов
            Remote stub = UnicastRemoteObject.exportObject(service, 0);

            // создание реестра расшаренных объектов
            final Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
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
