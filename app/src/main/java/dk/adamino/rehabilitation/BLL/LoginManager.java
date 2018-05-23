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
    public boolean isEmailValid(String email) {
        return email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,63})+$");
    }

    /**
     * Check password validity
     *
     * @param password
     * @return
     */
    public boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}
