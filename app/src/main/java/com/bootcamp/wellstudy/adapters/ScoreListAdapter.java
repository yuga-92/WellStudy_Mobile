package com.bootcamp.wellstudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.model.Score;

import java.util.List;

public class ScoreListAdapter extends BaseAdapter {

    private Context mContext;
    private TextView mark;
    private TextView lesson;
    private TextView date;
    private List<Score> scores;


    public ScoreListAdapter(Context mContext, List<Score> scores) {
        this.mContext = mContext;
        this.scores = scores;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Score scoreItem= scores.get(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.score_of_student_list_view_item, null);
//            mark = (TextView) view.findViewById(R.id.mark_textView);
//            lesson = (TextView) view.findViewById(R.id.lesson_textView);
//            date = (TextView) view.findViewById(R.id.date_textView);
        }
        mark = (TextView) view.findViewById(R.id.mark_textView);
        lesson = (TextView) view.findViewById(R.id.lesson_textView);
        date = (TextView) view.findViewById(R.id.date_textView);
        date = (TextView) view.findViewById(R.id.date_textView);

        mark.setText(scoreItem.getMark().toString());
        lesson.setText(scoreItem.getLeson());
        date.setText(scoreItem.getDate().toString());
        return view;
    }

}
