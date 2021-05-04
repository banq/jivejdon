package com.jdon.jivejdon.spi.component.viewcount;

import java.util.Comparator;

import com.jdon.jivejdon.domain.model.thread.ViewCounter;

public class ThreadViewComparator implements Comparator<ViewCounter> {

    @Override
    public int compare(ViewCounter o1, ViewCounter o2) {
        if (o1.getViewCount() == o2.getViewCount())
            return 0;
        else if (o1.getViewCount() > o2.getViewCount())
            return -1;
        else
            return 1;
    }

}