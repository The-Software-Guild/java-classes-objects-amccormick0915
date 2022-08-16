package assessment.classesandobjects.controller;

import assessment.classesandobjects.dto.DVD;
import assessment.classesandobjects.dao.*;
import assessment.classesandobjects.ui.*;

import java.io.IOException;
import java.util.LinkedList;

public class LibraryController {
    private final LibraryView view;
    private final LibraryDao dao;

    public LibraryController(LibraryView newView, LibraryDao newDao) throws IOException {
        view = newView;
        dao = newDao;
        dao.initialiseLibrary();
    }

    public void run()  {
        boolean continueMenu = true;
        int sessionCRUDType = 0;
        int userSessionChoice;
        try{
            while (continueMenu) {
                // Continues running the previous CRUD session ELSE new session
                if (sessionCRUDType > 0 && view.continueCRUDSession(sessionCRUDType)) {
                    userSessionChoice = sessionCRUDType;
                } else {
                    sessionCRUDType = 0;
                    userSessionChoice = view.printMenuAndGetUserChoice();
                }

                switch (userSessionChoice) {
                    case 1 -> showDVDsInLibrary();
                    case 2 -> {
                        sessionCRUDType = 2;
                        addDVDtoLibrary();
                    }
                    case 3 ->
                            // Remove DVD
                            sessionCRUDType = manipulateDVD(3);
                    case 4 ->
                            // Update DVD
                            sessionCRUDType = manipulateDVD(4);
                    case 5 ->
                            // View DVD
                            sessionCRUDType = manipulateDVD(5);
                    case 6 -> {
                        dao.saveLibrary();
                        continueMenu = false;
                    }
                    default -> unknownCommand();
                }
            }
        } catch (LibraryDaoException | IOException e){
            view.displayErrorMessage(e.getMessage());
        }
        exitMessage();
    }

    private int manipulateDVD(int sessionNumber) throws LibraryDaoException, IOException {
        String deleteOrUpdateOrSearch = (sessionNumber == 3) ?
                "delete" : ((sessionNumber == 4) ?
                "update" : "search");
        if (!view.checkIfDVDListIsEmpty(dao.getAllDVDs())) {
            DVD dvdChosen;
            showDVDsInLibrary();
            int searchDVDByIDorTitleorCancel = view.searchDVDByIDorByTitle(deleteOrUpdateOrSearch);
            if (searchDVDByIDorTitleorCancel == 1) {
                dvdChosen = getDVDByID();
            } else if (searchDVDByIDorTitleorCancel == 2) {
                dvdChosen = getDVDbyTitle();
            } else {
                return 0;
            }
            switch (deleteOrUpdateOrSearch) {
                case "delete" -> view.deletionResult(dao.removeDVD(dvdChosen));
                case "update" -> updateDVDdetails(dvdChosen);
                case "search" -> view.displayDVDdetailsAndConfirmDVDchosen(dvdChosen);
            }
            return deleteOrUpdateOrSearch.equals("delete") ? 3 : deleteOrUpdateOrSearch.equals("update") ? 4 : 5;
        }
        return 0;
    }

    private void updateDVDdetails(DVD dvdChosen) {
        boolean continueUpdatingDVD = true;
        if (view.displayDVDdetailsAndConfirmDVDchosen(dvdChosen)) {
            while (continueUpdatingDVD) {
                if (view.updateDVD(dvdChosen) && view.userConfirmYesOrNo("Would you like to continue updating the same DVD? (Y/N)")) {
                        view.displayDVDdetails(dvdChosen);
                } else {
                    break;
                }
            }
        }
    }

    private DVD getDVDByID() throws LibraryDaoException, IOException {
        return dao.getDVD(view.chooseDVDbyID(dao.getAllDVDs()));
    }

    private DVD getDVDbyTitle() throws LibraryDaoException, IOException {
        return dao.getDVD(view.userChooseByTitle(dao.getAllDVDTitles()));
    }

    private void showDVDsInLibrary() throws LibraryDaoException, IOException {
        view.displayBannerDisplayDVDs();
        LinkedList<DVD> dvds = dao.getAllDVDs();
        view.displayDVDList(dvds);
    }

    private void addDVDtoLibrary() throws LibraryDaoException, IOException {
        view.displayBannerAddDVD();
        DVD newDVD = view.getDVDdetails();
        while(true){
            if(dao.checkIfTitleExists(newDVD) && view.displayUnsuccessfulDVDaddition()){
                    newDVD.setTitle(view.setFailedDVDtitle());
            } else {
                break;
            }
        }
        dao.addDVD(newDVD);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
