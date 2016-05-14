package com.example.makarov.photonews.utils;

import com.example.makarov.photonews.models.Subscription;

import java.util.List;

public class FastSort {

    public static List<Subscription> sort(List<Subscription> subscriptions, int start, int end) {

        if (start >= end) return subscriptions;
        int i = start;
        int j = end;
        int op = i - (i - j) / 2;

        long dateI = subscriptions.get(i).getDate();
        long dateJ = subscriptions.get(j).getDate();
        long dateOp = subscriptions.get(op).getDate();

        while (i < j) {
            while (i < op && dateI <= dateOp) {
                i += 1;
            }
            while (j > op && dateJ >= dateOp) {
                j -= 1;
            }

            if (i < j) {
                Subscription subscription = subscriptions.get(i);

                subscriptions.set(i, subscriptions.get(j));
                subscriptions.set(j, subscription);
                if (i == op) {
                    op = j;
                } else if (j == op) {
                    op = i;
                }
            }
        }
        sort(subscriptions, start, op);
        sort(subscriptions, op + 1, end);

        return subscriptions;
    }
}

