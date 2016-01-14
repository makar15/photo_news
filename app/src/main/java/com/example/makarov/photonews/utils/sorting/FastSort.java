package com.example.makarov.photonews.utils.sorting;

import com.example.makarov.photonews.models.Subscription;

import java.util.List;

public class FastSort {

    public static List<Subscription> sort(List<Subscription> subscriptions, int start, int end) {

        if (start >= end) return subscriptions;
        int i = start;
        int j = end;
        int op = i - (i - j) / 2;

        while (i < j) {
            while ((i < op) && (subscriptions.get(i).getDate() <= subscriptions.get(op).getDate()))
                i += 1;
            while ((j > op) && (subscriptions.get(j).getDate() >= subscriptions.get(op).getDate()))
                j -= 1;

            if (i < j) {

                Subscription temp = subscriptions.get(i);

                subscriptions.set(i, subscriptions.get(j));
                subscriptions.set(j, temp);
                if (i == op) op = j;
                else if (j == op) op = i;
            }
        }
        sort(subscriptions, start, op);
        sort(subscriptions, op + 1, end);

        return subscriptions;
    }
}

