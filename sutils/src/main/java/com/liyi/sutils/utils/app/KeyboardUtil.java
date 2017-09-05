package com.liyi.sutils.utils.app;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * 与虚拟键盘相关的工具类
 */
public class KeyboardUtil {
    /**
     * 弹出虚拟软键盘
     */
    public static void openKeyboard(@NonNull Context context, EditText et) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 弹出虚拟软键盘并把布局顶上去
     */
    public static void openKeyboardByTop(@NonNull Context context, EditText et) {
        // The keyboard will top the layout
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(@NonNull Context context, EditText et) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * 判断软件盘是否弹出
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(@NonNull Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }
}
