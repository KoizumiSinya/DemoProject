package com.tgi.multiple_category_view.anim;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * @author: Sinya
 * @date: 2019/02/28. 17:10
 * @editor:
 * @edit date:
 */
public final class FilterItemAnimation extends SimpleItemAnimator {


    private List<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    private List<MoveInfo> mPendingMoves = new ArrayList<>();
    private List<ChangeInfo> mPendingChanges = new ArrayList<>();

    private List<List<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    private List<List<MoveInfo>> mMovesList = new ArrayList<>();
    private List<List<ChangeInfo>> mChangesList = new ArrayList<>();

    private List<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        mPendingRemovals.add(holder);
        return true;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        holder.itemView.setAlpha(0f);
        mPendingAdditions.add(holder);
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        fromX += holder.itemView.getTranslationX();
        fromY += holder.itemView.getTranslationY();
        resetAnimation(holder);

        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            view.setTranslationX(-deltaX);
        }
        if (deltaY != 0) {
            view.setTranslationY(-deltaY);
        }
        mPendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        if (oldHolder == newHolder) {
            // Don't know how to run change animations when the same view holder is re-used.
            // run a move animation to handle position changes.
            return animateMove(oldHolder, fromLeft, fromTop, toLeft, toTop);
        }
        float prevTranslationX = oldHolder.itemView.getTranslationX();
        float prevTranslationY = oldHolder.itemView.getTranslationY();
        float prevAlpha = oldHolder.itemView.getAlpha();

        resetAnimation(oldHolder);

        float deltaX = (toLeft - fromLeft - prevTranslationX);
        float deltaY = (toTop - fromTop - prevTranslationY);
        // recover prev translation state after ending animation
        oldHolder.itemView.setTranslationX(prevTranslationX);
        oldHolder.itemView.setTranslationY(prevTranslationY);
        oldHolder.itemView.setAlpha(prevAlpha);

        if (newHolder != null) {
            // carry over translation values
            resetAnimation(newHolder);
            newHolder.itemView.setTranslationX(-deltaX);
            newHolder.itemView.setTranslationY(-deltaY);
            oldHolder.itemView.setAlpha(0f);
        }
        mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop));
        return true;
    }

    @Override
    public void runPendingAnimations() {
        boolean removalsPending = !mPendingRemovals.isEmpty();
        boolean movesPending = !mPendingMoves.isEmpty();
        boolean changesPending = !mPendingChanges.isEmpty();
        boolean additionsPending = !mPendingAdditions.isEmpty();
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
            return;
        }

        for (RecyclerView.ViewHolder holder : mPendingRemovals) {
            animateRemoveImpl(holder);
        }
        mPendingRemovals.clear();

        // Next, move stuff
        if (movesPending) {
            final List<MoveInfo> moves = new ArrayList<>();
            moves.addAll(mPendingMoves);
            mMovesList.add(moves);
            mPendingMoves.clear();

            Runnable mover = new Runnable() {
                @Override
                public void run() {
                    for (MoveInfo info : moves) {
                        animateMoveImpl(info.holder, info.fromX, info.fromY, info.toX, info.toY);
                    }
                    moves.clear();
                    mMovesList.remove(moves);
                }
            };

            if (removalsPending) {
                View view = moves.get(0).holder.itemView;
                ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
            } else {
                mover.run();
            }
        }


        // Next, change stuff, to run in parallel with move animations
        if (changesPending) {
            final List<ChangeInfo> changes = new ArrayList<>();
            changes.addAll(mPendingChanges);
            mChangesList.add(changes);
            mPendingChanges.clear();
            Runnable changer = new Runnable() {
                @Override
                public void run() {
                    for (ChangeInfo info : changes) {
                        animateChangeImpl(info);
                    }
                    changes.clear();
                    mChangesList.remove(changes);
                }
            };
            if (removalsPending) {
                RecyclerView.ViewHolder holder = changes.get(0).oldHolder;
                ViewCompat.postOnAnimationDelayed(holder.itemView, changer, getRemoveDuration());
            } else {
                changer.run();
            }
        }

        // Next, add stuff
        if (additionsPending) {
            final List<RecyclerView.ViewHolder> additions = new ArrayList<>();
            additions.addAll(mPendingAdditions);
            mAdditionsList.add(additions);
            mPendingAdditions.clear();
            Runnable adder = new Runnable() {
                @Override
                public void run() {
                    for (RecyclerView.ViewHolder holder : additions) {
                        animateAddImpl(holder);
                    }
                    additions.clear();
                    mAdditionsList.remove(additions);
                }
            };

            if (removalsPending || movesPending || changesPending) {
                long removeDuration = removalsPending ? getRemoveDuration() : 0;
                long moveDuration = movesPending ? getMoveDuration() : 0;
                long changeDuration = changesPending ? getChangeDuration() : 0;
                long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
                View view = additions.get(0).itemView;
                ViewCompat.postOnAnimationDelayed(view, adder, totalDelay);
            } else {
                adder.run();
            }
        }

    }

    @Override
    public void endAnimation(@NonNull RecyclerView.ViewHolder item) {
        View view = item.itemView;
        // this will trigger end callback which should set properties to their target values.
        ViewCompat.animate(view).cancel();

        // TODO if some other animations are chained to end, how do we cancel them as well?
        for (int i = mPendingMoves.size() - 1; i >= 0; i--) {
            if (mPendingMoves.get(i).holder == item) {
                view.setTranslationY(0f);
                view.setTranslationX(0f);
                dispatchMoveFinished(item);
                mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(mPendingChanges, item);
        if (mPendingRemovals.remove(item)) {
            view.setAlpha(1f);
            dispatchRemoveFinished(item);
        }
        if (mPendingAdditions.remove(item)) {
            view.setAlpha(1f);
            dispatchAddFinished(item);
        }
        for (int i = mChangesList.size() - 1; i >= 0; i--) {
            List<ChangeInfo> changes = mChangesList.get(i);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) {
                mChangesList.remove(i);
            }
        }
        for (int i = mMovesList.size() - 1; i >= 0; i--) {
            List<MoveInfo> moves = mMovesList.get(i);
            for (int j = moves.size() - 1; j >= 0; j--) {
                MoveInfo moveInfo = moves.get(j);
                if (moveInfo.holder == item) {
                    view.setTranslationY(0f);
                    view.setTranslationX(0f);
                    dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        mMovesList.remove(i);
                    }
                    break;
                }
            }
        }
        for (int i = mAdditionsList.size() - 1; i >= 0; i--) {
            List<RecyclerView.ViewHolder> additions = mAdditionsList.get(i);
            if (additions.remove(item)) {
                view.setAlpha(1f);
                dispatchAddFinished(item);
                if (additions.isEmpty()) {
                    mAdditionsList.remove(i);
                }
            }
        }
        dispatchFinishedWhenDone();
    }

    @Override
    public void endAnimations() {
        int count = mPendingMoves.size();
        for (int i = count - 1; i >= 0; i--) {
            MoveInfo item = mPendingMoves.get(i);
            View view = item.holder.itemView;
            view.setTranslationY(0f);
            view.setTranslationX(0f);
            dispatchMoveFinished(item.holder);
            mPendingMoves.remove(i);
        }
        count = mPendingRemovals.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mPendingRemovals.get(i);
            dispatchRemoveFinished(item);
            mPendingRemovals.remove(i);
        }
        count = mPendingAdditions.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mPendingAdditions.get(i);
            View view = item.itemView;
            view.setAlpha(1f);
            dispatchAddFinished(item);
            mPendingAdditions.remove(i);
        }
        count = mPendingChanges.size();
        for (int i = count - 1; i >= 0; i--) {
            endChangeAnimationIfNecessary(mPendingChanges.get(i));
        }
        mPendingChanges.clear();
        if (!isRunning()) {
            return;
        }

        int listCount = mMovesList.size();
        for (int i = listCount - 1; i >= 0; i--) {
            List<MoveInfo> moves = mMovesList.get(i);
            count = moves.size();
            for (int j = count - 1; j >= 0; j--) {
                MoveInfo moveInfo = moves.get(j);
                RecyclerView.ViewHolder item = moveInfo.holder;
                View view = item.itemView;
                view.setTranslationY(0f);
                view.setTranslationX(0f);
                dispatchMoveFinished(moveInfo.holder);
                moves.remove(j);
                if (moves.isEmpty()) {
                    mMovesList.remove(moves);
                }
            }
        }
        listCount = mAdditionsList.size();
        for (int i = listCount - 1; i >= 0; i--) {
            List<RecyclerView.ViewHolder> additions = mAdditionsList.get(i);
            count = additions.size();
            for (int j = count - 1; j >= 0; j--) {
                RecyclerView.ViewHolder item = additions.get(j);
                View view = item.itemView;
                view.setAlpha(1f);
                dispatchAddFinished(item);
                additions.remove(j);
                if (additions.isEmpty()) {
                    mAdditionsList.remove(additions);
                }
            }
        }
        listCount = mChangesList.size();
        for (int i = listCount - 1; i >= 0; i--) {
            List<ChangeInfo> changes = mChangesList.get(i);
            count = changes.size();
            for (int j = count - 1; j >= 0; j--) {
                endChangeAnimationIfNecessary(changes.get(j));
                if (changes.isEmpty()) {
                    mChangesList.remove(changes);
                }
            }
        }

        cancelAll(mRemoveAnimations);
        cancelAll(mMoveAnimations);
        cancelAll(mAddAnimations);
        cancelAll(mChangeAnimations);

        dispatchAnimationsFinished();
    }

    @Override
    public boolean isRunning() {
        return !mPendingAdditions.isEmpty() //
                || !mPendingChanges.isEmpty() //
                || !mPendingMoves.isEmpty() //
                || !mPendingRemovals.isEmpty() //
                || !mMoveAnimations.isEmpty() //
                || !mRemoveAnimations.isEmpty() //
                || !mAddAnimations.isEmpty() //
                || !mChangeAnimations.isEmpty() //
                || !mMovesList.isEmpty() //
                || !mAdditionsList.isEmpty() //
                || !mChangesList.isEmpty();
    }


    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 300f).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 300;

                view.setScaleX(1 - ratio);
                view.setScaleY(1 - ratio);
                view.setAlpha(1 - ratio);

                if (ratio == 1f) {
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                    view.setAlpha(1f);
                    dispatchRemoveFinished(holder);
                    mRemoveAnimations.remove(holder);
                    dispatchFinishedWhenDone();
                }
            }
        });
        valueAnimator.start();
    }


    private void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        final View view = holder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            ViewCompat.animate(view).translationX(0f);
        }
        if (deltaY != 0) {
            ViewCompat.animate(view).translationY(0f);
        }
        mMoveAnimations.add(holder);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 300f).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 300;
                view.setScaleX(ratio);
                view.setScaleY(ratio);
                view.setAlpha(ratio);

                if (ratio == 1f) {
                    dispatchMoveFinished(holder);
                    mMoveAnimations.remove(holder);
                    dispatchFinishedWhenDone();
                }
            }
        });
        valueAnimator.start();
    }

    private void animateChangeImpl(final ChangeInfo changeInfo) {
        RecyclerView.ViewHolder holder = changeInfo.oldHolder;
        final View view = holder.itemView;
        RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder.itemView;

        mChangeAnimations.add(changeInfo.oldHolder);

        view.setTranslationX(changeInfo.toX - changeInfo.fromX);
        view.setTranslationY(changeInfo.toY - changeInfo.fromY);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 300f).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 300;

                view.setScaleX(1 - ratio);
                view.setScaleY(1 - ratio);
                view.setAlpha(1 - ratio);

                if (ratio == 1f) {
                    view.setAlpha(1f);
                    view.setTranslationX(0f);
                    view.setTranslationY(0f);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    dispatchFinishedWhenDone();
                }
            }
        });
        valueAnimator.start();
        mChangeAnimations.add(changeInfo.newHolder);

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f, 300f).setDuration(300);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 300;
                newView.setScaleX(ratio);
                newView.setScaleY(ratio);
                newView.setAlpha(ratio);
                newView.setTranslationX(1 - ratio);
                newView.setTranslationY(1 - ratio);
                if (ratio == 1f) {
                    newView.setAlpha(1f);
                    newView.setTranslationX(0f);
                    newView.setTranslationY(0f);
                    dispatchChangeFinished(changeInfo.newHolder, false);
                    mChangeAnimations.remove(changeInfo.newHolder);
                    dispatchFinishedWhenDone();
                }
            }
        });
        valueAnimator2.start();
    }


    private void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        mAddAnimations.add(holder);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 300f).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 300;

                view.setScaleX(ratio);
                view.setScaleY(ratio);
                view.setAlpha(ratio);

                if (ratio == 1f) {
                    dispatchAddFinished(holder);
                    mAddAnimations.remove(holder);
                    dispatchFinishedWhenDone();
                }
            }
        });

        valueAnimator.start();
    }


    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void resetAnimation(RecyclerView.ViewHolder holder) {
        //AnimatorCompatHelper.clearInterpolator(holder.itemView);
        holder.itemView.animate().setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return 0;
            }
        });
        endAnimation(holder);
    }


    private void endChangeAnimation(List<ChangeInfo> infoList, RecyclerView.ViewHolder item) {
        for (int i = infoList.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item)) {
                if (changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                    infoList.remove(changeInfo);
                }
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);

        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }


    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder == item) {
            changeInfo.oldHolder = null;
            oldItem = true;
        } else {
            return false;
        }
        item.itemView.setAlpha(1f);
        item.itemView.setTranslationX(0f);
        item.itemView.setTranslationY(0f);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    private void cancelAll(List<RecyclerView.ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ViewCompat.animate(viewHolders.get(i).itemView).cancel();
        }
    }

    class MoveInfo {
        protected RecyclerView.ViewHolder holder;
        protected int fromX;
        protected int fromY;
        protected int toX;
        protected int toY;

        public MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }


    class ChangeInfo {
        protected RecyclerView.ViewHolder oldHolder;
        protected RecyclerView.ViewHolder newHolder;
        protected int fromX;
        protected int fromY;
        protected int toX;
        protected int toY;

        public ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }
}
