package miorganizer.wegmeth.com.mioraganizer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.activities.SemesterDetailActivity;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;


public class SemesterFragment extends Fragment {

    public SemesterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_semester, container, false);

        Map<Integer, View> semesterButtons = new HashMap<Integer, View>();
        semesterButtons.put(1,fragment.findViewById(R.id.semester1));
        semesterButtons.put(2,fragment.findViewById(R.id.semester2));
        semesterButtons.put(3,fragment.findViewById(R.id.semester3));
        semesterButtons.put(4,fragment.findViewById(R.id.semester4));
        semesterButtons.put(5,fragment.findViewById(R.id.semester5));
        semesterButtons.put(6,fragment.findViewById(R.id.semester6));

        SQLHelper helper = new SQLHelper(getActivity().getApplicationContext());




        for (final Map.Entry<Integer, View> pair : semesterButtons.entrySet()) {

            pair.getValue().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SemesterDetailActivity.class);
                    intent.putExtra("semester_id",pair.getKey());
                    startActivity(intent);

                    return false;
                }
            });

        }

        return fragment;
    }
}
