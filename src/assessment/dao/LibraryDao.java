package assessment.dao;

import assessment.dto.DVD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public interface LibraryDao {


        /**
         * Adds the given Student to the roster and associates it with the given
         * student id. If there is already a student associated with the given
         * student id it will return that student object, otherwise it will
         * return null.
         *
         * @return the Student object previously associated with the given
         * student id if it exists, null otherwise
         */
        DVD addDVD(DVD newDVD) throws LibraryDaoException, IOException;

        /**
         * Returns a List of all students in the roster.
         *
         * @return List containing all students in the roster.
         */
        LinkedList< DVD> getAllDVDs() throws LibraryDaoException, IOException;

        /**
         * Returns the student object associated with the given student id.
         * Returns null if no such student exists
         *
         * @param titleDVD ID of the student to retrieve
         * @return the Student object associated with the given student id,
         * null if no such student exists
         */
        DVD getDVD(String titleDVD) throws LibraryDaoException, IOException;

        DVD getDVD(Integer dvdNumber) throws LibraryDaoException, IOException;
        /**
         * Removes from the roster the student associated with the given id.
         * Returns the student object that is being removed or null if
         * there is no student associated with the given id
         *
         * @return Student object that was removed or null if no student
         * was associated with the given student id
         */

         ArrayList<String> getAllDVDTitles();
        boolean removeDVD(DVD dvdChosen) throws LibraryDaoException, IOException;
        void initialiseLibrary() throws IOException;
        void saveLibrary() throws LibraryDaoException, IOException;
        boolean checkIfTitleExists(DVD currentDVD);
}
