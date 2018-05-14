package dk.adamino.rehabilitation.BE;

public class Exercise {
    public String mAmount;
    public String mTitle;
    public String mRepetitions;
    public String mDescription;

    public Exercise(String amount, String title, String repetitions, String description) {
        mAmount = amount;
        mTitle = title;
        mRepetitions = repetitions;
        mDescription = description;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getRepetitions() {
        return mRepetitions;
    }
}
