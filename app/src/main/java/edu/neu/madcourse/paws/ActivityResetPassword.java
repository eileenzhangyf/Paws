package edu.neu.madcourse.paws;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityResetPassword extends AppCompatActivity {
    FirebaseAuth myauth;
    Button sent;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        sent = (Button) findViewById(R.id.resetbutton);
        myauth = FirebaseAuth.getInstance();

        EditText email_input = (EditText) findViewById(R.id.reseteditor);

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = email_input.getText().toString();
                myauth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("RESET", "Email sent.");
                                }
                            }
                        });
            }
        });
    }
}
