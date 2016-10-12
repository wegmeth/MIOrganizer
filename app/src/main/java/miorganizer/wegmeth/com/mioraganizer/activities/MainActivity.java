package miorganizer.wegmeth.com.mioraganizer.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.helper.Downloader;
import miorganizer.wegmeth.com.mioraganizer.helper.MainOnTabSelectedListener;
import miorganizer.wegmeth.com.mioraganizer.helper.PagerAdapter;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;
import miorganizer.wegmeth.com.mioraganizer.helper.XMLDataManager;
import miorganizer.wegmeth.com.mioraganizer.models.Module;
import miorganizer.wegmeth.com.mioraganizer.models.Semester;

public class MainActivity extends AppCompatActivity {

    public static final String XML_URL = "http://wegmeth.com/Medieninformatik.xml";
    private SQLHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        helper = new SQLHelper(this);

        fillData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }

    private void iniGUI() {

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        int loadTab = intent.getIntExtra("tab", -1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().

                setText("Semester")

        );

        tabLayout.addTab(tabLayout.newTab().

                setText("Gruppen")

        );

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new MainOnTabSelectedListener(viewPager));

        if (loadTab != -1)
            viewPager.setCurrentItem(loadTab);

    }

    private void fillData() {

        try {

            getApplicationContext().openFileInput("mi.xml");
            iniGUI();

        } catch (FileNotFoundException e) {

            Downloader downloader = new Downloader(getApplicationContext()) {

                @Override
                protected void onPostExecute(Object o) {
                    try {

                        InputStream in = getApplicationContext().openFileInput("mi.xml");
                        XMLDataManager manager = new XMLDataManager();
                        List<Semester> list = manager.parse(in);

                        for (Semester semester : list) {

                            ContentValues val = new ContentValues();
                            val.put(Semester.COLUMN_NUMBER, semester.getId());


                            long id = helper.getWritableDatabase().insert(Semester.TABLE_NAME, null, val);

                            for (Module module : semester.getModules()) {

                                val = new ContentValues();
                                val.put(Module.COLUMN_CREDITS, module.getCredits());
                                val.put(Module.COLUMN_DESCRIPTION, module.getDescription());
                                val.put(Module.COLUMN_DONE, false);
                                val.put(Module.COLUMN_INSTRUCTOR, module.getInstructor());
                                val.put(Module.COLUMN_NAME, module.getName());
                                val.put(Module.COLUMN_PRAKTIKUM_DONE, false);
                                val.put(Module.COLUMN_PRAKTIKUM, module.isRequiredPrac());
                                val.put(Module.COLUMN_SEMESTER_ID, id);
                                val.put(Module.COLUMN_SHORT_NAME, module.getShortName());

                                helper.getWritableDatabase().insert(Module.TABLE_NAME, null, val);

                            }

                        }

                    } catch (Exception e) {
                        Log.e("Read", "", e);
                    }

                    iniGUI();
                }
            };

            downloader.execute();
        }

    }
}