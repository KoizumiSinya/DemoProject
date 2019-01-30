package jp.sinya.swipeback.demo.library4;

public interface SwipeListener {
    void onScroll(float percent, int px);

    void onEdgeTouch();

    /**
     * Invoke when scroll percent over the threshold for the first time
     */
    void onScrollToClose();

    void onScrollStateChange(int state, float px);
}