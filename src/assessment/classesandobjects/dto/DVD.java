package assessment.classesandobjects.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DVD {
    private String title;
    private Date releaseDate;
    //turn this to enum later
    private String rateMPAA;
    private String directorName;
    private String studio;
    private String userRating;

    public DVD(String initialTitle, Date initialReleaseDate, String initialRateMPAA,
               String initialDirectorName, String initialStudioName, String initialUserRating) {
        title = initialTitle;
        releaseDate = initialReleaseDate;
        rateMPAA = initialRateMPAA;
        directorName = initialDirectorName;
        studio = initialStudioName;
        userRating = initialUserRating;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getStudio() {
        return studio;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getRateMPAA() {
        return rateMPAA;
    }

    public String getReleaseDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        return dateFormat.format(releaseDate);
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setReleaseDate(Date newReleaseDate) {
        this.releaseDate = newReleaseDate;
    }

    public void setRateMPAA(String newRateMPAA) {
        this.rateMPAA = newRateMPAA;
    }

    public void setDirectorName(String newDirectorName) {
        this.directorName = newDirectorName;
    }

    public void setStudio(String newStudio) {
        this.studio = newStudio;
    }

    public void setUserRating(String newUserRating) {
        this.userRating = newUserRating;
    }


}
