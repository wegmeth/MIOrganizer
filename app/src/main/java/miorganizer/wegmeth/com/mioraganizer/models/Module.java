package miorganizer.wegmeth.com.mioraganizer.models;

import android.provider.BaseColumns;

public class Module implements BaseColumns {

    public static final String TABLE_NAME = "module";

    public static final String COLUMN_SEMESTER_ID = "semesterid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SHORT_NAME = "shortname";
    public static final String COLUMN_CREDITS = "credits";
    public static final String COLUMN_INSTRUCTOR = "instructor";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRAKTIKUM = "praktikum";
    public static final String COLUMN_DONE = "done";
    public static final String COLUMN_PRAKTIKUM_DONE = "praktikumdone";
    public static final String COLUMN_NOTE = "note";

    private long id;
    private double note;

    private long semesterId;
    private String name;
    private String shortName;
    private int credits;
    ;
    private String instructor;
    private String description;
    private boolean requiredPrac;

    private boolean done = false;
    private boolean praktikum_done = false;

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPraktikum_done(boolean praktikum_done) {
        this.praktikum_done = praktikum_done;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isPraktikum_done() {
        return praktikum_done;
    }

    public void setPraktikumDone(boolean praktikum_done) {
        this.praktikum_done = praktikum_done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequiredPrac() {
        return requiredPrac;
    }

    public void setRequiredPrac(boolean requiredPrac) {
        this.requiredPrac = requiredPrac;
    }
}
