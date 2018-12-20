package symbiosproduction.com.morbuslogs.database.models.patient;

public enum Gender {


    MALE("Male"),
    FEMALE("Female");

    /* Format genderTest = new MessageFormat(getResources().getString(R.string.gender_patient));
            Gender gender = Gender.MALE;
            test.put("gender", genderTest.format(new Object[]{gender.ordinal()}));
            */

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }

}
