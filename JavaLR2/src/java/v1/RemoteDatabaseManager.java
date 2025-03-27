package java.v1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

public interface RemoteDatabaseManager extends Remote {
    void displayOrdersWithCustomers() throws RemoteException;
    void addCustomer(String name, String email) throws RemoteException;
    void addOrder(int customerId, Date orderDate) throws RemoteException;
    boolean deleteCustomer(int customerId) throws RemoteException;
    void deleteOrder(int orderId) throws RemoteException;
}