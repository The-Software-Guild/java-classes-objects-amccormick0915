package assessment.ui;

import assessment.dto.DVD;

import java.util.*;

public class LibraryView {
    private final LibraryIO io;
    private final ArrayList<String> yesORno = new ArrayList<>(List.of("Y", "N", "y", "n"));
    private final ArrayList<String> ratingMPAA = new ArrayList<>(List.of("G", "PG", "PG-13", "R", "NC-17"));

    public LibraryView(LibraryIO newIO) {
        io = newIO;
    }

    public int printMenuAndGetUserChoice() {
        io.print("\n~~~~~~~ Welcome to the DVD Library! ~~~~~~~\n~~~~~ Choose your options from below! ~~~~~");
        //enable user to search by DVD title
        String menu = ("""
                        \t 1 - Show DVD Collection
                        \t 2 - Add DVD to collection
                        \t 3 - Remove DVD from Collection
                        \t 4 - Update DVD Information
                        \t 5 - Choose DVD to View
                        \t 6 - Exit""");
        return io.readUserInputIntPositiveRanged(menu, 1, 6);
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command");
    }

    public void displayExitBanner() {
        io.print("Closing program, have a good day~!");
    }

    public void displayBannerAddDVD( ) {
        io.print("==== Please add the following information ====");
    }

    public void displayBannerDisplayDVDs( ) {
        io.print("=== Displaying current DVDs in the system ===");
    }

    public void displayDVDList(LinkedList<DVD> dvdList) {
        if (dvdList.isEmpty()) {
            io.print("No DVDs in the System!");
        }
        int count = 1;
        for (DVD currentDVD : dvdList) {
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format("         #%1$s: %2$s", count, currentDVD.getTitle());
            io.print(sbuf.toString());
            count++;
        }
    }

    public DVD getDVDdetails() {
        String title = io.readUserStringInput("Please enter DVD Title");
        Date releaseDate = io.readUserDateInput("Please enter DVD release date (Format: 25/January/1999)");
        String rateMPAA = io.readUserStringInputArrayRange("Please enter MPAA rating (G, PG, PG-13, R, NC-17)", ratingMPAA);
        String directorName = io.readUserStringInput("Please enter DVD director's name");
        String studio = io.readUserStringInput("Please enter DVD studio name");
        String userRating = io.readUserStringInput("Please enter DVD review or note");
        return new DVD(title, releaseDate, rateMPAA, directorName, studio, userRating);
    }


    public boolean continueCRUDSession(int currentSession) {
        String prompt = switch (currentSession) {
            case 2 -> "adding";
            case 3 -> "deleting";
            case 4 -> "updating";
            case 5 -> "viewing";
            default -> "manipulating";
        };
        return (userConfirmYesOrNo("\nWould you like to continue " + prompt + " any DVDs? (Y/N)"));
    }

    public int searchDVDByIDorByTitle(String type) {
        int userChoice = io.readUserInputIntPositiveRanged("\nHow would you like to " + type + " DVD by?\n1: by ID    2: by DVD Title    3: Cancel and return to Menu", 1, 3);
        if (userChoice == 1) {
            return 1;
        } else if (userChoice == 2) {
            return 2;
        } else {
            io.print(".................................. RETURNING TO MAIN MENU ..............................");
            return 3;
        }
    }

    public String userChooseByTitle(ArrayList<String> allDVDtitles) {
        return io.readUserStringInputArrayRange("Choose DVD title (Case sensitive)", allDVDtitles);
    }

    public void deletionResult(boolean result) {
        if (!result) {
            io.print("Deletion FAILED. No such DVD exists.");
        } else {
            io.print("DVD Deleted Successfully");
        }
    }

    public boolean checkIfDVDListIsEmpty(LinkedList<DVD> allDVDs) {
        if (allDVDs.isEmpty()) {
            io.print("""
                    ------ DVD LIST IS EMPTY -----
                    ---- returning to Main Menu ----
                    """);
            return true;
        }
        return false;
    }

    public int chooseDVDbyID(LinkedList<DVD> allDVDs) {
        int userChoice = 0;
        if (!allDVDs.isEmpty()) {
            userChoice = io.readUserInputIntPositiveRanged("Please choose DVD ID", 1, allDVDs.size());
        }
        return (userChoice - 1);
    }

    public boolean displayDVDdetailsAndConfirmDVDchosen(DVD dvdChosen) {
        if (userConfirmYesOrNo("Is \"" + dvdChosen.getTitle() + "\" the right DVD (Y/N)")) {
            displayDVDdetails(dvdChosen);
            return true;
        }
        return false;
    }

    public void displayDVDdetails(DVD dvdChosen) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        formatter.format("    Title:         %1$s %n" +
                        "    Release Date:  %2$s %n" +
                        "    MPAA rate:     %3$s %n" +
                        "    Director:      %4$s %n" +
                        "    Studio:        %5$s %n" +
                        "    User Rating:   %6$s",
                dvdChosen.getTitle(),
                dvdChosen.getReleaseDate(),
                dvdChosen.getRateMPAA(),
                dvdChosen.getDirectorName(),
                dvdChosen.getStudio(),
                dvdChosen.getUserRating());
        io.print(stringBuilder.toString());
    }

    public boolean userConfirmYesOrNo(String prompt) {
        String userChoice = io.readUserStringInputArrayRange(prompt, yesORno);
        return userChoice.equalsIgnoreCase("y");
    }

    public boolean updateDVD(DVD dvdChosen) {
        int dataToUpdate = io.readUserInputIntPositiveRanged("\nChoose which data to update:\n1: Title   2: Release Date  3: MPAA rate   4: Director   5: Studio   6: User Rating   7: Cancel and Return to Main Menu", 1, 7);

        switch (dataToUpdate) {
            case 1:
                dvdChosen.setTitle(io.readUserStringInput("What is the new title?"));
                break;
            case 2:
                dvdChosen.setReleaseDate(io.readUserDateInput("What is the new date of release?"));
                break;
            case 3:
                dvdChosen.setRateMPAA(io.readUserStringInputArrayRange("What is the new MPAA rating?", ratingMPAA));
                break;
            case 4:
                dvdChosen.setDirectorName(io.readUserStringInput("What is the new director name?"));
                break;
            case 5:
                dvdChosen.setStudio(io.readUserStringInput("What is the new studio name?"));
                break;
            case 6:
                dvdChosen.setUserRating(io.readUserStringInput("What is the new user rating?"));
                break;
            case 7:
                return false;
        }
        return true;
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public boolean displayUnsuccessfulDVDaddition() {
        io.print("INVALID TITLE! Please set a different Title to add it to the Library!");
        return userConfirmYesOrNo("Would you like to change TITLE of DVD? (Y/N)");
    }
    public String setFailedDVDtitle(){
        return io.readUserStringInput("Please enter new Title");
    }
}
