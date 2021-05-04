package com.jdon.jivejdon.spi.component.viewcount;

import java.util.Comparator;

import com.jdon.jivejdon.domain.model.thread.ViewCounter;

public class ThreadViewComparator implements Comparator<ViewCounter> {

    @Override
    public int compare(ViewCounter o1, ViewCounter o2) {
        int diff1 = o1.getViewCount() - o1.getLastSavedCount();
        int diff2 = o2.getViewCount() - o2.getLastSavedCount();
        if (diff1 == diff2)
            return 0;
        else if (diff1 > diff2)
            return -1;
        else
            return 1;
    }

}