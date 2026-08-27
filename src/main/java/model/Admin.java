package model;

public class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public void displayRole() {
        System.out.println("Role : Admin");
    }
}