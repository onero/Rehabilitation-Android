package dk.adamino.rehabilitation.BE;

public class ExerciseInfo {
    public String mAmount;
    public String mTitle;
    public String mRepetitions;

    public ExerciseInfo (String amount, String title, String repetitions) {
        mAmount = amount;
        mTitle = title;
        mRepetitions = repetitions;
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
