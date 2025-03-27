package java.v1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = new DatabaseManager(
                "jdbc:postgresql://localhost:5432/mydb", 
                "user", 
                "password"
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