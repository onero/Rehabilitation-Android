package dk.adamino.rehabilitation.Callbacks;

import java.util.List;

import dk.adamino.rehabilitation.BE.Milestone;

/**
 * Created by Adamino.
 */
public interface IFirestoreMilestoneCallback {

    /**
     * Handle milestones found on Firestore
     * @param milestones
     */
    void onMilestoneResponse(List<Milestone> milestones);
}
