package jp.sinya.stickylistviewdemo;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;

import static android.widget.AdapterView.OnItemClickListener;
import static jp.sinya.stickylistviewdemo.PinnedHeaderListView.PinnedHeaderAdapter;

/**
 * @author KoizumiSinya
 * @date 2016/4/8.
 */
public class BaseStickyAdapter implements ListAdapter,AbsListView.OnScrollListener,SectionIndexer,OnItemClickListener,PinnedHeaderAdapter {

    private SectionIndexer mIndexer;


    // [+] Override ListAdapter

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public synchronized int getCount() {
        return 0;
    }

    @Override
    public synchronized Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // [-] Override ListAdapter
    // [+] Override OnItemClickListener

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    // [-] Override OnItemClickListener
    // [+] Override PinnedHeaderAdapter

    @Override
    public int getPinnedHeaderState(int position) {
        return 0;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {

    }

    // [-] Override PinnedHeaderAdapter
    // [+] Override SectionIndexer

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    // [-] Override SectionIndexer
    // [+] Override OnScrollListener

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // [-] Override OnScrollListener
}
