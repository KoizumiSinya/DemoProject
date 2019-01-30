package jp.sinya.swipeback.demo.library3.core.queue;

import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.Queue;

import jp.sinya.swipeback.demo.library3.core.ISupportFragment;
import jp.sinya.swipeback.demo.library3.core.SupportHelper;


public class ActionQueue {

    /**
     * Queue 在队列这种数据结构中，最先插入的元素将是最先被删除的元素；
     * 反之最后插入的元素将是最后被删除的元素，因此队列又称为“先进先出”（FIFO—first in first out）的线性表。
     */
    private Queue<Action> mQueue = new LinkedList<>();

    /**
     * 活跃在主线程的Handler
     */
    private Handler mMainHandler;

    public ActionQueue(Handler mainHandler) {
        this.mMainHandler = mainHandler;
    }

    public void enqueue(final Action action) {
        if (isThrottleBACK(action)) {
            return;
        }

        //如果Action的类型是Load加载，并且当前队列为空，且在主线程中，则直接执行Action对象的run()
        if (action.action == Action.ACTION_LOAD && mQueue.isEmpty() //
                //判断当前线程是主线程
                && Thread.currentThread() == Looper.getMainLooper().getThread()) {

            //Load类型的Action不加入队列，直接跳出
            action.run();
            return;
        }

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                enqueueAction(action);
            }
        });
    }

    private void enqueueAction(Action action) {
        //把Action加入到队列中
        mQueue.add(action);

        //当且仅当队列中的元素为1时才会执行
        if (mQueue.size() == 1) {
            handleAction();
        }
    }

    private void handleAction() {
        if (mQueue.isEmpty()) {
            return;
        }

        //peek()表示的是查看堆栈顶部的对象，但不从堆栈中移除它。
        Action action = mQueue.peek();
        action.run();

        executeNextAction(action);
    }

    private void executeNextAction(Action action) {
        if (action.action == Action.ACTION_POP) {
            //获取当前FragmentManager栈顶的Fragment，判断其进场/销毁时候的动画Duration
            ISupportFragment top = SupportHelper.getBackStackTopFragment(action.fragmentManager);
            action.duration = (top == null ? Action.DEFAULT_POP_TIME : top.getSupportDelegate().getExitAnimDuration());
        }

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行完之后把队列中的元素删除抛出
                mQueue.poll();
                //循环
                handleAction();
            }
        }, action.duration);
    }

    private boolean isThrottleBACK(Action action) {
        if (action.action == Action.ACTION_BACK) {
            Action head = mQueue.peek();
            if (head != null && head.action == Action.ACTION_POP) {
                return true;
            }
        }
        return false;
    }
}
