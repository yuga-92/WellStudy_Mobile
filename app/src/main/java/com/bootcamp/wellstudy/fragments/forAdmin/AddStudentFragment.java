package com.bootcamp.wellstudy.fragments.forAdmin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.api.ServiceGenerator;
import com.bootcamp.wellstudy.api.WellStudyClient;
import com.bootcamp.wellstudy.model.Faculty;
import com.bootcamp.wellstudy.model.Group;
import com.bootcamp.wellstudy.model.StudentDto;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentFragment extends Fragment {
    private EditText _ssoid;
    private EditText _firstName;
    private EditText _lastName;
    private EditText _password;
    private EditText _email;
    private Spinner facultySpinner;
    private Spinner groupSpinner;
    private Integer typeOfEducationId = 2;
    private Integer studentFacultyId = 1;
    private Integer studentGroupId = 1;
    private ArrayAdapter<Faculty> faculties_adapter;
    private ArrayAdapter<Group> groups_adapter;
    private List<Faculty> faculties;
    private List<Group> groups;

    public AddStudentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_student, container, false);
        ButterKnife.bind(getActivity());
        facultySpinner = (Spinner) rootView.findViewById(R.id.facultySpinner);
        groupSpinner = (Spinner) rootView.findViewById(R.id.groupsOfFacultySpinner);
        Button saveStudentButton = (Button) rootView.findViewById(R.id.saveStudent_button);
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.offSite) {
                    typeOfEducationId = 1;
                } else if (checkedId == R.id.radioOnSiteCommertional) {
                    typeOfEducationId = 2;
                } else {
                    typeOfEducationId = 3;
                }
            }
        });

        _ssoid = (EditText) rootView.findViewById(R.id.input_ssoId);
        _firstName = (EditText) rootView.findViewById(R.id.input_firstName);
        _lastName = (EditText) rootView.findViewById(R.id.input_lastName);
        _password = (EditText) rootView.findViewById(R.id.input_password);
        _email = (EditText) rootView.findViewById(R.id.input_email);
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                Faculty faculty = (Faculty) parent.getItemAtPosition(pos);
                studentFacultyId = faculty.getId();
                getGroupsOfFaculty(studentFacultyId);
            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });

        groupSpinner = (Spinner) rootView.findViewById(R.id.groupsOfFacultySpinner);
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                Group group = (Group) parent.getItemAtPosition(pos);
                studentGroupId = group.getId();
            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
        saveStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.saveStudent_button) {
                    StudentDto studentDto = createStudent();
                    if (validate()) {
                        onCreateFailed();
                    } else {
                        sendCreatedStudent(studentDto);
                    }
                    System.out.println("he");
                }
            }
        });
        getFaculties();
        return rootView;
    }

    private StudentDto createStudent() {
        StudentDto student = new StudentDto();
        student.setEmail(_email.getText().toString());
        student.setFirstName(_firstName.getText().toString());
        student.setLastName(_lastName.getText().toString());
        student.setPassword(_password.getText().toString());
        student.setSsoId(_ssoid.getText().toString());
        student.setFacultyId(studentFacultyId);
        student.setGroupId(studentGroupId);
        student.setTypeOfEducationId(typeOfEducationId);
        return student;
    }

    private void getFaculties() {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<List<Faculty>> call;
        call = client.getfaculties();
        call.enqueue(new Callback<List<Faculty>>() {
            @Override
            public void onResponse(Call<List<Faculty>> call, Response<List<Faculty>> response) {
                if (response.isSuccessful()) {
                    faculties = response.body();
                    faculties_adapter = new ArrayAdapter<>(getActivity(),
                            R.layout.faculty_spinner_layout, faculties);
                    facultySpinner.setAdapter(faculties_adapter);
                } else if (response.code() == 401) {
                    System.out.println("##UNA###");
                }
            }

            @Override
            public void onFailure(Call<List<Faculty>> call, Throwable t) {
                System.out.println("Something went wrong: " + t.getMessage());
            }
        });
    }

    private void getGroupsOfFaculty(Integer id) {

        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<List<Group>> call;
        call = client.getGroupsOfFaculty(id);
        System.out.println("###########" + id);
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    groups = response.body();
                    System.out.println("###########" + groups.toString());
                    groups_adapter = new ArrayAdapter<>(getActivity(),
                            R.layout.faculty_spinner_layout, groups);
                    groupSpinner.setAdapter(groups_adapter);
                } else
                    if (response.code() == 401) {
                    System.out.println("##UNA###");
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.out.println("Something went wrong: " + t);
            }
        });
    }

    private void sendCreatedStudent(final StudentDto student) {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Void> call;
        call = client.createStudent(student);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                if (response.isSuccessful()) {
                    Log.d("############", "Student created " + response.code());

                    alertDialogBuilder.setTitle("Student successfully created");
                    alertDialogBuilder.setIcon(R.drawable.alert_circle_outline);
                    alertDialogBuilder.setNegativeButton("Create one more", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alertDialogBuilder.setPositiveButton("Go to list", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Fragment fragment = new StudentsFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, fragment).addToBackStack("add").commit();
                        }
                    });

                } else if (response.code() == 409) {
                    alertDialogBuilder.setTitle("Student already exist");
                    alertDialogBuilder.setIcon(R.drawable.alert_circle_outline);
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });

                }
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("############", "Student not created because of ", t);
                Log.d("#######", student.toString());
            }
        });

    }

    private boolean validate() {
        boolean valid = true;

        String email = _email.getText().toString();
        String firstName = _firstName.getText().toString();
        String lastName = _lastName.getText().toString();
        String password = _password.getText().toString();
        String ssoid = _ssoid.getText().toString();

        if (ssoid.isEmpty()) {
            _ssoid.requestFocus();
            _ssoid.setError("cannot be empty");
            valid = false;
        } else {
            _ssoid.setError(null);
        }

        if (firstName.isEmpty()) {
            _firstName.requestFocus();
            _firstName.setError("cannot be empty");
            valid = false;
        } else {
            _firstName.setError(null);
        }
        if (lastName.isEmpty()) {
            _lastName.requestFocus();
            _lastName.setError("cannot be empty");
            valid = false;
        } else {
            _lastName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.requestFocus();
            _email.setError("enter a valid email address");
            valid = false;
        } else {
            _email.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 60) {
            _password.requestFocus();
            _password.setError("between 6 and 60 alphanumeric characters");
            valid = false;
        } else {
            _password.setError(null);
        }
        return !valid;
    }

    private void onCreateFailed() {
        Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_LONG).show();
        ServiceGenerator.setInstanceNull();
    }

}
