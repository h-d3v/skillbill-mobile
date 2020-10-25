package com.jde.skillbill.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.vue.VueConnexion;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityConnexionTest {

    @Rule
    public ActivityScenarioRule<ActivityConnexion> rule = new ActivityScenarioRule<ActivityConnexion>(ActivityConnexion.class);

    @Test
    public void testerFragment(){
        ActivityScenario<ActivityConnexion> scenario = rule.getScenario();
        scenario.launch(ActivityConnexion.class);

        scenario.onActivity(activity -> {
            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.layout_connexion);
            assertNotNull(fragment);
            assertTrue(fragment instanceof VueConnexion);
        });

    }


}