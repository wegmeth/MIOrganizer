package miorganizer.wegmeth.com.mioraganizer.models;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class Semester implements BaseColumns{

    public static final String TABLE_NAME = "semester";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMBER = "number";

    public Semester(){
        modules = new ArrayList<Module>();
    }

    private int id;

    private List<Module> modules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module m){
        this.modules.add(m);
    }
}
