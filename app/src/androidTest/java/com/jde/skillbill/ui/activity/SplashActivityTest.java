package com.jde.skillbill.ui.activity;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.jde.skillbill.R;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class SplashActivityTest {

    @Rule
    public ActivityScenarioRule<SplashActivity> rule = new ActivityScenarioRule<>(SplashActivity.class);

    @Test
    public void testVue(){
/*
        ActivityScenario<SplashActivity> scenario =rule.getScenario();
        scenario.launch(SplashActivity.class);
        scenario.onActivity(activity -> {
            View vue = activity.getCurrentFocus();
            assertNotNull(vue);
            assertTrue(vue.equals(activity.findViewById(R.layout.activity_splash)));
        });

 */
    }
}
