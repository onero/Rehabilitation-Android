package dk.adamino.rehabilitation.BLL;

/**
 * Created by Adamino.
 */
public class LoginManager {

    /**
     * Check provided email validity
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Check password validity
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}
