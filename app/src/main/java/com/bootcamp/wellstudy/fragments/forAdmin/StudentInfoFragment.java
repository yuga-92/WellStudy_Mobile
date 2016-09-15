package com.bootcamp.wellstudy.fragments.forAdmin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.adapters.ScoreListAdapter;
import com.bootcamp.wellstudy.adapters.SubjectListAdapter;
import com.bootcamp.wellstudy.api.OkHttp3Downloader;
import com.bootcamp.wellstudy.api.PicassoOkHttpClient;
import com.bootcamp.wellstudy.api.ServiceGenerator;
import com.bootcamp.wellstudy.api.WellStudyClient;
import com.bootcamp.wellstudy.model.Faculty;
import com.bootcamp.wellstudy.model.Group;
import com.bootcamp.wellstudy.model.Score;
import com.bootcamp.wellstudy.model.Student;
import com.bootcamp.wellstudy.model.Subject;
import com.bootcamp.wellstudy.model.TypeOfEducation;
import com.squareup.picasso.Picasso;
import static com.bootcamp.wellstudy.Constants.API_BASE_URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentInfoFragment extends Fragment {
    private SubjectListAdapter subjectListAdapter;
    private ScoreListAdapter scoreListAdapter;
    private Student student;
    private TextView _studentName;
    private String url;
    private TextView _studentEmail;
    private TextView _studentYearOfStudy;
    private TextView _typeOfEducation;
    private TextView _studentfaculty;
    private TextView _studentGroup;
    private Group studentGroup;
    private Faculty studentFaculty;
    private String ssoId;
    private ImageView imageView;
    private Picasso picasso;
    public StudentInfoFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        ssoId = bundle.getString("ssoId", null);
        PicassoOkHttpClient client = PicassoOkHttpClient.getInstance("admin", "abc125");
        OkHttpClient client2 = client.getService();
        picasso = new Picasso.Builder(getActivity())
                .downloader(new OkHttp3Downloader(client2))
                .build();
        if (ssoId != null) {
            getStudent();
        } else {
            System.out.println("SSO_NULL");
        }

        List <Subject> subjects = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            subjects.add(new Subject("Subject"+i," description"+i, i % 2 > 0));
        }
        List <Score> scores = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 42 ; i++) {
            scores.add(new Score("Lesson "+i,random.nextInt(4)+1, new Date()));
        }
        View rootView = inflater.inflate(R.layout.fragment_student_info, container, false);
        _studentName = (TextView) rootView.findViewById(R.id.studentName_textView);
        _studentEmail = (TextView) rootView.findViewById(R.id.studentEmail_textView);
        _studentYearOfStudy = (TextView) rootView.findViewById(R.id.student_yearOfStudy);
        _typeOfEducation = (TextView) rootView.findViewById(R.id.typeOfEducation_textView);
        _studentfaculty =  (TextView) rootView.findViewById(R.id.studentFacultyTextView);
        _studentGroup =  (TextView) rootView.findViewById(R.id.studentsGroup_textView);
        imageView =(ImageView) rootView.findViewById(R.id.imageView3);
        subjectListAdapter = new SubjectListAdapter(getActivity(), subjects);
        scoreListAdapter = new ScoreListAdapter(getActivity(), scores);
        ListView lvMain = (ListView) rootView.findViewById(R.id.listView);
        lvMain.setAdapter(subjectListAdapter);
        ListView lvScore = (ListView) rootView.findViewById(R.id.studentScores_listView);
        lvScore.setAdapter(scoreListAdapter);
        return rootView;
    }

    private void getStudent() {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Student> call;
            call = client.getStudent(ssoId);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
                    student = response.body();
                        System.out.println("########@@@@"+student.toString());
                        url=API_BASE_URL+"admin/imageDisplay?id="+student.getId();
                        System.out.println("#####"+url);
                        picasso.load(url).into(imageView);
                        getFacultyOfStudent();
                        getGroupOfStudent();
                        _studentName.setText(student.getFirstName()+" "+student.getLastName());
                        _typeOfEducation.setText(student.getTypeOfEducation().toString());
                        _studentEmail.setText(student.getEmail());
                        System.out.println("####year"+student.getYearOfStudy());
                        _studentYearOfStudy.setText(student.getYearOfStudy().toString());
                        _typeOfEducation.setText(TypeOfEducation.getType(student.getTypeOfEducation()));
                    } else if (response.code() == 401) {
                        System.out.println("##UNA###");
                    } else {
                        System.out.println("########@@@@");
                        // Handle other responses
                    }
                }
                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    System.out.println("Something went wrong: " + t.getMessage());
                }
            });
    }

    private void getFacultyOfStudent() {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Faculty> call;
            call = client.getFaculty(student.getFaculty());
            call.enqueue(new Callback<Faculty>() {
                @Override
                public void onResponse(Call<Faculty> call, Response<Faculty> response) {
                    if (response.isSuccessful()) {
                    studentFaculty = response.body();
                        _studentfaculty.setText(studentFaculty.getName());
                    } else if (response.code() == 401) {
                        System.out.println("##UNA###");
                    }
                }
                @Override
                public void onFailure(Call<Faculty> call, Throwable t) {
                    System.out.println("Something went wrong: " + t.getMessage());
                }
            });
    }

    private void getGroupOfStudent() {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Group> call;
        call = client.getGroup(student.getGroup());
        System.out.println("########GROUP"+student.getGroup());
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()) {
                    studentGroup = response.body();
                    System.out.println("########GROUP"+studentGroup.toString());
                    _studentGroup.setText(studentGroup.getName());
                } else if (response.code() == 401) {
                    System.out.println("##UNA###");
                } else {
                    // Handle other responses
                    System.out.println("#ELSE###");
                }
            }
            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                System.out.println("Something ssswent wrong: " + t.getMessage());
            }
        });
    }
}
