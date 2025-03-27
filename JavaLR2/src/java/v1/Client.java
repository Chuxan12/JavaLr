package java.v1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteDatabaseManager service = (RemoteDatabaseManager) registry.lookup("DatabaseService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Показать заказы с клиентами");
                System.out.println("2. Добавить клиента");
                System.out.println("3. Добавить заказ");
                System.out.println("4. Удалить клиента");
                System.out.println("5. Удалить заказ");
                System.out.println("0. Выход");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        service.displayOrdersWithCustomers();
                        break;
                    case 2:
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите email: ");
                        String email = scanner.nextLine();
                        service.addCustomer(name, email);
                        break;
                    case 3:
                        System.out.print("ID клиента: ");
                        int customerId = scanner.nextInt();
                        System.out.print("Дата заказа (гггг-мм-дд): ");
                        String date = scanner.next();
                        service.addOrder(customerId, Date.valueOf(date));
                        break;
                    case 4:
                        System.out.print("ID клиента для удаления: ");
                        int cId = scanner.nextInt();
                        if (service.deleteCustomer(cId)) {
                            System.out.println("Клиент удален.");
                        } else {
                            System.out.println("Удаление отменено.");
                        }
                        break;
                    case 5:
                        System.out.print("ID заказа для удаления: ");
                        int oId = scanner.nextInt();
                        service.deleteOrder(oId);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Неверный выбор!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}