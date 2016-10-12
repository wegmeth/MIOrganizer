package miorganizer.wegmeth.com.mioraganizer.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.listadapter.ModulListViewAdapter;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;
import miorganizer.wegmeth.com.mioraganizer.models.Module;

public class SemesterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_semester_modul);

        final ArrayList<Module> modules = new ArrayList<Module>();
        Intent intent = getIntent();
        int semesterId = intent.getIntExtra("semester_id", 0);

        String[] projection = {
                Module._ID,
                Module.COLUMN_NAME,
                Module.COLUMN_PRAKTIKUM_DONE,
                Module.COLUMN_PRAKTIKUM,
                Module.COLUMN_DONE,
                Module.COLUMN_SHORT_NAME
        };
        String selection = Module.COLUMN_SEMESTER_ID + " = ?";
        String[] selectionArgs = {"" + semesterId};

        final SQLHelper helper = new SQLHelper(getApplicationContext());

        Cursor c = helper.getReadableDatabase().query(Module.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        c.moveToFirst();

        int credit = 0;
        Module mod;
        while (!c.isAfterLast()) {
            mod = new Module();
            mod.setId(c.getLong(c.getColumnIndexOrThrow(Module._ID)));
            mod.setName(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_NAME)));
            mod.setShortName(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_SHORT_NAME)));
            mod.setRequiredPrac(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_PRAKTIKUM)) == 1);
            mod.setDone(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_DONE)) == 1);
            mod.setPraktikumDone(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_PRAKTIKUM_DONE)) == 1);

            modules.add(mod);

            c.moveToNext();
        }


        final ListView list = (ListView) findViewById(R.id.modulListView);


        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module mod = modules.get(position);

                Intent intent = new Intent();
                intent.setClass(SemesterDetailActivity.this, ModulDetailActivity.class);
                intent.putExtra("module_id", mod.getId());
                startActivity(intent);

                Log.i("click", mod.getName());
            }
        });

        list.setAdapter(new ModulListViewAdapter(getApplicationContext(), modules) {

            @Override
            public void doneChecked(boolean isChecked, Module mod) {

                ContentValues val = new ContentValues();
                val.put(Module.COLUMN_DONE, isChecked);

                helper.getWritableDatabase().update(Module.TABLE_NAME, val, Module._ID + "=" + mod.getId(), null);

            }

            @Override
            public void prakChecked(boolean isChecked, Module mod) {
                ContentValues val = new ContentValues();
                val.put(Module.COLUMN_PRAKTIKUM_DONE, isChecked);

                helper.getWritableDatabase().update(Module.TABLE_NAME, val, Module._ID + "=" + mod.getId(), null);
            }
        });


    }

}
