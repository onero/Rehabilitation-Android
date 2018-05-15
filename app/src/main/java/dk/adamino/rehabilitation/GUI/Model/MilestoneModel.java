package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.BE.Visit;

/**
 * Created by Adamino.
 */
public class MilestoneModel {

    private static MilestoneModel sInstance;

    private Milestone mCurrentMileStone;
    private Visit mCurrentVisit;

    private MilestoneModel() {
    }

    public static MilestoneModel getInstance() {
        if (sInstance == null) {
            sInstance = new MilestoneModel();
        }
        return sInstance;
    }

    public Milestone getCurrentMileStone() {
        return mCurrentMileStone;
    }

    public void setCurrentMileStone(Milestone currentMileStone) {
        mCurrentMileStone = currentMileStone;
    }

    public Visit getCurrentVisit() {
        return mCurrentVisit;
    }

    public void setCurrentVisit(Visit currentVisit) {
        mCurrentVisit = currentVisit;
    }
}
