package symbiosproduction.com.morbuslogs.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.FirebaseFirestore;


import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.FirestoreWrapper;
import symbiosproduction.com.morbuslogs.database.model.patient.Patient;
import symbiosproduction.com.morbuslogs.fragment.logs.NewLogFragment;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "HomeActivity";

    private TextView navLoggedAsTextView;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void updateUI()
    {
        if(user != null)
        {

            navLoggedAsTextView = (TextView) findViewById(R.id.nav_header);
            String userEmail = getString(R.string.nav_header_subtitle, user.getDisplayName());
            navLoggedAsTextView.setText(userEmail);

            FirebaseUserMetadata metadata = user.getMetadata();
            // User is signed in for the first time
            //      getCreationTimestamp() currently is bugged, see attached link.
            //      https://stackoverflow.com/questions/48079683/firebase-user-returns-null-metadata-for-already-signed-up-users
            if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                //@TODO: Add dialog to fill in sex and age of patient.
                FirestoreWrapper firestoreWrapper = new FirestoreWrapper();
                Patient newPatient = new Patient(user);
                firestoreWrapper.addData(newPatient);
                firestoreWrapper.sendPatientToDatabase(newPatient);
            }
            else
            {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
/*                int intensity = 9;
                PainType painType = PainType.SOFT_TISSUE_PAIN;
                String testDescription = " test description \n + test description after backlash 'n' \t ";
                String testDescription2 = " test2  description \n + test2 description after backlash 'n' \t ";
                Long duration = 1923124L;
                SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
                String testDateNow = ISO_8601_FORMAT.format(Calendar.getInstance().getTime());
                String testDateNow2 = ISO_8601_FORMAT.format(new Date());
                PainSymptom symptomTest = new PainSymptom(intensity,painType,testDescription,duration,testDateNow);
                PainSymptom symptomTest2 = new PainSymptom(intensity+1,PainType.ACUTE_PAIN,testDescription2,duration+3,testDateNow2);

                SymptomsLog logTest = new SymptomsLog(testDateNow,testDateNow2);
                logTest.addSymptom(symptomTest);
                logTest.addSymptom(symptomTest2);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(logTest.getCollection())
                        .document(user.getUid())
                        .collection("userLogs")
                        .document(logTest.getDateOfSubmission())
                        .set(logTest.toMap());*/

//                db.collection("Logs")
//                        .document(user.getUid())
//                        .collection("userLogs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful())
//                        {
//                            for(QueryDocumentSnapshot document: task.getResult())
//                            {
//                                Log.d(TAG,document.getId() + " => " + document.getData());
//                            }
//                        }
//                        else
//                        {
//                            Log.d(TAG,"Error getting documents: ", task.getException());
//                        }
//                    }
//                });
                boolean test = true;
            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        updateUI();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_share) {
            // Handle the camera action
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout)
        {
            createLogoutDialog();
        }
        else if (id == R.id.nav_add_new_log)
        {
            fragment = new NewLogFragment();
            fragmentTransaction.replace(R.id.frame_layout_content_main,fragment);
            fragmentTransaction.addToBackStack(null); //stops fragment instead of destroying it
        }

        if(fragment != null) {
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void createLogoutDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        alertDialogBuilder.setMessage(R.string.message_logout_dialog);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle(R.string.title_logout_dialog);
        alertDialogBuilder.setIcon(R.drawable.ic_warning_white_24dp);

        alertDialogBuilder.setPositiveButton(R.string.confirm_logout_dialog,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleLogout();
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.cancel_logout_dialog,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void handleLogout()
    {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User has been successfully logged out");
                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        HomeActivity.this.finish();
                    }
                });
    }
}
