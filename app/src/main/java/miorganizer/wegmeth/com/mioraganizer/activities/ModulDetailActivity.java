package miorganizer.wegmeth.com.mioraganizer.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;
import miorganizer.wegmeth.com.mioraganizer.models.Module;

public class ModulDetailActivity extends AppCompatActivity {

    private SQLHelper helper = null;
    private EditText note;

    long moduleId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);
        helper = new SQLHelper(getApplicationContext());

        Intent intent = getIntent();
         moduleId = intent.getLongExtra("module_id", 0);

        String[] projection = {
                Module._ID,
                Module.COLUMN_NAME,
                Module.COLUMN_PRAKTIKUM_DONE,
                Module.COLUMN_PRAKTIKUM,
                Module.COLUMN_DONE,
                Module.COLUMN_SHORT_NAME,
                Module.COLUMN_DESCRIPTION,
                Module.COLUMN_INSTRUCTOR,
                Module.COLUMN_NOTE
        };
        String selection = Module._ID + " = ?";
        String[] selectionArgs = {"" + moduleId};

        final SQLHelper helper = new SQLHelper(getApplicationContext());

        Cursor c = helper.getReadableDatabase().query(Module.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        c.moveToFirst();

        int credit = 0;

        Module mod = null;

        while (!c.isAfterLast()) {
            mod = new Module();

            mod.setNote(c.getDouble(c.getColumnIndexOrThrow(Module.COLUMN_NOTE)));
            mod.setName(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_NAME)));
            mod.setShortName(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_SHORT_NAME)));
            mod.setRequiredPrac(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_PRAKTIKUM)) == 1);
            mod.setDone(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_DONE)) == 1);
            mod.setPraktikumDone(c.getInt(c.getColumnIndexOrThrow(Module.COLUMN_PRAKTIKUM_DONE)) == 1);
            mod.setInstructor(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_INSTRUCTOR)));
            mod.setDescription(c.getString(c.getColumnIndexOrThrow(Module.COLUMN_DESCRIPTION)));

            c.moveToNext();
        }

        TextView txtName = (TextView) findViewById(R.id.name);
        txtName.setText(mod.getName());

        TextView txtInstructor = (TextView) findViewById(R.id.instructor);
        txtInstructor.setText(mod.getInstructor());

        TextView txtDescription = (TextView) findViewById(R.id.description);
        txtDescription.setText(mod.getDescription());

        txtDescription.setMovementMethod(new ScrollingMovementMethod());

         note = (EditText) findViewById(R.id.editNote);
        if(mod.getNote() != 0.0)
            note.setText(mod.getNote()+"");
        note.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ContentValues val = new ContentValues();
                val.put(Module.COLUMN_DONE, true);
                val.put(Module.COLUMN_PRAKTIKUM_DONE,true);
                val.put(Module.COLUMN_NOTE, note.getText().toString());

                helper.getWritableDatabase().update(Module.TABLE_NAME, val, Module._ID + "=" + moduleId, null);

            }

        });

    }

    @Override
    protected void onPause() {

        super.onPause();

        ContentValues val = new ContentValues();

        val.put(Module.COLUMN_DONE, true);
        val.put(Module.COLUMN_PRAKTIKUM_DONE,true);
        val.put(Module.COLUMN_NOTE, note.getText().toString());

        helper.getWritableDatabase().update(Module.TABLE_NAME, val, Module._ID + "=" + moduleId, null);

    }
}
