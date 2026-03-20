import java.io.*;
import java.util.*;

class Transaction {
    String type;
    double amount;
    String category;

    Transaction(String type, double amount, String category) {
        this.type = type;
        this.amount = amount;
        this.category = category;
    }

    public String toString() {
        return type + " | " + amount + " | " + category;
    }
}

public class ExpenseTracker {
    static ArrayList<Transaction> list = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n1. Add Income\n2. Add Expense\n3. View Transactions\n4. Show Balance\n5. Save & Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addTransaction("Income");
                    break;
                case 2:
                    addTransaction("Expense");
                    break;
                case 3:
                    viewTransactions();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    return;
            }
        }
    }

    static void addTransaction(String type) {
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        list.add(new Transaction(type, amount, category));
    }

    static void viewTransactions() {
        for (Transaction t : list) {
            System.out.println(t);
        }
    }

    static void showBalance() {
        double balance = 0;

        for (Transaction t : list) {
            if (t.type.equals("Income"))
                balance += t.amount;
            else
                balance -= t.amount;
        }

        System.out.println("Current Balance: " + balance);
    }

    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter("data.txt");

            for (Transaction t : list) {
                fw.write(t.type + "," + t.amount + "," + t.category + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    static void loadFromFile() {
        try {
            File file = new File("data.txt");
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                list.add(new Transaction(parts[0], Double.parseDouble(parts[1]), parts[2]));
            }

            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading file");
        }
    }
}