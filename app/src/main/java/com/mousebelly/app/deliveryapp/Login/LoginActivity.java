package com.mousebelly.app.deliveryapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.deliveryapp.APIs;
import com.mousebelly.app.deliveryapp.CustomProgressDialog;
import com.mousebelly.app.deliveryapp.LocalSaveModule;
import com.mousebelly.app.deliveryapp.MapsActivity;
import com.mousebelly.app.deliveryapp.R;
import com.mousebelly.app.deliveryapp.Server;
import com.mousebelly.app.deliveryapp.SocketAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import io.socket.emitter.Emitter;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    //public static Context currentActivity;
    public String url;
    public static User user = new User();
    public static String USERID, USERNAME;
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mPhoneNo,mPasswordView;
    // private View mProgressView;
    private ProgressDialog loginProgress;

    TextView forgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginProgress = CustomProgressDialog.getDialog(LoginActivity.this,"Verifying...");

        Typeface trumpitFace = Typeface.createFromAsset(getAssets(), this.getResources().getString(R.string.font_face));
        TextView mouse = (TextView) findViewById(R.id.mouse);
        mouse.setTypeface(trumpitFace);
        TextView belly = (TextView) findViewById(R.id.belly);
        belly.setTypeface(trumpitFace);
        // Set up the login form.
        mPhoneNo = (EditText)findViewById(R.id.phoneno);


        mPasswordView = (EditText) findViewById(R.id.password);

        SharedPreferences cred = this.getSharedPreferences(LocalSaveModule.Credential, Context.MODE_PRIVATE);
        String savedEmail = cred.getString("UserName", "").toString();
        String savedPass = cred.getString("Password", "").toString();

        mPhoneNo.setText(savedEmail);
        mPasswordView.setText(savedPass);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = mPhoneNo.getText().toString();


                url = APIs.getDeliveryBoy + Email;
                mPasswordView.clearFocus();
                mPhoneNo.clearFocus();
                attemptLogin();
            }
        });


        forgetPass = (TextView) findViewById(R.id.forget_password);


        forgetPass.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LinearLayout popupLayout = new LinearLayout(getApplicationContext());
                popupLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                popupLayout.setOrientation(LinearLayout.VERTICAL);




                final EditText emailOTP = new EditText(getApplicationContext());
                emailOTP.setPadding(10, 50, 10, 30);
                emailOTP.setHint("Your Email");
                emailOTP.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);
                emailOTP.setImeOptions(EditorInfo.IME_ACTION_DONE);
                emailOTP.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                popupLayout.addView(emailOTP);

                Button sendOTP = new Button(getApplicationContext());
                sendOTP.setText("Send OTP");
                sendOTP.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailAddress = emailOTP.getText().toString();
                        //url = APIs.mail_sendotp + emailAddress;
                        emailOTP.clearFocus();
                        attemptLogin();
                    }
                });
                popupLayout.addView(sendOTP);


                builder.setCustomTitle(popupLayout);
                builder.show();
            }
        });

    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPhoneNo.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mPhoneNo.getText().toString();
        USERID = email;
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = this.getCurrentFocus();


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mPhoneNo.setError(getString(R.string.error_field_required));
            focusView = mPhoneNo;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mPhoneNo.setError(getString(R.string.error_invalid_email));
            focusView = mPhoneNo;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if (focusView != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            // mProgressView.setVisibility(View.VISIBLE);
           // loginProgress.setMessage("Verifying...");
            loginProgress.setCancelable(false);
            loginProgress.show();
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {

        /*if (!Pattern.compile("^[789][0-9]{9,9}$").matcher(email).matches())
            return false;
        else*/{
        return true;

        }

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            String jsonstr = Server.s.get(url);
            if (jsonstr != null) {
                System.out.println("Value of " + jsonstr);

                try {

                    JSONObject jsonObject = new JSONObject(jsonstr);
                    String id = jsonObject.getString("_id");
                    String Contact = jsonObject.getString("contact");
                    String Pwd = jsonObject.getString("password");
                    USERNAME = Contact;

                    user.set_id(id);
                    user.setContact(Contact);
                    user.setPassword(Pwd);


                    System.out.println("Entered Password : " + mPassword);
                    if (Contact.equals(mEmail)) {
                       // String result = VerifyLogin.compare(mPassword, Pwd) ? "Login Successful" : "Login Failed";
                        System.out.println("Hash" + Pwd);

                        boolean status = VerifyLogin.compare(mPassword, Pwd);
                        if (status) {
                            LocalSaveModule.storePreferences(LoginActivity.this, mPassword);
                            System.out.println("saved");


                            System.out.println("Login Successful");

                            // is it write
                            SocketAccess.socket.emit("deliveryBoyActive", id);

                      /*      System.out.println("listening");
                            SocketAccess.socket.on("loggedin", new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {

                                    String socketId;

                                    try {
                                        JSONObject socketIdjson = new JSONObject(args[0].toString());
                                        socketId = socketIdjson.getString("socket_id");
                                        System.out.println("SOCKET ID : " + socketId);
                                        // System.out.println("http://mousebelly.herokuapp.com/sign/sockSessionid/"+user.getEmail()+"/"+socketId);

                                        //TODO uncomment to activate

                                        *//* Server.s.put(APIs.sign_sockSessionid + USERID + "/" + socketId);

                                        Server.s.put(APIs.sign_LoggedinCheck + USERID + "/true");
                                        Server.s.put(APIs.sign_isConnectedtrue + USERID + "/true");*//*

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });*/
                        }

                        return status;

                    }
//                        String result = VerifyLogin.compare(mPassword,Pwd)?"Login Successful":"Login Failed";
                    // Toast.makeText(getApplicationContext(),  , Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {

                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();

                Intent logintomainactivity = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(logintomainactivity);

            } else {
                forgetPass.setVisibility(View.VISIBLE);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_LONG).show();
            }

            loginProgress.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            loginProgress.dismiss();

            // mProgressView.setVisibility(View.GONE);
        }
    }


}

