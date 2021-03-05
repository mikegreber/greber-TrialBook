package com.example.greber_trialbook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Helper class for RecyclerViews for making list swipe-able with callback.
 *
 * Adapted from Medium Article
 * author: Zachery Osborn
 * title: Step by Step: RecyclerView Swipe to Delete and Undo
 * date: September 12, 2018
 * link: https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
 */
public class ItemSwipeHelper {

    private ItemSwipeHelper() { }

    public interface OnItemSwipeListener {
        void onSwipe(int swipedIndex, int direction);
    }

    private static class SwipeCallback extends ItemTouchHelper.SimpleCallback {
        private  OnItemSwipeListener listener;

        private SwipeCallback(@NonNull OnItemSwipeListener listener) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.listener = listener;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            listener.onSwipe(viewHolder.getAdapterPosition(), direction);
        }
    }

    static void setCallback(@NonNull RecyclerView recyclerView, @NonNull OnItemSwipeListener callback) {
        new ItemTouchHelper(new SwipeCallback(callback)).attachToRecyclerView(recyclerView);
    }
}
