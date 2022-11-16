package Method;

import javax.swing.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class FrontEndInteraction {

    public static Scanner s;

    //Display the menu of each page
    public int displayMenu(String header, String[] options, String prompt) {
        System.out.println("\n" + header);

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        int selection = getInt(prompt);

        if (selection > 0 && selection <= options.length) {
            return selection;
        } else {
            System.out.println("Invalid menu selection");
            return -1;
        }
    }

    //get integer from user input , call getString(
    public int getInt(String prompt) {
        int response;

        String str = getString(prompt);
        try {
            response = Integer.parseInt(str);
            return response;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input - number required");
            return -1;
        }

    }

    public String getString(String prompt) {
        return getString(prompt, false);
    }

    public String getString(String prompt, boolean emptyAllowed) {
        if (s == null)
            s = new Scanner(System.in);

        System.out.println(prompt);
        String response = s.nextLine();

        if ("".equals(response) && !emptyAllowed) {
            response = null;
            System.out.println("Blank entry is not allowed.");
        }


        return response;
    }
}
