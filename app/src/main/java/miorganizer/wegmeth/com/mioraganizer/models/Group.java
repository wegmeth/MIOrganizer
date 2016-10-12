package miorganizer.wegmeth.com.mioraganizer.models;

import android.provider.BaseColumns;

public class Group  implements BaseColumns {

    public static final String TABLE_NAME = "notegroup";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NOTES= "notes";

    private String name;
    private String notes;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
