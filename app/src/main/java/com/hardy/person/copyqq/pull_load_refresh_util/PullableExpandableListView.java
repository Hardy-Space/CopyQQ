package com.hardy.person.copyqq.pull_load_refresh_util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @author 马鹏昊
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class PullableExpandableListView extends ExpandableListView implements
        Pullable
{

    public PullableExpandableListView(Context context)
    {
        super(context);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs,
                                      int defStyle)
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
            // 滑到顶部了
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
        } else if (getLastVisiblePosition() == (getCount() - 1))
        {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }


/*
    实现onGroupClickListener接口，重写onGroupClick这个方法,可以控制展开列表时不滚动
    经试验证明在这里面重写不管用，要去setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(parent.isGroupExpanded(groupPosition)){
                    parent.collapseGroup(groupPosition);
                }else{
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition,false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        }););才行！！！
*/
   /* @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if(parent.isGroupExpanded(groupPosition)){
            parent.collapseGroup(groupPosition);
        }else{
            //第二个参数false表示展开时是否触发默认滚动动画
            parent.expandGroup(groupPosition,false);
        }
        //telling the listView we have handled the group click, and don't want the default actions.
        return true;
    }*/
}
