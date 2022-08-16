package assessment.classesandobjects;

import assessment.classesandobjects.controller.LibraryController;
import assessment.classesandobjects.dao.LibraryDaoException;
import assessment.classesandobjects.dao.LibraryDaoFileImpl;
import assessment.classesandobjects.ui.LibraryIOConsoleImp;
import assessment.classesandobjects.ui.LibraryView;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        LibraryController library = new LibraryController(new LibraryView(new LibraryIOConsoleImp()), new LibraryDaoFileImpl());
        library.run();
    }
}
