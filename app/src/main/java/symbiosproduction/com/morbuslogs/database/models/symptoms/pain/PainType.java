package symbiosproduction.com.morbuslogs.database.models.symptoms.pain;

public enum PainType {

    ACUTE("Acute"),
    CHRONIC("Chronic"),
    BREAKTHROUGH("Breakthrough"),
    BONE("Bone"),
    SOFT_TISSUE("Soft tissue"),
    NERVE("Nerve"),
    REFERRED("Referred"),
    PHANTOM("Phantom");

    private final String painType;

    PainType(String painType) {
        this.painType = painType;
    }

    @Override
    public String toString() {
        return painType;
    }
}
