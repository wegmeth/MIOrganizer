package miorganizer.wegmeth.com.mioraganizer.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;
import miorganizer.wegmeth.com.mioraganizer.models.Group;

public class GroupAddActivity extends AppCompatActivity {

    TextView editName = null;
    TextView editDescription = null;
    Group group = null;
    SQLHelper helper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_add);

        editName = (TextView) findViewById(R.id.editGroupName);
        editDescription = (TextView) findViewById(R.id.editDescription);

        helper = new SQLHelper(getApplicationContext());


        Intent intent = getIntent();
        final long groupId = intent.getLongExtra("group_id", -1);

        if (groupId != -1) {


            String[] projection = {
                    Group._ID,
                    Group.COLUMN_NAME,
                    Group.COLUMN_NOTES,
            };
            String selection = Group._ID + " = ?";
            String[] selectionArgs = {"" + groupId};

            Cursor c = helper.getReadableDatabase().query(Group.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int credit = 0;
            while (!c.isAfterLast()) {
                group = new Group();

                group.setId(c.getLong(c.getColumnIndexOrThrow(Group._ID)));
                group.setName(c.getString(c.getColumnIndexOrThrow(Group.COLUMN_NAME)));
                group.setNotes(c.getString(c.getColumnIndexOrThrow(Group.COLUMN_NOTES)));

                c.moveToNext();
            }

            editDescription.setText(group.getNotes());
            editName.setText(group.getName());
        }


        Button btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues val = new ContentValues();
                val.put(Group.COLUMN_NAME, editName.getText().toString());
                val.put(Group.COLUMN_NOTES, editDescription.getText().toString());

                if(groupId != -1){
                    helper.getWritableDatabase().update(Group.TABLE_NAME, val, Group._ID + "=" + groupId, null);
                }else{
                    helper.getWritableDatabase().insert(Group.TABLE_NAME,"", val);
                }

                Intent intent = new Intent();
                intent.putExtra("tab",1);
                intent.setClass(GroupAddActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }

}
