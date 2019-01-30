package com.sinya.draglauncher.simple;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.sinya.draglauncher.R;
import com.sinya.draglauncher.luancherwidget.CancellableQueueTimer;
import com.sinya.draglauncher.luancherwidget.HitTestResult3;
import com.sinya.draglauncher.luancherwidget.IconMover;
import com.sinya.draglauncher.luancherwidget.JiggleModeActivator;
import com.sinya.draglauncher.luancherwidget.LayoutCalculator;
import com.sinya.draglauncher.luancherwidget.ObjectPool;
import com.sinya.draglauncher.luancherwidget.bean.ApplicationInfo;
import com.sinya.draglauncher.luancherwidget.bean.FolderInfo;
import com.sinya.draglauncher.luancherwidget.bean.ItemInfo;
import com.sinya.draglauncher.luancherwidget.impl.Controller;
import com.sinya.draglauncher.luancherwidget.impl.IPageView;
import com.sinya.draglauncher.luancherwidget.view.DotView;
import com.sinya.draglauncher.luancherwidget.view.FolderContentView;
import com.sinya.draglauncher.luancherwidget.view.FolderScrollView;
import com.sinya.draglauncher.luancherwidget.view.FolderView;
import com.sinya.draglauncher.luancherwidget.view.PageScrollView;
import com.sinya.draglauncher.luancherwidget.view.SpringBoardPage;
import com.sinya.draglauncher.simple.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class LauncherActivity extends Activity {

    private Context context;

    /**
     * 广播动作
     */
    private final static String RECEIVER_ADD_APP = "RECEIVER_ADD_APP";

    private int screenWidth;
    private int screenHeight;

    /**
     * 引用的xml布局文件
     */
    private View rootView;

    /**
     * xml布局下的根布局FrameLayout
     */
    private FrameLayout mFrame;

    /**
     * 用来装载 排列好的APP的LinearLayout
     */
    private LinearLayout scrollView;

    /**
     * scrollView 的父布局
     */
    private PageScrollView scrollContainer;

    /**
     * scrollContainer 的父布局
     */
    private RelativeLayout mContainer;

    /**
     * 存在于mContainer中，用来识别手势监听
     */
    private RelativeLayout mTouchController;

    /**
     * 指示点，存放于mContainer的底部
     */
    private DotView dotView;

    /**
     * 自定义控件 文件夹打开之后的View
     */
    private FolderView mFolderView;

    private List<ApplicationInfo> list;
    private Vector<ApplicationInfo[]> pages;

    /**
     * 手势识别管理器
     */
    private GestureDetector gestureDetector;

    /**
     * APP图标尺寸\间距\位置\等 数据的计算器
     */
    private LayoutCalculator layoutCalculator;

    /**
     *
     */
    private ObjectPool objectPool;

    /**
     * APP图标拖拽移动管理器
     */
    private IconMover iconMover;

    private int selectedPageIndex = 1;
    private int openFolderIndex = -1;

    /**
     * 触摸的速率
     */
    private float touchSlop;

    /**
     * 是否已经加载过数据
     */
    private boolean isLoaded;

    /**
     * 处于桌面状态时，点击的位置是否是Item以外的空白处
     */
    private boolean isDeskItemOut;

    /**
     * 处于打开文件夹状态时，点击的位置是否是文件夹中Item以外的空白处
     */
    private boolean isFolderItemOut;

    private HitTestResult3 hitTest2 = new HitTestResult3();
    private HitTestResult3 hitTest3 = new HitTestResult3();

    private Handler handler;

    // [+] 广播

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVER_ADD_APP.equals(intent.getAction())) {
                SpringBoardPage page = (SpringBoardPage) getPage(getPageCount(), false);
                if (page.getIconsCount() >= LayoutCalculator.iconsPerPage) {
                    page = addNewPage();
                }
                ApplicationInfo info = new ApplicationInfo();
                info.setTitle("应用" + 88);
                info.setOrder(88);
                info.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                page.clearUp(info);
                page.invalidate();
            } else if (ApplicationInfo.LOAD_ICON.equals(intent.getAction())) {
                if (mFolderView == null) {
                    for (int i = 1; i < getPageCount() + 1; i++) {
                        SpringBoardPage page = (SpringBoardPage) getPage(i, false);
                        page.invalidate();
                    }
                } else {
                    FolderContentView page = (FolderContentView) getPage(0, true);
                    page.invalidate();
                }
            }
        }
    };

    // [-] 广播
    // [+] 手势识别监听

    private OnGestureListener gestureListener = new OnGestureListener() {
        public boolean onDown(MotionEvent ev) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) <= 100.0F) {
                return false;
            }
            if (velocityX > 0) {
                scrollToLeft();
            } else {
                scrollToRight();
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
    };

    // [-] 手势识别监听
    // [+] 图标\文件夹 长按震动之后的操作监管器

    private JiggleModeActivator jma = new JiggleModeActivator() {
        public boolean isJigglable() {
            int i = scrollContainer.getScrollX();
            if ((i >= screenWidth) && (i % screenWidth == 0)) return true;
            return false;
        }

        public void jiggle() {
            setState(JiggleModeActivator.STATE_JIGGLE);
            if (mFolderView != null) {
                mFolderView.jiggle();
            } else {
                addNewPage();
                for (int i = 1; i < scrollView.getChildCount() - 1; i++) {
                    getPage(i, false).jiggle();
                }
            }
        }

        public void unjiggle() {
            setState(JiggleModeActivator.STATE_UNJIGGLE);
            for (int i = scrollView.getChildCount() - 2; i >= 1; i--) {
                IPageView page = getPage(i, false);
                page.unJiggle();
                if (page.getIconsCount() == 0) {
                    scrollView.removeViewAt(i);
                    if (getCurrentPageIndex() == i && getCurrentPageIndex() == getPageCount() + 1) {
                        scrollToLeft();
                    }
                }
            }
            dotView.setPages(getPageCount());
        }
    };

    // [-] 图标\文件夹 长按震动之后的操作监管器
    // [+] APP的操控器 是一个接口

    private Controller controller = new Controller() {

        @Override
        public void initData(final List<ApplicationInfo> list) {

            AsyncTask<String, String, String> tast = new AsyncTask<String, String, String>() {

                @Override
                protected String doInBackground(String... params) {

                    //模拟数据 添加11个app
                    int count = 25;
                    for (int i = 0; i <= count; i++) {
                        ApplicationInfo info = new ApplicationInfo();
                        info.setId(i + "");
                        info.setTitle("应用" + i);
                        info.setOrder(i);
                        if (i != count) {
                            info.setIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.module_0 + i));
                        } else {
                            info.setIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.module_9999));
                        }
                        list.add(info);
                    }

                    Collections.sort(list, new Comparator<ApplicationInfo>() {
                        public int compare(ApplicationInfo arg0, ApplicationInfo arg1) {
                            return arg0.getOrder() - arg1.getOrder();
                        }
                    });
                    return null;
                }

                protected void onPostExecute(String result) {
                    loaded();
                }

            };

            //execute方法被调用的时候就会去执行 onPostExecute方法
            tast.execute("");
        }

        /**
         * 替换顺序，放入\移出 文件夹、删除\增加 APP之后的保存操作
         */
        @Override
        public void onSynchronize() {
            Toast.makeText(LauncherActivity.this, "正在同步数据", Toast.LENGTH_SHORT).show();
        }

        /**
         * 点击APP
         * @param app
         */
        @Override
        public void onAppClick(ApplicationInfo app) {
            cancelJiggle();
            Toast.makeText(LauncherActivity.this, "点击了" + app.getTitle(), Toast.LENGTH_SHORT).show();
        }

        /**
         * 删除APP
         * @param app
         */
        @Override
        public void onAppRemove(ApplicationInfo app) {
            Toast.makeText(LauncherActivity.this, "删除" + app.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RECEIVER_ADD_APP);
            sendBroadcast(intent);
        }
    };

    // [-] APP的操控器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(LauncherActivity.this, R.layout.main_activity, null);
        setContentView(rootView);
        context = this;

        Log.i("Sinya", "LauncherActivity - onCreate()");

        initReceiver();
        initFindViewById();
        initHelper();
        initListener();
        initPagers();
    }

    // [+] 初始化相关数据

    /**
     * 初始化注册广播接收器
     */
    private void initReceiver() {
        IntentFilter filter = new IntentFilter(RECEIVER_ADD_APP);
        filter.addAction(ApplicationInfo.LOAD_ICON);
        registerReceiver(receiver, filter);
    }

    /**
     * 实例化布局中的控件
     */
    private void initFindViewById() {
        mFrame = (FrameLayout) findViewById(R.id.frame);
        scrollView = (LinearLayout) findViewById(R.id.container);
        scrollContainer = (PageScrollView) findViewById(R.id.pageView);
        dotView = (DotView) findViewById(R.id.dotView);
        mContainer = (RelativeLayout) findViewById(R.id.springboard_container);
        mTouchController = (RelativeLayout) findViewById(R.id.touchController);
    }

    /**
     * 实例化相关的操控类
     */
    private void initHelper() {
        list = new ArrayList<>();
        pages = new Vector<>();

        handler = new Handler();
        gestureDetector = new GestureDetector(context, this.gestureListener);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        layoutCalculator = new LayoutCalculator(context);
        objectPool = new ObjectPool(context, layoutCalculator);
        iconMover = new IconMover(rootView, layoutCalculator, objectPool, handler);
    }

    /**
     * 监听
     */
    private void initListener() {
        scrollContainer.setOnScrollChangedListener(scrollContainer_OnScrollChanged);
        mTouchController.setOnTouchListener(scrollContainer_OnTouch);
    }

    /**
     * 初始化pagers数据
     */
    private void initPagers() {
        dotView.init(layoutCalculator, objectPool);
        OnLayoutReady onLayoutReady = new OnLayoutReady();

        /**
         * 获取scrollView布局控件的视图树观察者，调用addOnGlobalLayoutListener方法。需要传入一个OnGlobalLayoutListener对象
         * ①addOnGlobalLayoutListener表示 当一个视图树在全局\布局 发生改变，或某个视图的可视状态发生改变的时候调用这个回调函数
         * ②也就是说，当initPagers方法结束时，正是Activity 的onCreate方法的方法内容运行完毕，
         * 这个时候作为布局控件之一的scrollView，由不可见变成可见。
         * 所以就会触发 实现了OnGlobalLayoutListener接口的类OnLayoutReady的onGlobalLayout（）方法
         */
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(onLayoutReady);
    }

    // [-] 初始化相关数据

    /**
     * Activity加载layout布局之后 获取layout的参数信息
     */
    private void layoutReady() {
        screenWidth = mFrame.getWidth();
        screenHeight = mFrame.getHeight();

        //将xml布局文件中的根布局mFrame传递给 LayoutCalculator 类，用于计算各类绘图数据
        layoutCalculator.layoutReady(mFrame);
    }

    /**
     * 设置scrollView的内容
     */
    private void setupScrollView() {

        //开启一个异步线程，加载数据
        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {

                //TODO 如果本地有数据，则从本地加载（意味着本地已经有图标资源数据）
                initDataFromDB(list);
                return "";
            }

            @Override
            protected void onPostExecute(String result) {

                loaded();

                if (!isLoaded) {
                    list.clear();
                    controller.initData(list);
                } else {

                    //TODO 如果没有请求网络数据，则同步本地数据
                    //controller.onSynchronize();

                }
            }
        };

        task.execute("");
    }

    public void loaded() {

        pages.clear();
        scrollView.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, -1);//-1不代表宽度，代表MATCH_PARENT常量的值

        LinearLayout emptyView = new LinearLayout(context);
        emptyView.setBackgroundDrawable(null);
        scrollView.addView(emptyView, params);

        LinearLayout emptyView2 = new LinearLayout(context);
        emptyView2.setBackgroundDrawable(null);
        scrollView.addView(emptyView2, params);

        ensurePages(1);

        //根据APP个数 确定pages页码
        for (int i = 0; i < list.size(); i++) {
            int page = i / LayoutCalculator.iconsPerPage + 1;
            int index = i % LayoutCalculator.iconsPerPage;
            ensurePages(page);

            ApplicationInfo[] infos = pages.get(page - 1);
            if ((index < infos.length) && (infos[index] == null)) {
                infos[index] = list.get(i);
            }
        }

        for (int i = 0; i < pages.size(); i++) {
            SpringBoardPage page = new SpringBoardPage(context);
            page.init(layoutCalculator, objectPool);
            page.setIcons(pages.get(i));
            scrollView.addView(page, scrollView.getChildCount() - 1, params);
        }

        scrollContainer.post(new Runnable() {

            @Override
            public void run() {
                scrollContainer.scrollTo(screenWidth, 0);
                scrollContainer.setVisibility(View.VISIBLE);
            }
        });

        dotView.setPages(getPageCount());
        dotView.setCurrentPage(0);
    }

    private void ensurePages(int count) {
        if (pages.size() < count) {
            addPage();
        }
    }

    private ApplicationInfo[] addPage() {
        ApplicationInfo[] infos = new ApplicationInfo[LayoutCalculator.rows * LayoutCalculator.columns];
        pages.add(infos);
        return infos;
    }

    private void scrollToCurrent(int index) {
        scrollContainer.smoothScrollTo(index * screenWidth, 0);
        dotView.setCurrentPage(index - 1);
    }

    public void scrollToLeft() {
        IPageView page = getPage(selectedPageIndex, false);
        if (page != null) {
            page.deselect();
        }
        if (--selectedPageIndex <= 1) {
            selectedPageIndex = 1;
        }
        scrollContainer.smoothScrollTo(selectedPageIndex * screenWidth, 0);
        dotView.setCurrentPage(selectedPageIndex - 1);
    }

    public void scrollToRight() {
        getPage(selectedPageIndex, false).deselect();
        if (++selectedPageIndex > scrollView.getChildCount() - 2) {
            selectedPageIndex = scrollView.getChildCount() - 2;
        }
        scrollContainer.smoothScrollTo(selectedPageIndex * screenWidth, 0);
        dotView.setCurrentPage(selectedPageIndex - 1);
    }

    private static double getDistance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return Math.sqrt(x * x + y * y);
    }

    // [+] 自定义OnTouch

    private OnTouchListener scrollContainer_OnTouch = new OnTouchListener() {

        private float x;
        private float y;

        /**
         * 是否截获 手势
         */
        boolean gdIntercept;
        private int scrollPointX;
        private int scrollPointY;

        IPageView currentPage;


        private int startIndex = -1;
        private boolean isDrag = false; //是否可以拖动

        private HitTestResult3 oldHitTest2 = new HitTestResult3();
        private FolderScrollView folderScrollView;

        private boolean isFolderActionDown = false;
        private boolean isDesktopActionDown = false;

        // [+] 多种操作的定时任务器

        private CancellableQueueTimer jiggleModeWaiter;
        private CancellableQueueTimer moveToScrollWaiter;
        private CancellableQueueTimer moveIntoFolderWaiter;
        private CancellableQueueTimer startDragWaiter;
        private CancellableQueueTimer moveIconWaiter;
        private CancellableQueueTimer moveToDesktopWaiter;

        private Runnable jiggleDetacher = new Runnable() {

            @Override
            public void run() {
                if (mFolderView != null) {
                    mFolderView.jiggle();
                } else {
                    jma.jiggle();
                }
                isDeskItemOut = false;
            }
        };

        private Runnable scrollToLeftDetacher = new Runnable() {

            @Override
            public void run() {
                if (iconMover.isAboveFolder()) {
                    iconMover.bisideFolder();
                    currentPage.removeFolderBound();
                    if (moveIntoFolderWaiter != null) {
                        moveIntoFolderWaiter.cancel();
                        moveIntoFolderWaiter = null;
                    }
                }
                scrollToLeft();
            }
        };

        private Runnable scrollToRightDetacher = new Runnable() {

            @Override
            public void run() {
                if (iconMover.isAboveFolder()) {
                    iconMover.bisideFolder();
                    currentPage.removeFolderBound();
                    if (moveIntoFolderWaiter != null) {
                        moveIntoFolderWaiter.cancel();
                        moveIntoFolderWaiter = null;
                    }
                }
                scrollToRight();
            }
        };

        private Runnable startDragDetacher = new Runnable() {

            @Override
            public void run() {
                isDrag = true;
                if (currentPage != null) {
                    detachIcon(currentPage, startIndex, currentPage.getSelectedIndex(), mFolderView != null);
                }
            }
        };

        private Runnable moveIconDetacher = new Runnable() {
            HitTestResult3 oldHitTest = new HitTestResult3();

            @Override
            public void run() {
                if (hitTest2.index >= 0) {
                    if (oldHitTest.index != hitTest2.index || oldHitTest.inIcon != hitTest2.inIcon) {
                        if (iconMover.isAboveFolder()) {
                            iconMover.bisideFolder();
                            currentPage.removeFolderBound();
                            if (moveIntoFolderWaiter != null) {
                                moveIntoFolderWaiter.cancel();
                                moveIntoFolderWaiter = null;
                            }
                        }
                        oldHitTest.index = hitTest2.index;
                        oldHitTest.inIcon = hitTest2.inIcon;
                    }
                    if (!hitTest2.inIcon) {
                        if (iconMover.isAboveFolder()) {
                            iconMover.bisideFolder();
                            currentPage.removeFolderBound();
                            if (moveIntoFolderWaiter != null) {
                                moveIntoFolderWaiter.cancel();
                                moveIntoFolderWaiter = null;
                            }
                        }
                        if (currentPage.setMoveTo(hitTest2.index)) {
                            if (mFolderView != null) {
                                mFolderView.initLayout();
                            }
                            iconMover.setIndex(hitTest2.index);
                            iconMover.setPageIndex(selectedPageIndex);
                            iconMover.setsIndex(hitTest2.index);
                            iconMover.setsPageIndex(selectedPageIndex);
                        } else {
                            iconMover.setIndex(iconMover.getsIndex());
                            iconMover.setPageIndex(iconMover.getsPageIndex());
                        }
                    } else {
                        if (!iconMover.isAboveFolder()) {
                            iconMover.aboveFolder();
                            iconMover.setIndex(hitTest2.index);
                            iconMover.setPageIndex(selectedPageIndex);
                            currentPage.createFolderBound(hitTest2.index);
                            if (moveIntoFolderWaiter == null) {
                                moveIntoFolderWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), moveIntoFolderDetacher);
                            }
                        }
                    }
                } else {
                    iconMover.setIndex(iconMover.getsIndex());
                    iconMover.setPageIndex(iconMover.getsPageIndex());
                }
                moveIconWaiter = null;
            }
        };

        private Runnable moveIntoFolderDetacher = new Runnable() {

            @Override
            public void run() {
                if (hitTest2.index > 0) {
                    IPageView page = getPage(selectedPageIndex, false);
                    ApplicationInfo info = page.getIcon(hitTest2.index);
                    if (info != null && info.getType() == ItemInfo.TYPE_FOLDER) {
                        FolderInfo folder = (FolderInfo) info;
                        openFolderIndex = hitTest2.index;
                        openFolder(folder);
                    }
                }
                moveIntoFolderWaiter = null;
            }
        };

        private Runnable moveToDesktopDetacher = new Runnable() {

            @Override
            public void run() {
                currentPage.clearUp(null);
                closeFolder();
                getPage(selectedPageIndex, false).clearUp(null);
            }
        };

        // [-] 多种操作的定时任务器

        @Override
        public boolean onTouch(View v, final MotionEvent ev) {
            hitTest2.index = -1;
            hitTest2.inIcon = false;
            hitTest2.buttonRemove = false;

            if (mFolderView != null) {
                return onTouchFolder(v, ev);
            }

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (iconMover.isMoving()) {
                        return false;
                    }

                    isDrag = false;
                    oldHitTest2.index = -1;
                    oldHitTest2.inIcon = false;
                    iconMover.setAboveFolder(false);

                    x = ev.getX();
                    y = ev.getY();

                    scrollPointX = scrollContainer.getScrollX();
                    int index = scrollPointX / screenWidth;
                    startIndex = index;
                    currentPage = getPage(index, false);

                    isDesktopActionDown = true;
                    isFolderActionDown = false;

                    if (currentPage != null) {
                        currentPage.hitTest3((int) x, (int) y, hitTest3);

                        if (hitTest3.index >= 0) {
                            currentPage.select(hitTest3.index);
                            ApplicationInfo info = currentPage.getIcon(hitTest3.index);

                            if (info != null) {
                                if (jma.isJiggling()) {
                                    if (!hitTest3.buttonRemove) {
                                        if (startDragWaiter == null) {
                                            startDragWaiter = new CancellableQueueTimer(handler, 200, startDragDetacher);
                                        }
                                    }
                                } else {
                                    hitTest3.buttonRemove = false;
                                }
                            } else {
                                isDeskItemOut = true;
                                Log.i("Sinya", "点击了Item为null的空白处");
                            }
                        } else {
                            isDeskItemOut = true;
                            Log.i("Sinya", "点击了不是Item所在的位置的空白处");
                        }
                    }

                    gdIntercept = gestureDetector.onTouchEvent(ev);
                    if (!jma.isJiggling() && jiggleModeWaiter == null) {
                        //通过获取手势参数，提取按下之后的时间。监听是否会产生长按事件
                        jiggleModeWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), jiggleDetacher);
                    }
                    return true;

                case MotionEvent.ACTION_MOVE:

                    int currentPointX = scrollContainer.getScrollX();
                    index = currentPointX / screenWidth;
                    currentPage = getPage(index, false);

                    if (!isDesktopActionDown) {
                        x = ev.getX();
                        y = ev.getY();
                        scrollPointX = scrollContainer.getScrollX();
                        startIndex = index;
                        isFolderActionDown = false;
                        isDesktopActionDown = true;
                    }

                    if (!gdIntercept) {
                        if (jma.isJiggling()) {
                            if (getDistance(ev.getX(), ev.getY(), x, y) <= touchSlop) {
                                if (currentPage != null) {
                                    if (isDrag) {
                                        detachIcon(currentPage, startIndex, currentPage.getSelectedIndex(), false);
                                    }
                                }
                            } else {
                                if (currentPage != null) {
                                    currentPage.deselect();
                                }
                                if (startDragWaiter != null) {
                                    startDragWaiter.cancel();
                                    startDragWaiter = null;
                                }
                                hitTest3.buttonRemove = false;
                            }

                            if (iconMover.isMoving()) {

                                isDeskItemOut = false;

                                Point point = new Point((int) ev.getX(), (int) ev.getY());
                                iconMover.moveTo(point.x, point.y);
                                mFrame.invalidate(iconMover.getBounds());
                                if (currentPage != null) {
                                    int position = currentPage.hitTest2(point.x, point.y, hitTest2, iconMover.hook().getType() == ItemInfo.TYPE_FOLDER);
                                    if (position == -1) {
                                        if (moveToScrollWaiter == null) {
                                            moveToScrollWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), scrollToLeftDetacher);
                                        }
                                        return true;
                                    } else if (position == 1) {
                                        if (moveToScrollWaiter == null) {
                                            moveToScrollWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), scrollToRightDetacher);
                                        }
                                        return true;
                                    } else if (position == 0) {
                                        if (hitTest2.index >= 0) {
                                            if (oldHitTest2.index != hitTest2.index || oldHitTest2.inIcon != hitTest2.inIcon) {
                                                oldHitTest2.index = hitTest2.index;
                                                oldHitTest2.inIcon = hitTest2.inIcon;
                                                if (moveIconWaiter != null) {
                                                    moveIconWaiter.cancel();
                                                    moveIconWaiter = null;
                                                }
                                                moveIconWaiter = new CancellableQueueTimer(handler, 100, moveIconDetacher);
                                            }
                                        }
                                    } else {
                                        if (moveIconWaiter != null) {
                                            moveIconWaiter.cancel();
                                            moveIconWaiter = null;
                                        }
                                        if (iconMover.isAboveFolder()) {
                                            iconMover.bisideFolder();
                                            iconMover.setIndex(iconMover.getsIndex());
                                            iconMover.setPageIndex(iconMover.getsPageIndex());
                                            currentPage.removeFolderBound();
                                            if (moveIntoFolderWaiter != null) {
                                                moveIntoFolderWaiter.cancel();
                                                moveIntoFolderWaiter = null;
                                            }
                                        }
                                    }
                                    if (moveToScrollWaiter != null) {
                                        moveToScrollWaiter.cancel();
                                        moveToScrollWaiter = null;
                                    }
                                }

                                return true;

                            } else {
                                scrollContainer.scrollTo((int) (scrollPointX - (ev.getX() - x)), 0);
                            }
                        } else {
                            scrollContainer.scrollTo((int) (scrollPointX - (ev.getX() - x)), 0);
                            if (getDistance(ev.getX(), ev.getY(), x, y) > touchSlop) {
                                if (jiggleModeWaiter != null) {
                                    jiggleModeWaiter.cancel();
                                    jiggleModeWaiter = null;
                                }
                                if (currentPage != null) {
                                    if (currentPage.getSelectedIndex() >= 0) {
                                        currentPage.deselect();
                                    }
                                }
                            }
                        }
                    }

                    if (isOverScrolling()) {
                        isDeskItemOut = false;
                        float f = ev.getX() - x;
                        scrollContainer.scrollTo((int) (scrollPointX - f / 2.0F), 0);
                        return true;
                    }

                    gdIntercept = gestureDetector.onTouchEvent(ev);
                    break;

                case MotionEvent.ACTION_CANCEL:
                    if (currentPage != null) {
                        currentPage.deselect();
                    }

                case MotionEvent.ACTION_UP:

                    //TODO 手指离开屏幕时 要做针对是否是点击了Item以外的位置进行判断
                    if (isDeskItemOut) {
                        if (jma.isJiggling()) {
                            jma.unjiggle();
                        }else {
                            if (mFolderView != null) {
                                if (mFolderView.isJiggling()) {
                                    mFolderView.unJiggle();
                                }else{
                                    closeFolder();
                                }
                            }
                        }
                    }
                    isDeskItemOut = false;

                    if (jiggleModeWaiter != null) {
                        jiggleModeWaiter.cancel();
                        jiggleModeWaiter = null;
                    }
                    if (moveToScrollWaiter != null) {
                        moveToScrollWaiter.cancel();
                        moveToScrollWaiter = null;
                    }
                    if (startDragWaiter != null) {
                        startDragWaiter.cancel();
                        startDragWaiter = null;
                    }

                    gdIntercept = gestureDetector.onTouchEvent(ev);

                    if (isOverScrolling()) {
                        scrollToCurrent(selectedPageIndex);
                    } else {
                        if (!gdIntercept && !iconMover.isMoving()) {
                            if (Math.abs((ev.getX() - x)) > screenWidth / 2) {
                                if (ev.getX() - x > 0) {
                                    scrollToLeft();
                                } else {
                                    scrollToRight();
                                }
                            } else {
                                scrollToCurrent(selectedPageIndex);
                            }
                        }
                    }

                    isDesktopActionDown = false;
                    isFolderActionDown = false;

                    final IPageView currentPage = getPage(selectedPageIndex, false);
                    if (currentPage != null) {
                        final int select = currentPage.getSelectedIndex();
                        if (select >= 0) {
                            ApplicationInfo info = currentPage.getSelectedApp();
                            if (info != null) {
                                if (!jma.isJiggling()) {
                                    if (info.getType() == ItemInfo.TYPE_FOLDER) {
                                        openFolderIndex = select;
                                        openFolder((FolderInfo) info);
                                    } else {
                                        controller.onAppClick(info);
                                    }
                                } else {

                                    //TODO 在这里可以添加需要的条件，防止APP图标在编辑操作状态下 文件夹会被删除
                                    if (hitTest3.buttonRemove) {
                                        currentPage.removeApp(hitTest3.index);
                                        controller.onAppRemove(info);
                                    }

                                    //TODO 手指离开屏幕后，文件夹会打开的依据
                                    if (info.getType() == ItemInfo.TYPE_FOLDER && !isDrag) {
                                        openFolderIndex = select;
                                        openFolder((FolderInfo) info);
                                    }
                                }
                            }
                        }
                        currentPage.deselect();
                    }

                    if (iconMover.isMoving()) {
                        for (int i = 1; i < getPageCount() + 1; i++) {
                            if (i != iconMover.getPageIndex()) {
                                getPage(i, false).clearUp(null);
                            }
                        }

                        final IPageView p = getPage(iconMover.getPageIndex(), false);
                        if (p != null) {
                            Point point = p.getIconLocation(iconMover.getIndex());
                            final ApplicationInfo app = iconMover.hook();
                            final int i = iconMover.getIndex();

                            if (!iconMover.isAboveFolder()) {
                                iconMover.stopMoving((iconMover.getPageIndex() - selectedPageIndex) * screenWidth + point.x, point.y, new IconMover.OnMovingStopped() {
                                    @Override
                                    public void movingStopped(ApplicationInfo appInfo) {
                                        p.clearUp(app);
                                    }
                                });
                            } else {
                                iconMover.setAboveFolder(false);
                                p.removeFolderBound();
                                iconMover.moveIntoFolder((iconMover.getPageIndex() - selectedPageIndex) * screenWidth + point.x, point.y, new IconMover.OnMovingStopped() {
                                    @Override
                                    public void movingStopped(ApplicationInfo appInfo) {
                                        p.addToFolder(i, app);
                                        p.clearUp(null);
                                    }
                                });
                            }
                        }
                    }
                    startIndex = -1;
                    return true;
            }
            return false;
        }

        public boolean onTouchFolder(View v, final MotionEvent ev) {
            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (iconMover.isMoving()) {
                        return false;
                    }

                    x = ev.getX();
                    y = ev.getY();

                    isDrag = false;
                    oldHitTest2.index = -1;
                    oldHitTest2.inIcon = false;
                    folderScrollView = mFolderView.getScrollView();
                    iconMover.setAboveFolder(false);
                    scrollPointY = folderScrollView.getScrollY();
                    currentPage = getPage(0, true);

                    if (x < mFolderView.getTranslateLeft() || y < mFolderView.getTranslateTop() ||//
                            x > folderScrollView.getWidth() + mFolderView.getTranslateLeft() ||//
                            y > folderScrollView.getHeight() + mFolderView.getTranslateTop()) {
                        closeFolder();
                    }

                    isDesktopActionDown = false;
                    isFolderActionDown = true;

                    if (currentPage != null) {
                        currentPage.hitTest3((int) x - mFolderView.getTranslateLeft(), (int) y - mFolderView.getTranslateTop() + scrollPointY, hitTest3);

                        if (hitTest3.index >= 0) {
                            currentPage.select(hitTest3.index);
                            ApplicationInfo info = currentPage.getIcon(hitTest3.index);

                            if (info != null) {
                                if (mFolderView.isJiggling()) {
                                    if (!hitTest3.buttonRemove) {
                                        if (startDragWaiter == null) {
                                            startDragWaiter = new CancellableQueueTimer(handler, 200, startDragDetacher);
                                        }
                                    }
                                } else {
                                    hitTest3.buttonRemove = false;
                                }
                            } else {
                                LogUtils.I("Sinya", "点击了文件夹的Item为null的空白处");
                            }
                        } else {
                            LogUtils.I("Sinya", "点击了文件夹中的空白处");
                        }

                    }

                    //通过手势获取时间，判断是否会触发长按
                    if (!mFolderView.isJiggling() && jiggleModeWaiter == null) {
                        jiggleModeWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), jiggleDetacher);
                    }

                    return true;

                case MotionEvent.ACTION_MOVE:

                    if (!isFolderActionDown) {
                        folderScrollView = mFolderView.getScrollView();
                        x = ev.getX();
                        y = ev.getY();
                        iconMover.setAboveFolder(false);
                        scrollPointY = folderScrollView.getScrollY();
                        currentPage = getPage(0, true);
                        isFolderActionDown = true;
                        isDesktopActionDown = false;
                    }

                    if (mFolderView.isJiggling()) {
                        if (getDistance(ev.getX(), ev.getY(), x, y) <= touchSlop) {
                            if (currentPage != null) {
                                if (isDrag) {
                                    detachIcon(currentPage, startIndex, currentPage.getSelectedIndex(), true);
                                }
                            }
                        } else {
                            if (currentPage != null) {
                                currentPage.deselect();
                            }
                            if (startDragWaiter != null) {
                                startDragWaiter.cancel();
                                startDragWaiter = null;
                            }
                            hitTest3.buttonRemove = false;
                        }

                        if (iconMover.isMoving()) {
                            Point point = new Point((int) ev.getX(), (int) ev.getY());
                            iconMover.moveTo(point.x, point.y);
                            mFolderView.invalidate(iconMover.getBounds());
                            if (currentPage != null) {
                                if (point.x < mFolderView.getTranslateLeft() || point.y < mFolderView.getTranslateTop() || point.x > folderScrollView.getWidth() + mFolderView.getTranslateLeft() || point.y > folderScrollView.getHeight() + mFolderView.getTranslateTop()) {
                                    if (moveToDesktopWaiter == null) {
                                        moveToDesktopWaiter = new CancellableQueueTimer(handler, ViewConfiguration.getLongPressTimeout(), moveToDesktopDetacher);
                                    }
                                } else {
                                    if (moveToDesktopWaiter != null) {
                                        moveToDesktopWaiter.cancel();
                                        moveToDesktopWaiter = null;
                                    }
                                }
                                if (point.y - mFolderView.getTranslateTop() < folderScrollView.getHeight() / 3) {
                                    folderScrollView.scrollBy(0, -10);
                                    scrollPointY = folderScrollView.getScrollY();
                                }
                                if (point.y - mFolderView.getTranslateTop() > folderScrollView.getHeight() * 2 / 3) {
                                    mFolderView.getScrollView().scrollBy(0, 10);
                                    scrollPointY = folderScrollView.getScrollY();
                                }
                                int position = currentPage.hitTest2(point.x - mFolderView.getTranslateLeft(), point.y - mFolderView.getTranslateTop() + scrollPointY, hitTest2, false);
                                if (position == 0) {
                                    if (hitTest2.index >= 0) {
                                        if (oldHitTest2.index != hitTest2.index || oldHitTest2.inIcon != hitTest2.inIcon) {
                                            oldHitTest2.index = hitTest2.index;
                                            oldHitTest2.inIcon = hitTest2.inIcon;
                                            if (moveIconWaiter != null) {
                                                moveIconWaiter.cancel();
                                                moveIconWaiter = null;
                                            }
                                            moveIconWaiter = new CancellableQueueTimer(handler, 100, moveIconDetacher);
                                        }
                                    }
                                } else {
                                    if (moveIconWaiter != null) {
                                        moveIconWaiter.cancel();
                                        moveIconWaiter = null;
                                    }
                                    if (iconMover.isAboveFolder()) {
                                        iconMover.bisideFolder();
                                        iconMover.setIndex(iconMover.getsIndex());
                                        iconMover.setPageIndex(iconMover.getsPageIndex());
                                        currentPage.removeFolderBound();
                                        if (moveIntoFolderWaiter != null) {
                                            moveIntoFolderWaiter.cancel();
                                            moveIntoFolderWaiter = null;
                                        }
                                    }
                                }
                                if (moveToScrollWaiter != null) {
                                    moveToScrollWaiter.cancel();
                                    moveToScrollWaiter = null;
                                }
                            }
                            return true;
                        } else {

                            //文件夹打开并且处于编辑状态下，滑动FolderView
                            folderScrollView.scrollTo(0, (int) (scrollPointY - (ev.getY() - y)));
                        }
                    } else {
                        //文件夹打开的时候，滑动FolderView
                        folderScrollView.scrollTo(0, (int) (scrollPointY - (ev.getY() - y)));
                        if (getDistance(ev.getX(), ev.getY(), x, y) > touchSlop) {
                            if (jiggleModeWaiter != null) {
                                jiggleModeWaiter.cancel();
                                jiggleModeWaiter = null;
                            }
                            if (currentPage != null) {
                                if (currentPage.getSelectedIndex() >= 0) {
                                    currentPage.deselect();
                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    currentPage.deselect();

                case MotionEvent.ACTION_UP:

                    //TODO 手指离开屏幕后 处理点击空白处的操作

                    isDesktopActionDown = false;
                    isFolderActionDown = false;
                    if (jiggleModeWaiter != null) {
                        jiggleModeWaiter.cancel();
                        jiggleModeWaiter = null;
                    }
                    if (moveToScrollWaiter != null) {
                        moveToScrollWaiter.cancel();
                        moveToScrollWaiter = null;
                    }
                    if (startDragWaiter != null) {
                        startDragWaiter.cancel();
                        startDragWaiter = null;
                    }
                    if (moveToDesktopWaiter != null) {
                        moveToDesktopWaiter.cancel();
                        moveToDesktopWaiter = null;
                    }

                    final IPageView currentPage = getPage(selectedPageIndex, true);
                    if (currentPage != null) {
                        final int select = currentPage.getSelectedIndex();
                        if (select >= 0) {
                            ApplicationInfo info = currentPage.getSelectedApp();
                            if (info != null) {
                                if (!mFolderView.isJiggling()) {
                                    controller.onAppClick(info);
                                } else {
                                    if (hitTest3.buttonRemove) {
                                        currentPage.removeApp(hitTest3.index);
                                        controller.onAppRemove(info);
                                    }
                                }
                            }
                        }
                        currentPage.deselect();
                    }
                    if (iconMover.isMoving()) {
                        final IPageView p = getPage(iconMover.getPageIndex(), true);
                        Point point = p.getIconLocation(iconMover.getIndex());
                        final ApplicationInfo app = iconMover.hook();
                        iconMover.stopMoving(point.x + mFolderView.getTranslateLeft(), point.y + mFolderView.getTranslateTop() - scrollPointY, new IconMover.OnMovingStopped() {
                            @Override
                            public void movingStopped(ApplicationInfo appInfo) {
                                p.clearUp(app);
                            }
                        });
                    }
                    startIndex = -1;
                    return true;
            }
            return false;
        }

        private void detachIcon(IPageView page, int pageIndex, int index, boolean isFolder) {

            ApplicationInfo info = page.getIcon(index);
            if (info == null) return;
            page.deselect();
            Point point = page.getIconLocation(index);
            if (!iconMover.isMoving()) {
                if (isFolder) {
                    iconMover.startMoving(info, point.x + mFolderView.getTranslateLeft(), mFolderView.getTranslateTop() + point.y - folderScrollView.getScrollY(), (int) x, (int) y);
                } else {
                    iconMover.startMoving(info, point.x, point.y, (int) x, (int) y);
                    iconMover.setPageIndex(pageIndex);
                    iconMover.setsPageIndex(pageIndex);
                }
                iconMover.setIndex(index);
                iconMover.setsIndex(index);
            }
            page.setIconIntoPage(index, null);
        }
    };

    // [-] 自定义OnTouch

    /**
     * 同步数据
     */
    private void onSynchronize() {
        boolean isMessed = false;
        if (mFolderView != null) {
            IPageView page = getPage(0, true);
            if (page.isMessed()) {
                isMessed = true;
                page.setMessed(false);
            }
        }
        for (int i = 1; i < getPageCount() + 1; i++) {
            IPageView page = getPage(i, false);
            if (page.isMessed()) {
                isMessed = true;
                page.setMessed(false);
            }
        }
        if (isMessed) {
            onSynchronizeDB();
            controller.onSynchronize();
        }
    }

    /**
     * 保存APP图标\文件夹 拖动\替换\删除\增加等操作之后的数据到本地
     */
    private void onSynchronizeDB() {

    }

    /**
     * 判断是否是停止滑动
     * @return
     */
    private boolean isOverScrolling() {
        int x = scrollContainer.getScrollX();
        if (x < screenWidth) return true;
        else {
            if (x > (scrollView.getChildCount() - 2) * this.screenWidth) {
                return true;
            }
        }
        return false;
    }

    public static abstract interface OnPageScrollListener {
        public abstract void onPageScroll(int paramInt);
    }

    /**
     * 获取当前页控件对象
     * @param index
     * @param isFolder
     * @return
     */
    public IPageView getPage(int index, boolean isFolder) {
        IPageView page = null;
        if (isFolder && mFolderView != null) {
            return mFolderView.getContentView();
        }
        if ((index <= 0) || (index > getPageCount())) return null;
        View view = scrollView.getChildAt(index);
        if (view instanceof SpringBoardPage) page = (SpringBoardPage) view;
        return page;
    }

    /**
     * 获取当前页 APP图标个数
     * @return
     */
    public int getPageCount() {
        return scrollView.getChildCount() - 2;
    }

    private OnScrollChangedListener scrollContainer_OnScrollChanged = new OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            isDeskItemOut = false;
        }
    };

    /**
     * 获取当前pager页码
     * @return
     */
    public int getCurrentPageIndex() {
        return scrollContainer.getScrollX() / this.screenWidth;
    }

    public SpringBoardPage addNewPage() {
        if (getPage(scrollView.getChildCount() - 2, false).getIconsCount() == 0) {
            return null;
        }
        SpringBoardPage page = new SpringBoardPage(LauncherActivity.this);
        page.init(this.layoutCalculator, this.objectPool);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.screenWidth, -1);
        scrollView.addView(page, -1 + this.scrollView.getChildCount(), layoutParams);
        dotView.setPages(getPageCount());
        return page;
    }

    /**
     * 关闭文件夹
     */
    private void closeFolder() {
        SpringBoardPage page = (SpringBoardPage) getPage(selectedPageIndex, false);
        Transformation transformation = new Transformation();
        if (mFolderView != null && !mFolderView.getAnimation().getTransformation(AnimationUtils.currentAnimationTimeMillis(), transformation)) {
            Point point = page.getIconLocation(openFolderIndex);
            Animation animation = objectPool.createAnimationOpenFolder(point.x, point.y, screenWidth, screenHeight, false);
            animation.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    boolean isJiggling = mFolderView.isJiggling();
                    mContainer.removeView(mFolderView);
                    mFolderView = null;
                    if (isJiggling) {
                        jma.jiggle();
                    }
                    getPage(selectedPageIndex, false).clearUp(null);
                }
            });
            if (mFolderView != null) {
                mFolderView.startAnimation(animation);
            }
            Animation pageAnimation = objectPool.createAnimationPageShow(true);
            page.startAnimation(pageAnimation);
            dotView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 打开文件夹
     *
     * @param folderInfo
     */
    private void openFolder(FolderInfo folderInfo) {
        if (mFolderView == null) {
            SpringBoardPage page = (SpringBoardPage) getPage(selectedPageIndex, false);
            int index;
            if (hitTest2.index != -1) {
                index = hitTest2.index;
            } else {
                index = page.getSelectedIndex();
            }
            Point point = page.getIconLocation(index);
            dotView.setVisibility(View.INVISIBLE);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mFolderView = folderInfo.getFolderView(LauncherActivity.this);
            if (mFolderView.getParent() == null) {
                mContainer.addView(mFolderView, params);
            }
            mFolderView.onReday(layoutCalculator, objectPool, screenHeight);
            Animation folderAnimation = objectPool.createAnimationOpenFolder(point.x, point.y, screenWidth, screenHeight, true);
            mFolderView.startAnimation(folderAnimation);
            Animation pageAnimation = objectPool.createAnimationPageShow(false);
            page.startAnimation(pageAnimation);
            page.removeFolderBound();
            page.clearUp(null);
            if (jma.isJiggling()) {
                mFolderView.jiggle();
            } else {
                mFolderView.unJiggle();
            }
            jma.unjiggle();
        }
    }

    /**
     * 取消APP的编辑中状态
     */
    private void cancelJiggle() {
        if (jma.isJiggling()) {
            jma.unjiggle();
        } else {
            if (mFolderView != null) {
                if (mFolderView.isJiggling()) {
                    mFolderView.unJiggle();
                } else {
                    // closeFolder();
                }
            }
        }
    }

    /**
     * 从本地加载APP图标数据
     *
     * @param list
     */
    private void initDataFromDB(List<ApplicationInfo> list) {

        //模拟数据 添加11个app
        int count = 5;
        for (int i = 0; i <= count; i++) {
            ApplicationInfo info = new ApplicationInfo();
            info.setId(i + "");
            info.setTitle("应用" + i);
            info.setOrder(i);
            if (i != count) {
                info.setIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.module_0 + i));
            } else {
                info.setIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.module_9999));
            }
            list.add(info);
        }

        Collections.sort(list, new Comparator<ApplicationInfo>() {
            public int compare(ApplicationInfo arg0, ApplicationInfo arg1) {
                return arg0.getOrder() - arg1.getOrder();
            }
        });

        if (list.size() > 0) {
            isLoaded = true;
        }

    }

    // [+] Activity Override

    @Override
    public void onBackPressed() {
        if (jma.isJiggling()) {
            jma.unjiggle();
            IPageView page = getPage(selectedPageIndex, false);
            if (page.isMessed()) {
                onSynchronize();
            }
        } else {
            if (mFolderView != null) {
                if (mFolderView.isJiggling()) {
                    mFolderView.unJiggle();
                    onSynchronize();
                } else {
                    closeFolder();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        closeFolder();
        if (jma.isJiggling()) {
            jma.unjiggle();
        }
        selectedPageIndex = 1;
        scrollToCurrent(selectedPageIndex);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        LauncherActivity.this.unregisterReceiver(receiver);
        super.onDestroy();
    }

    // [-] Activity Override
    // [+] 内部类

    private class OnLayoutReady implements OnGlobalLayoutListener {

        private OnLayoutReady() {

        }

        @Override
        public void onGlobalLayout() {
            //当这个观察者的重写方法被启动之后，就将其解除。意思是加载第一次的时候才会执行，之后就不会再执行。
            scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            layoutReady();
            setupScrollView();
        }
    }

    // [-] 内部类
}
