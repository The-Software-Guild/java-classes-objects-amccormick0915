package assessment.ui;

import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryIOConsoleImp implements LibraryIO {
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public int readUserInputIntPositive(String prompt) {
        String userStringInput = readUserStringInput(prompt);
        int userIntInput = -1;
        boolean valid;
        do {
            try {
                userIntInput = Integer.parseInt(userStringInput);
                valid = true;
            } catch (Exception e) {
                valid = false;
                userStringInput = readUserStringInput("Invalid integer input. Please enter a whole number");
            }
        } while (!valid);
        return userIntInput;
    }

    @Override
    public int readUserInputIntPositiveRanged(String prompt, int min, int max) {
        int userIntInput = readUserInputIntPositive(prompt);
        boolean valid = false;
        do {
            if (userIntInput > max || userIntInput < min) {
                userIntInput = readUserInputIntPositive("Only a number from " + min + " to " + max + " are accepted. Please try again");
            } else {
                valid = true;
            }
        } while (!valid);
        return userIntInput;
    }

    @Override
    public String readUserStringInput(String prompt) {
        print(prompt);
        String userInput = (new Scanner(System.in)).nextLine();

        while (userInput.isBlank() || userInput.isEmpty()) {
            print("Empty Input not allowed. Please try again");
            userInput = (new Scanner(System.in)).nextLine();
        }
        return userInput;
    }

    @Override
    public String readUserStringInputArrayRange(String prompt, ArrayList<String> listOfChoices) {
        String initialUserInput = readUserStringInput(prompt);
        boolean valid = false;
        do {
            if (!listOfChoices.contains(initialUserInput)) {
                print("Invalid input, please try again");
                initialUserInput = readUserStringInput(prompt);
            } else {
                valid = true;
            }
        } while (!valid);
        return initialUserInput;
    }

    @Override
    public Date readUserDateInput(String prompt) {
        SimpleDateFormat formatter = (new SimpleDateFormat("dd/MMMM/yyyy", Locale.ENGLISH));
        String initialDateStringProvided;
        Date userDateFormatted;
        while (true) {
            initialDateStringProvided = readUserStringInput(prompt);
            try {
                userDateFormatted = formatter.parse(initialDateStringProvided);
                break;
            } catch (Exception error) {
                print("Invalid date input! Please try again! (Date format: 01/January/1999)");
            }
        }
        return userDateFormatted;
    }
}
