package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

public class FileUserDAO implements UserDao {

    private final String filename;
    private final List<User> users;

    public FileUserDAO(String filename) {
        this.filename = filename;
        users = new ArrayList<User>();
        readUsers();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.append(user.getUsername() + " " + user.getPassword() + '\n');
            users.add(user);
            writer.close();
        } catch (IOException ex) {
            System.exit(-1);
        }
    }

    private Scanner createScanner() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found, exiting");
            System.exit(-1);
        }
        return sc;
    }

    private void readUsers() {
        Scanner sc = createScanner();
        if (sc != null) {
            while (sc.hasNextLine()) {
                String rivi = sc.nextLine();
                String[] s = rivi.split(" ");
                users.add(new User(s[0], s[1]));
            }
        }
    }

}
