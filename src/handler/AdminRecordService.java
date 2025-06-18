package handler;

import datastructure.LinkedList;
import entity.Admin;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AdminRecordService {
    private static LinkedList<Admin> adminList;

    public static LinkedList<Admin> getAdminList() {
        return adminList;
    }

    public static void makeAdminList() {
        adminList =  new LinkedList<>();
    }

    public static Admin findAdminById(int id) {
        for (int i = 0; i < adminList.size(); i++) {
            Admin nowAdmin = adminList.get(i);
            if (nowAdmin.getId() == id) {
                return nowAdmin;
            }
        }
        return null;
    }

    public static int getMaxId() {
        int max = 30000; // Starting point for admin IDs (e.g., 3001, 3002, ...)
        for (int i = 0; i < adminList.size(); i++) {
            Admin nowAdmin = adminList.get(i);
            if (nowAdmin.getId() > max) {
                max = nowAdmin.getId();
            }
        }
        return max;
    }

    public static int getNextAdminId() {
        return getMaxId() + 1;
    }

    public static void addAdmin(Admin newAdmin) {
        if (adminList == null) {
            makeAdminList(); // Pastikan list tidak null
        }
        adminList.addLast(newAdmin);
        saveAdminTXT();
    }


    public static void loadAdminTXT() {
        if (adminList == null) {
            makeAdminList();
        } else {
            adminList.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("src/data/admin.txt"))) {
            String line;
            boolean isFirstline = true;

            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstline) {
                    isFirstline = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;

                int ID = Integer.parseInt(parts[0].trim());
                String password = parts[1].trim();
                String name = parts[2].trim();

                Admin newAdmin = new Admin(ID, password, name);
                adminList.addLast(newAdmin);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void saveAdminTXT() {
        try {
            FileWriter myWriter = new FileWriter("src/data/admin.txt");
            myWriter.write("Admin ID | Password | Name\n");
            for (int i = 0; i < adminList.size(); i++) {
                Admin localAdmin = adminList.get(i);
                if (localAdmin != null) {
                    myWriter.write(localAdmin.getId() + " | " + localAdmin.getPassword() + " | " + localAdmin.getName() + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
