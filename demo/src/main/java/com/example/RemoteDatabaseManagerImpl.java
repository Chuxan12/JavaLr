package com.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Date;

public class RemoteDatabaseManagerImpl extends UnicastRemoteObject implements RemoteDatabaseManager {
    private final DatabaseManager dbManager;

    public RemoteDatabaseManagerImpl(DatabaseManager dbManager) throws RemoteException {
        super();
        this.dbManager = dbManager;
    }

    @Override
    public void displayOrdersWithCustomers() throws RemoteException {
        try {
            dbManager.displayOrdersWithCustomers();
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public void addCustomer(String name, String email) throws RemoteException {
        try {
            dbManager.addCustomer(name, email);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public void addOrder(int customerId, Date orderDate) throws RemoteException {
        try {
            dbManager.addOrder(customerId, orderDate);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) throws RemoteException {
        try {
            return dbManager.deleteCustomer(customerId);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public void deleteOrder(int orderId) throws RemoteException {
        try {
            dbManager.deleteOrder(orderId);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }
}