package dk.adamino.rehabilitation.BE;
import java.util.List;

/**
 * Created by Adamino.
 */
public class RehabilitationPlan {
    public String uid;
    public String diagnosis;
    public String goal;
    public List<String> exerciseIds;
    public List<Milestone> mMilestones;
}
