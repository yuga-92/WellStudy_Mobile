package com.bootcamp.wellstudy.fragments.forAdmin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.adapters.CustomUsersGridViewAdapter;
import com.bootcamp.wellstudy.api.ServiceGenerator;
import com.bootcamp.wellstudy.api.WellStudyClient;
import com.bootcamp.wellstudy.model.Student;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bootcamp.wellstudy.Constants.USERS_PER_LIST_PAGE;

public class StudentsFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    private Integer page = 1;
    private GridView studentsGridView;
    private SwipyRefreshLayout swipyRefreshLayout;
    private List<Student> students;
    private List<Student> studentsTemp;
    private ActionBar actionBar;
    private Integer lastItemInScroll = 0;
    private Integer numberOfStudents;

    public StudentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        swipyRefreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setOnRefreshListener(this);
        setHasOptionsMenu(true);
        studentsGridView = (GridView) rootView.findViewById(R.id.gridView);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        students = new ArrayList<>();
        downloadStudentsAndAddToList(false);
//        studentsGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                           int position, long arg3) {
//                deleteStudentDialog(students.get(position).getSsoId());
//                return false;
//            }
//        });
        studentsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Fragment fragment = new StudentInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ssoId", students.get(position).getSsoId());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack("students").commit();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddStudentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack("students").commit();
            }
        });

        return rootView;
    }

    private void downloadStudentsAndAddToList(final Boolean selection) {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<List<Student>> call;
        call = client.students(page, Integer.valueOf(USERS_PER_LIST_PAGE));
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    studentsTemp = response.body();
                    /** For debug */
                    System.out.println(response.code() + "###### " + response.isSuccessful() +
                            response.headers() + "88888" + response.message());
                    System.out.println("###############" + response.body());
                    /** For debug */
                    if (studentsTemp != null) {
                        students.addAll(studentsTemp);
                        getNumberOfStudents();
                    }
                    if (selection) {
                        studentsGridView.setSelection(lastItemInScroll);
                    }
                    studentsGridView.setAdapter(new CustomUsersGridViewAdapter(getActivity(), students));
                    swipyRefreshLayout.setRefreshing(false);
                } else if (response.code() == 401) {
                    System.out.println("##UNA###");
                }
            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                System.out.println("Something went wrong: " + t.getMessage());
            }
        });
    }


    private void getNumberOfStudents() {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Integer> call;
        call = client.getNumberOfStudents();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    numberOfStudents = response.body();
                    Integer currentInView = students.size();
                    actionBar.setTitle(currentInView + " students of " + numberOfStudents);
                } else if (response.code() == 401) {
                    System.out.println("##UNA###");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("Something went wrong: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        lastItemInScroll = studentsGridView.getLastVisiblePosition();
        if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            ++page;
            System.out.println("#######PAGENUMBER" + page);
            getNumberOfStudents();
            downloadStudentsAndAddToList(true);
        } else {
            students.clear();
            downloadStudentsAndAddToList(false);
        }
    }

    private void searchStudentsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.students_search_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.searchField_textView);
        dialogBuilder.setTitle("Search");
        dialogBuilder.setIcon(R.drawable.magnify);
        dialogBuilder.setMessage("Enter second name of student that you want to search:");
        dialogBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edt.getText().toString();
                if (!name.isEmpty()){
                    searchStudent(edt.getText().toString());

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Field can not be empty");
                    alertDialogBuilder.setIcon(R.drawable.alert_circle_outline);
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            searchStudentsDialog();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void searchStudent(final String name){
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        System.out.println("##UNA1###");
        Call<List<Student>> call;
        call = client.searchByLastname(name);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    students = response.body();
                    if (students != null) {
                        studentsGridView.setAdapter(new CustomUsersGridViewAdapter(getActivity(), students));
                    } else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Student \""+ name +"\" not found");
                        alertDialogBuilder.setIcon(R.drawable.account_alert);
                        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } else if (response.code() == 401) {
                    System.out.println("##UNA401###");
                } else {
                    // Handle other responses
                    System.out.println("#####"+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                System.out.println("Something went wrong: " + t.getMessage());
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            searchStudentsDialog();
            return true;
        }
        return false;
    }

    private void deleteStudentDialog(final String ssoid){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Student \""+ ssoid +"\" not found");
        alertDialogBuilder.setMessage("Are you really want to delete this student?");
        alertDialogBuilder.setIcon(R.drawable.account_alert);
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteStudent(ssoid);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteStudent(String ssoid){
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
        WellStudyClient client = serviceGenerator.getService();
        Call<Void> call;
        call = client.deleteStudent(ssoid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    Log.d("############","Student deleted " + response.code());
                    alertDialogBuilder.setTitle("Student deleted");
                    alertDialogBuilder.setIcon(R.drawable.alert_circle_outline);
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            downloadStudentsAndAddToList(false);
                        }
                    });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("############","Student not deleted because of ",t);
               // Log.d("#######", student.toString());
            }
        });

    }
}
