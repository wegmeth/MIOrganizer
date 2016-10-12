package miorganizer.wegmeth.com.mioraganizer.helper;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import miorganizer.wegmeth.com.mioraganizer.models.Module;
import miorganizer.wegmeth.com.mioraganizer.models.Semester;

/**
 * Created by jan on 29.09.16.
 */
public class XMLDataManager {

    public List<Semester>  parse(InputStream in) {

        XmlPullParserFactory pullParserFactory;
        Semester semester = null;
        List<Semester> semesters = new ArrayList<Semester>();
        List<Module> modules = new ArrayList<Module>();
        Module module = null;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullParserFactory.newPullParser();

            InputStreamReader reader = new InputStreamReader(in);
            xmlPullParser.setInput(reader);

            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = "";
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        semesters = new ArrayList<Semester>();
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName() == null) break;

                        if (xmlPullParser.getName().equals("modul")) {

                            module = new Module();

                            module.setName(xmlPullParser.getAttributeValue("", "name"));

                            module.setCredits(Integer.valueOf(xmlPullParser.getAttributeValue("", "credits")));

                            module.setInstructor(xmlPullParser.getAttributeValue("", "dozent"));

                            module.setShortName(xmlPullParser.getAttributeValue("", "short_name"));

                            module.setRequiredPrac(xmlPullParser.getAttributeValue("", "praktikum").equals("1"));

                        } else if (xmlPullParser.getName().equals("description")) {

                            module.setDescription(xmlPullParser.nextText());

                        } else if (xmlPullParser.getName().equals("semester")) {

                            semester = new Semester();
                            semester.setId(Integer.valueOf(xmlPullParser.getAttributeValue("", "id")));

                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("modul")) {

                            semester.addModule(module);

                        } else if (xmlPullParser.getName().equals("semester")) {

                            semesters.add(semester);

                        }
                }
                eventType = xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            Log.e("XMLDataManager", "parse", e);
        } catch (IOException e) {
            Log.e("XMLDataManager", "parse", e);
        }

        return semesters;
    }

}
