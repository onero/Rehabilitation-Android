package dk.adamino.rehabilitation;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dk.adamino.rehabilitation.BLL.LoginManager;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Theories.class)
public class LoginManagerShould {

    private LoginManager mLoginManager;

    public LoginManagerShould() {
        mLoginManager = new LoginManager();
    }

    @Test
    public void validateEmailIsCorrect_with_validEmail() {
        String emailToValidate = "alh@rehab.dk";
        
        boolean result = mLoginManager.isEmailValid(emailToValidate);

        assertTrue(result);
    }

    @Test
    public void validateEmailIsIncorrect_with_emailMissingAddSign() {
        String emailToValidate = "alhrehab.dk";

        boolean emailIsValid = mLoginManager.isEmailValid(emailToValidate);

        assertFalse(emailIsValid);
    }

    @Test
    public void validateEmailIsIncorrect_with_emailMissingTopLevelDomain() {
        String emailToValidate = "alh@rehab";

        boolean emailIsValid = mLoginManager.isEmailValid(emailToValidate);

        assertFalse(emailIsValid);
    }

    @Test
    public void validateEmailIsIncorrect_with_missingTextBetweenAddAndTopLevelDomain() {
        String emailToValidate = "alh@.dk";

        boolean emailIsValid = mLoginManager.isEmailValid(emailToValidate);

        assertFalse(emailIsValid);
    }

    @DataPoints
    public static String[] emailsWithInvalidCharacters() {
        return new String[]
                {
                        "alh@@.dk",
                        "alh..dk",
                        "alh,.dk",
                        "alh@%¤&.dk",
                };
    }

    @Theory
    public void validateEmailIsIncorrect_with_invalidCharacters(String emailWithInvalidCharacters) {
        boolean emailIsValid = mLoginManager.isEmailValid(emailWithInvalidCharacters);

        assertFalse(emailIsValid);
    }
}