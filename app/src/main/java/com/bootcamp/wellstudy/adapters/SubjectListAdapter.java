package com.bootcamp.wellstudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.model.Subject;

import java.util.List;

public class SubjectListAdapter extends BaseAdapter {

    private Context mContext;

    private List<Subject> subjects;


    public SubjectListAdapter(Context mContext, List<Subject> subjects) {
        this.mContext = mContext;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Subject subjectItem = subjects.get(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.student_fsubjects_list_view_item, null);
        }
        TextView subjectName = (TextView) view.findViewById(R.id.subjectName_textview);
        ImageView subjectActivity = (ImageView) view.findViewById(R.id.subjectActivityStatusImage);
        subjectName.setText(subjectItem.getSubjectName());
        if (subjectItem.getActive()) {
            subjectActivity.setImageResource(R.drawable.active);
        } else {
            subjectActivity.setImageResource(R.drawable.inactive);
        }
        return view;
    }

}
