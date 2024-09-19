package ADMIN;

public class AdminManager {
    private AdminVar[] admins;
    private int count;

    public AdminManager(int size) {
        admins = new AdminVar[size];
        count = 0;
    }

    public boolean register(String username, String password) {
        for (int i = 0; i < count; i++) {
            if (admins[i].getUsername().equals(username)) {
                return false; 
            }
        }
        if (count < admins.length) {
            admins[count++] = new AdminVar(username, password);
            return true; 
        }
        return false; 
    }

    public boolean login(String username, String password) {
        for (int i = 0; i < count; i++) {
            if (admins[i].getUsername().equals(username) && admins[i].getPassword().equals(password)) {
                return true; 
            }
        }
        return false;
    }
}

