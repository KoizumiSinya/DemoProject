package com.example.demo_dialogmenu.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo_dialogmenu.R;

import animationslibrary.AnimationHelp;
import animationslibrary.Techniques;

/**
 * @Author: Sinya
 * @Editor:
 * @Date: 2015/11/12. 12:16
 * @Update:
 */
public class MyDialog {

    public static final int ONLY_DIALOG = 1;
    public static final int SELECT_DIALOG = 2;

    public static final int OK = 1;
    public static final int CANCEL = 2;
    public static int item;

    /**
     * @param context    上下文
     * @param title      标题
     * @param content    文本
     * @param DialogType 按钮显示个数（1为只有确定， 2为有取消 和 确实）
     * @param cancelable 是否可以点击空白处取消dialog
     * @return
     */
    public static int showDialog(Context context, String title, String content, int DialogType, boolean cancelable) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(cancelable);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.view_dialog_btn, null);
        dialog.setContentView(dialogView);

        if (title == null) {
            dialogView.findViewById(R.id.title).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) dialogView.findViewById(R.id.title)).setText(title);
        }

        TextView tx_content = (TextView) dialogView.findViewById(R.id.content);
        tx_content.setText(content);

        if (DialogType == ONLY_DIALOG) {
            dialogView.findViewById(R.id.cancel).setVisibility(View.GONE);
            dialogView.findViewById(R.id.divider).setVisibility(View.GONE);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                //AnimationHelp.AnimationOperation rope = AnimationHelp.with(Techniques.RotateOutDownLeft).duration(1000).playOn(dialogView);
                dialog.dismiss();

                if (id == R.id.cancel) {
                    item = 0;
                } else {
                    item = 1;
                }

                throw new RuntimeException();
            }
        };

        dialogView.findViewById(R.id.confirm).setOnClickListener(clickListener);
        dialogView.findViewById(R.id.cancel).setOnClickListener(clickListener);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//判断是否为横屏
            windowParams.width = getScreenHeight(context) / 10 * 8;
        } else {
            windowParams.width = getScreenWidth(context) / 10 * 8;
        }

        window.setAttributes(windowParams);
        dialog.show();
        //AnimationHelp.AnimationOperation rope = AnimationHelp.with(Techniques.RotateInDownLeft).duration(1000).playOn(dialogView);

        /*try {
            Looper.getMainLooper().loop();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }*/
        return item;
    }

    /**
     * @param context
     * @param title
     * @param items
     * @param cancelable
     * @param menuItemClickListener
     * @return
     */
    public static Dialog showMenu(Context context, String title, String[] items, boolean cancelable, final MenuItemClickListener menuItemClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(cancelable);

        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_menu, null);
        dialog.setContentView(view);

        if (title == null) {
            view.findViewById(R.id.title).setVisibility(View.GONE);
            view.findViewById(R.id.tx_title_divider).setVisibility(View.GONE);
        } else {
            ((TextView) view.findViewById(R.id.title)).setText(title);
        }

        LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);
        parent.removeAllViews();

        for (int i = 0; i <  items.length; i++) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(-1, -2);
            params1.rightMargin = 1;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setText(items[i]);
            tv.setTextSize(18);
            tv.setSingleLine(true);
            tv.setTextColor(context.getResources().getColor(R.color.dialog_text));
            int padding = dip2px(context, 10);
            tv.setPadding(padding, padding, padding, padding);
            tv.setGravity(Gravity.CENTER);

            //如果没有title
            if (title == null) {
                if (items.length == 1) {
                    tv.setBackgroundResource(R.drawable.dialog_menu_cancel_selector);
                }  else if (items.length >= 2) {
                    if (i == 0) {
                        tv.setBackgroundResource(R.drawable.dialog_menu_item_top_selector);
                    } else if (i == items.length - 1) {
                        tv.setBackgroundResource(R.drawable.dialog_menu_item_buttom_selector);
                    } else {
                        tv.setBackgroundResource(R.drawable.dialog_menu_item_center_selector);
                    }
                }

                //如果有title
            } else {
                if (items.length == 1) {
                    tv.setBackgroundResource(R.drawable.dialog_menu_item_buttom_selector);
                } else if (items.length >= 2) {
                    if (i != items.length - 1) {
                        tv.setBackgroundResource(R.drawable.dialog_menu_item_center_selector);
                    } else {
                        tv.setBackgroundResource(R.drawable.dialog_menu_item_buttom_selector);
                    }
                }
            }

            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    menuItemClickListener.confirm((int) tv.getTag());
                }
            });

            parent.addView(tv);

            if (i != items.length - 1) {
                TextView divider = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, 1);
                divider.setLayoutParams(params);
                divider.setBackgroundResource(android.R.color.darker_gray);
                parent.addView(divider);
            }
        }

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                menuItemClickListener.confirm(-1);
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                menuItemClickListener.confirm(-2);
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.width = getScreenWidth(context);
        window.setGravity(Gravity.BOTTOM);

        //添加动画效果
        window.setWindowAnimations(R.style.dialogAnim);
        window.setAttributes(windowParams);
        dialog.show();

        return dialog;
    }

    /**
     * 获取屏幕分辨率宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public interface DialogClickListener {
        public abstract void confirm(int item);
    }

    public interface MenuItemClickListener {
        public abstract void confirm(int item);
    }
}
