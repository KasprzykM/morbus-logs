package symbiosproduction.com.morbuslogs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.activity.HomeActivity;


public class LoginActivity extends AppCompatActivity{


    private static final int RC_SIGN_IN = 1996;
    private static final String TAG = "LoginActivity";

    private ConstraintLayout constraintLayout;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authGoogle();
        setContentView(R.layout.activity_login);
        constraintLayout = (ConstraintLayout) findViewById(R.id.loginActivityLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                this.finish();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                if (response == null) {
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                Log.e(TAG, "Sign-in error: ", response.getError());

            }
        }
    }

    private void showSnackbar(Integer messageId)
    {
        String message = getResources().getString(messageId);
        Snackbar.make(constraintLayout,message,Snackbar.LENGTH_LONG).show();
    }

    private void authGoogle()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.AppTheme_LoginActivity)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public void onClickGoogle(View view)
    {
        authGoogle();
    }

}
