package com.hardy.person.copyqq.pull_load_refresh_util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author 马鹏昊
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class PullableListView extends ListView implements Pullable
{

    public PullableListView(Context context)
    {
        super(context);
    }

    public PullableListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean canPullDown()
    {
        if (getCount() == 0)
        {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0)
        {
            // 滑到ListView的顶部了,首先是最上面显示的是adapter中的第一项，其次是第一项也上滑到ListView顶部了
            // 试想如果只符合第一要求，但是第一项没有完全显示出来，即第一项的顶端减去ListView的顶端小于0，说明还未滑到顶端
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp()
    {
        if (getCount() == 0)
        {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1))//判断是否是数据中的最后一项
        {
            // 滑到底部了
            //第一个条件是判断当前显示在屏幕上的最后一个View是否为空，不能直接用getLastVisiblePosition()就
            // 行了，因为getChildAt()方法中的position是当前屏幕中显示的一组View的索引，而不是adapter中数据的索引
            //第二个条件判断距离父控件顶端的距离是否小于等于父控件的高度，如果是说明已经滑到底部
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}