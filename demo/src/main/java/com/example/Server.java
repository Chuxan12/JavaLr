package com.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = new DatabaseManager(
                "jdbc:postgresql://localhost:5433/mydb", 
                "myuser", 
                "1488"
            );
            RemoteDatabaseManagerImpl service = new RemoteDatabaseManagerImpl(dbManager);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("DatabaseService", service);
            System.out.println("Сервер запущен...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}