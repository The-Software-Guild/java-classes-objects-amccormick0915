package assessment.classesandobjects.ui;

import java.util.ArrayList;
import java.util.Date;

public interface LibraryIO {
    void print(String message);
    int readUserInputIntPositive(String prompt);
    int readUserInputIntPositiveRanged(String prompt, int min, int max);
    String readUserStringInput (String prompt);
    String readUserStringInputArrayRange (String prompt, ArrayList<String> choices);
    Date readUserDateInput(String s);
}
