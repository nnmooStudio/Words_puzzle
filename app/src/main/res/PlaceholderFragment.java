package com.nnmoo.www.words_puzzle;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by zhouf on 2016/6/2.
 */
public class PlaceholderFragment extends Fragment{
    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

    }
