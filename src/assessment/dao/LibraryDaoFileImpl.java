package assessment.dao;

import assessment.dto.DVD;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryDaoFileImpl implements LibraryDao {
    public static final String ROSTER_FILE = "roster.txt";
    public static final String DELIMITER = "::";
    private LinkedList<DVD> dvds = new LinkedList<>();

    @Override
    public DVD addDVD(DVD newDVD) throws LibraryDaoException, IOException {
        dvds.add(newDVD);
        writeRoster();
        return newDVD;
    }

    @Override
    public LinkedList<DVD> getAllDVDs() {
        return dvds;
    }

    @Override
    public ArrayList<String> getAllDVDTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (DVD currentDVD : dvds) {
            titles.add(currentDVD.getTitle());
        }
        return titles;
    }

    @Override
    public boolean removeDVD(DVD dvdChosen) {
        return dvds.remove(dvdChosen);
    }

    @Override
    public DVD getDVD(String titleDVD) {
        for (DVD currentDVD : dvds) {
            if (currentDVD.getTitle().equals(titleDVD)) {
                return currentDVD;
            }
        }
        return null;
    }

    @Override
    public DVD getDVD(Integer dvdNumber) {
        return dvds.get(dvdNumber);
    }

    @Override
    public void initialiseLibrary() throws IOException {
        loadRoster();
    }

    @Override
    public void saveLibrary() throws LibraryDaoException, IOException {
        writeRoster();
    }

    @Override
    public boolean checkIfTitleExists(DVD currentDVD){
        for(DVD dvdInSystem: dvds){
            if(dvdInSystem.getTitle().equals(currentDVD.getTitle())){
                return true;
            }
        }
        return false;
    }

    private String marshallDVD(DVD aDVD) {
        String dvdAsText = aDVD.getTitle() + DELIMITER;
        dvdAsText += aDVD.getReleaseDate() + DELIMITER;
        dvdAsText += aDVD.getRateMPAA() + DELIMITER;
        dvdAsText += aDVD.getDirectorName() + DELIMITER;
        dvdAsText += aDVD.getStudio() + DELIMITER;
        dvdAsText += aDVD.getUserRating();
        return dvdAsText;
    }

    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        return new DVD(
                dvdTokens[0],
                dateFormatter(dvdTokens[1]),
                dvdTokens[2],
                dvdTokens[3],
                dvdTokens[4],
                dvdTokens[5]);
    }

    private Date dateFormatter(String datePulled) {
        SimpleDateFormat formatter = (new SimpleDateFormat("dd/MMMM/yyyy", Locale.ENGLISH));
        Date userDateFormatted;
        try {
            userDateFormatted = formatter.parse(datePulled);
        } catch (Exception error) {
            System.out.println("Invalid date input! Please try again! (Date format: 01/January/1999)");
            userDateFormatted = null;
        }
        return userDateFormatted;
    }

    private void loadRoster() throws IOException {
        Scanner scanner;
        File rosterFile = new File(ROSTER_FILE);
        rosterFile.createNewFile();
        scanner = new Scanner(
                new BufferedReader(
                        new FileReader(ROSTER_FILE)));
        String currentLine;
        DVD currentDVD;
        LinkedList<DVD> currentDVDs = new LinkedList<>();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);
            currentDVDs.add(currentDVD);
        }
        dvds = currentDVDs;
        scanner.close();
    }

    private void writeRoster() throws LibraryDaoException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            throw new LibraryDaoException("Could not save student data.", e);
        }
        String dvdAsText;
        List<DVD> studentList = this.getAllDVDs();
        for (DVD currentStudent : studentList) {
            dvdAsText = marshallDVD(currentStudent);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
        loadRoster();
    }

}
