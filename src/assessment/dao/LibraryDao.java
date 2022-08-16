package assessment.dao;

import assessment.dto.DVD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public interface LibraryDao {

    DVD addDVD(DVD newDVD) throws LibraryDaoException, IOException;

    LinkedList<DVD> getAllDVDs() throws LibraryDaoException, IOException;

    DVD getDVD(String titleDVD) throws LibraryDaoException, IOException;

    DVD getDVD(Integer dvdNumber) throws LibraryDaoException, IOException;

    ArrayList<String> getAllDVDTitles();

    boolean removeDVD(DVD dvdChosen) throws LibraryDaoException, IOException;

    void initialiseLibrary() throws IOException;

    void saveLibrary() throws LibraryDaoException, IOException;

    boolean checkIfTitleExists(DVD currentDVD);
}
