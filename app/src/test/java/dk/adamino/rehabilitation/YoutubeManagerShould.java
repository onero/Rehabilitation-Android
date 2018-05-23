package dk.adamino.rehabilitation;

import org.junit.Test;

import dk.adamino.rehabilitation.BLL.YoutubeManager;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class YoutubeManagerShould {

    private YoutubeManager mYoutubeManager;

    public YoutubeManagerShould() {
        mYoutubeManager = new YoutubeManager();
    }

    @Test
    public void getCorrectId_From_HttpUrl() {
        String videoUrlToValidateAgainst = "http://www.youtube.com/watch?v=LXOaCkbt4lI";

        // Last part of url is always id of video
        String expectedResult = "LXOaCkbt4lI";

        String result = mYoutubeManager.getYoutubeIdFromUrl(videoUrlToValidateAgainst);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getCorrectId_From_HttpsUrl() {
        String videoUrlToValidateAgainst = "https://www.youtube.com/watch?v=LXOaCkbt4lI";

        // Last part of url is always id of video
        String expectedResult = "LXOaCkbt4lI";

        String result = mYoutubeManager.getYoutubeIdFromUrl(videoUrlToValidateAgainst);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getCorrectId_From_SharedUrlAtSpecificVideoPosition() {
        String videoUrlToValidateAgainst = "https://youtu.be/LXOaCkbt4lI?t=3m9s";

        // Last part of url is always id of video
        String expectedResult = "LXOaCkbt4lI";

        String result = mYoutubeManager.getYoutubeIdFromUrl(videoUrlToValidateAgainst);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getCorrectId_From_DkLocalizedUrl() {
        String videoUrlToValidateAgainst = "https://www.youtube.com/watch?v=LXOaCkbt4lI&gl=DK&hl=da";

        // Last part of url is always id of video
        String expectedResult = "LXOaCkbt4lI";

        String result = mYoutubeManager.getYoutubeIdFromUrl(videoUrlToValidateAgainst);

        assertEquals(expectedResult, result);
    }
}