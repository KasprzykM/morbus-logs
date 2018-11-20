package symbiosproduction.com.morbuslogs.database.model.symptoms.pain;

public enum PainType {

    ACUTE_PAIN("Acute pain"),
    CHRONIC_PAIN("Chronic pain"),
    BREAKTHROUGH_PAIN("Breakthrough pain"),
    BONE_PAIN("Bone pain"),
    SOFT_TISSUE_PAIN("Soft tissue pain"),
    PHANTOM_PAIN("Phantom pain");

    private final String painType;

    PainType(String painType) {
        this.painType = painType;
    }

    @Override
    public String toString() {
        return painType;
    }
}
