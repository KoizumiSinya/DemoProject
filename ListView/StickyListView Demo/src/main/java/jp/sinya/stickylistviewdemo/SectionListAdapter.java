package jp.sinya.stickylistviewdemo;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static jp.sinya.stickylistviewdemo.PinnedHeaderListView.*;


/**
 * Adapter for sections.
 */
public class SectionListAdapter implements ListAdapter, OnItemClickListener, PinnedHeaderAdapter, SectionIndexer, OnScrollListener {

    private SectionIndexer mIndexer;
    private String[] mSections;
    private int[] mCounts;
    private int mSectionCounts = 0;

    private final SectionListActivity.StandardArrayAdapter linkedAdapter;
    private final Map<String, View> currentViewSections = new HashMap<>();
    private int viewTypeCount;
    protected final LayoutInflater inflater;

    private View transparentSectionView;

    private OnItemClickListener linkedListener;

    private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateTotalCount();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            updateTotalCount();
        }
    };


    public SectionListAdapter(final LayoutInflater inflater, final SectionListActivity.StandardArrayAdapter linkedAdapter) {
        this.linkedAdapter = linkedAdapter;
        this.inflater = inflater;
        linkedAdapter.registerDataSetObserver(dataSetObserver);
        updateTotalCount();
        mIndexer = new MySectionIndexer(mSections, mCounts);
    }

    private boolean isTheSame(final String previousSection, final String newSection) {
        if (previousSection == null) {
            return newSection == null;
        } else {
            return previousSection.equals(newSection);
        }
    }

    private void fillSections() {
        mSections = new String[mSectionCounts];
        mCounts = new int[mSectionCounts];
        final int count = linkedAdapter.getCount();
        String currentSection = null;
        int sectionIndex = 0;
        int sectionCounts = 0;
        String previousSection = null;
        for (int i = 0; i < count; i++) {
            sectionCounts++;
            currentSection = linkedAdapter.items[i].section;
            if (!isTheSame(previousSection, currentSection)) {
                mSections[sectionIndex] = currentSection;
                previousSection = currentSection;

                if (sectionIndex == 0) {
                    mCounts[0] = 1;
                } else if (sectionIndex != 0) {
                    mCounts[sectionIndex - 1] = sectionCounts - 1;
                }
                if (i > 0) {
                    sectionCounts = 1;
                }
                sectionIndex++;
            }
            if (i == count - 1) {
                mCounts[sectionIndex - 1] = sectionCounts;
            }
        }
    }

    private synchronized void updateTotalCount() {
        String currentSection = null;
        viewTypeCount = linkedAdapter.getViewTypeCount() + 1;
        final int count = linkedAdapter.getCount();
        for (int i = 0; i < count; i++) {
            final SectionListItem item = linkedAdapter.getItem(i);
            if (!isTheSame(currentSection, item.section)) {
                mSectionCounts++;
                currentSection = item.section;
            }
        }
        fillSections();
    }

    @Override
    public synchronized int getCount() {
        return linkedAdapter.getCount();
    }

    @Override
    public synchronized Object getItem(final int position) {
        final int linkedItemPosition = getLinkedPosition(position);
        return linkedAdapter.getItem(linkedItemPosition);
    }

    public synchronized String getSectionName(final int position) {
        return null;
    }

    @Override
    public long getItemId(final int position) {
        return linkedAdapter.getItemId(getLinkedPosition(position));
    }

    protected Integer getLinkedPosition(final int position) {
        return position;
    }

    @Override
    public int getItemViewType(final int position) {
        return linkedAdapter.getItemViewType(getLinkedPosition(position));
    }

    private View getSectionView(final View convertView, final String section) {
        View theView = convertView;
        if (theView == null) {
        }
        setSectionText(section, theView);
        replaceSectionViewsInMaps(section, theView);
        return theView;
    }

    protected void setSectionText(final String section, final View sectionView) {
        final TextView textView = (TextView) sectionView.findViewById(R.id.header);
        textView.setText(section);
    }

    protected synchronized void replaceSectionViewsInMaps(final String section, final View theView) {
        if (currentViewSections.containsKey(theView)) {
            currentViewSections.remove(theView);
        }
        currentViewSections.put(section, theView);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            //装填ItemView的布局
            view = inflater.inflate(R.layout.section_list_item, null);
        }
        final SectionListItem currentItem = linkedAdapter.items[position];
        if (currentItem != null) {
            final TextView header = (TextView) view.findViewById(R.id.header);
            final TextView textView = (TextView) view.findViewById(R.id.example_text_view);
            if (textView != null) {
                textView.setText(currentItem.item.toString());
            }
            if (header != null) {
                header.setText(currentItem.section);
            }
            int section = getSectionForPosition(position);
            if (getPositionForSection(section) == position) {
                view.findViewById(R.id.header_parent).setVisibility(View.VISIBLE);
                header.setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.header_parent).setVisibility(View.GONE);
                header.setVisibility(View.GONE);
            }
        }

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkedListener != null) {
                    linkedListener.onItemClick(null, null, position, getItemId(position));
                }
            }
        });
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }

    @Override
    public boolean hasStableIds() {
        return linkedAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return linkedAdapter.isEmpty();
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        linkedAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        linkedAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return linkedAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(final int position) {
        return linkedAdapter.isEnabled(getLinkedPosition(position));
    }

    public int getRealPosition(int pos) {
        return pos - 1;
    }

    public synchronized View getTransparentSectionView() {
        if (transparentSectionView == null) {
        }
        return transparentSectionView;
    }

    protected void sectionClicked(final String section) {
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (linkedListener != null) {
            linkedListener.onItemClick(parent, view, getLinkedPosition(position), id);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener linkedListener) {
        this.linkedListener = linkedListener;
    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (mIndexer == null) {
            return PINNED_HEADER_GONE;
        }
        if (realPosition < 0) {
            return PINNED_HEADER_GONE;
        }
        int section = getSectionForPosition(realPosition);
        int nextSectionPosition = getPositionForSection(section + 1);
        if (nextSectionPosition != -1 && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        int realPosition = position;
        int section = getSectionForPosition(realPosition);

        String title = (String) mIndexer.getSections()[section];
        ((TextView) header.findViewById(R.id.header_text)).setText(title);

    }

    @Override
    public Object[] getSections() {
        if (mIndexer == null) {
            return new String[]{""};
        } else {
            return mIndexer.getSections();
        }
    }

    @Override
    public int getPositionForSection(int section) {
        if (mIndexer == null) {
            return -1;
        }
        return mIndexer.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        if (mIndexer == null) {
            return -1;
        }
        return mIndexer.getSectionForPosition(position);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }

    }

}
