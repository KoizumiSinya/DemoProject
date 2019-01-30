package jp.sinya.galleryviewpager.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import jp.sinya.galleryviewpager.R;
import jp.sinya.galleryviewpager.gallery.IWGalleryAdapter;

/**
 * 继承 IWGalleryAdapter 的 Adapter。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-05-10
 */
public class WGalleryAdapter extends BaseAdapter implements IWGalleryAdapter {

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sample, parent, false);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        iv.setImageResource(parent.getContext().getResources().getIdentifier("pic" + position, "drawable", parent.getContext().getPackageName()));
        return convertView;
    }

    @Override
    public int getChangeAlphaViewId() {
        return 0;
    }
}
