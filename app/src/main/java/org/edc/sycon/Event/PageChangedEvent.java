package org.edc.sycon.Event;

import android.util.Log;

/**
 * Dispatched when the current selected page of the application navigation changed. E.g. user swipes from the center
 * page to the left page.
 */
public class PageChangedEvent {

    /**
     * @param hasVerticalNeighbors true if the current selected page has vertical (below and/or above) neighbor pages, false - if not.
     */
    public PageChangedEvent(boolean hasVerticalNeighbors) {
        mHasVerticalNeighbors = hasVerticalNeighbors;
    }

    private boolean mHasVerticalNeighbors = true;

    /**
     * @return true if the page has vertical (below and/or above) neighbor pages, false - if not.
     */
    public boolean hasVerticalNeighbors() {
        Log.d(getClass().getName(), "hasVerticalNeighbors: called and value is " + mHasVerticalNeighbors);
        return mHasVerticalNeighbors;
    }

}
