package com.example.toolsharing;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    Animation anim;
    TextView txt; //Splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(GONE);

        /* Splash Screen    */
        txt = findViewById(R.id.txt);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setUpNavigation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        txt.startAnimation(anim);
        /* Splash End   */


    }

    private void setUpNavigation() {
        txt.setVisibility(GONE);

        Navigation.findNavController(this, R.id.host_frag);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
