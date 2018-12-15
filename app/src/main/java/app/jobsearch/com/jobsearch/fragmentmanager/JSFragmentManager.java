package app.jobsearch.com.jobsearch.fragmentmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.jobsearch.com.jobsearch.R;

public class JSFragmentManager {
    private FragmentActivity myContext;

    public JSFragmentManager(FragmentActivity aContext) {
        myContext = aContext;
    }


    public void updateContent(Fragment aFragment, String tag, Bundle aBundle) {
        try {

            Log.e("TAG Screen name", tag);

            // Initialise Fragment Manager
            final FragmentManager aFragmentManager = myContext
                    .getSupportFragmentManager();

            // Initialise Fragment Transaction
            final FragmentTransaction aTransaction = aFragmentManager
                    .beginTransaction();

            if (aBundle != null) {
                aFragment.setArguments(aBundle);
            }


            //      aTransaction.setCustomAnimations(R.anim.right_to_left_second, R.anim.left_to_right_second);

            aTransaction.add(R.id.container, aFragment, tag);

            // Add the selected fragment

            // add the tag to the backstack
            aTransaction.addToBackStack(tag);

            // Commit the Fragment transaction
            aTransaction.commit();


        } catch (Exception aError) {
            aError.printStackTrace();
        }
    }


    public void clearAllFragments() {

        try {
            FragmentManager aFragmentManager = myContext
                    .getSupportFragmentManager();

            aFragmentManager.popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void removeFragment(int aCount) {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        for (int i = 0; i < aCount; i++) {
            aFragmentManager.popBackStack();

        }
    }

    public void clearOneFragment() {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        if (aFragmentManager.getBackStackEntryCount() > 1) {
            aFragmentManager.popBackStack();
            aFragmentManager.executePendingTransactions();

            Log.d("TAG",
                    "CURRENT FRAGMENT BACK STACK CLASS "
                            + aFragmentManager
                            .getBackStackEntryAt(
                                    aFragmentManager
                                            .getBackStackEntryCount() - 1)
                            .getName());


            Fragment aFragment = aFragmentManager.getFragments().get(aFragmentManager.getBackStackEntryCount() - 1);
            aFragment.onResume();

            if (aFragment instanceof JSFragment) {
                ((JSFragment) aFragment).onResumeFragment();
            }
            String aFragmentName = aFragmentManager.getBackStackEntryAt(
                    aFragmentManager.getBackStackEntryCount() - 1).getName();
        }
    }

    public void backPress() {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        if (aFragmentManager.getBackStackEntryCount() > 1) {
            aFragmentManager.popBackStack();
            aFragmentManager.executePendingTransactions();

            Log.d("TAG",
                    "CURRENT FRAGMENT BACK STACK CLASS "
                            + aFragmentManager
                            .getBackStackEntryAt(
                                    aFragmentManager
                                            .getBackStackEntryCount() - 1)
                            .getName());


            Fragment aFragment = aFragmentManager.getFragments().get(aFragmentManager.getBackStackEntryCount() - 1);
            aFragment.onResume();

         /*   if (aFragment instanceof RTFragment) {
                ((RTFragment) aFragment).onResumeFragment();
            }*/
            String aFragmentName = aFragmentManager.getBackStackEntryAt(
                    aFragmentManager.getBackStackEntryCount() - 1).getName();
        }
    }


    public int getBackstackCount() {

        FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        return aFragmentManager.getBackStackEntryCount();
    }


    //Get the Current TAG

    public String getActiveFragmentTAG() {

        if (myContext.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String aCurrentTAG = myContext.getSupportFragmentManager().getBackStackEntryAt(myContext.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();

        return aCurrentTAG;
    }

    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = myContext.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = myContext.getSupportFragmentManager().findFragmentByTag(fragmentTag);
        return currentFragment;
    }
}
