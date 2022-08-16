import assessment.controller.LibraryController;
import assessment.dao.LibraryDaoFileImpl;
import assessment.ui.LibraryIOConsoleImp;
import assessment.ui.LibraryView;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        LibraryController library = new LibraryController(new LibraryView(new LibraryIOConsoleImp()), new LibraryDaoFileImpl());
        library.run();
    }
}
