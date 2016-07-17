package com.hardy.person.copyqq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.activities.MainActivity;
import com.hardy.person.copyqq.adapters.MyExpandableAdapter;
import com.hardy.person.copyqq.pull_load_refresh_util.MyListener;
import com.hardy.person.copyqq.pull_load_refresh_util.PullToRefreshLayout;
import com.hardy.person.copyqq.utils.LastUIHolder;
import com.hardy.person.copyqq.utils.PullToRefreshLayoutHolder;

import java.util.Iterator;

/**
 * @author 马鹏昊
 * @date {2016.7.1}
 * @des 联系人界面，已处理保存上次ExpandableListView操作结果，下次恢复上次界面，而不是初始界面
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class ContactFragment extends Fragment {

    ExpandableListView mExpandableListView;
    PullToRefreshLayout mToRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_layout, container, false);
        //去服务器获取分组信息，显示在Fragment中，整个是ListView且有下拉刷新
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.content_view);
        mToRefreshLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
        mToRefreshLayout.setOnRefreshListener(new MyListener());
        //持有当前刷新布局
        PullToRefreshLayoutHolder.sLayout = mToRefreshLayout;
        if (MainActivity.isFirstShowUI)
            //如果是第一次进入此界面，就去初始化原始界面
            initListView();
        else
            //如果不是第一次进入就去恢复到上次退出这个界面时的显示界面
            restoreLastUI();
        return view;
    }

    /*
        恢复上次显示联系人的界面
    */
    private void restoreLastUI() {
        //先初始化数据
        initListView();
        //迭代器去联系人界面数据保存类读数据
        Iterator<Integer> iterator = LastUIHolder.selectedGroupPositions.iterator();
        while (iterator.hasNext()) {
            //因为默认的初始化时是全部合并，把上次退出前已经展开的项重新展开，达到保存的效果
            mExpandableListView.expandGroup(iterator.next(), false);
        }
//        Log.d("###", "" + LastUIHolder.selectedItemPosition);
        //恢复页面时把上次退出时的第一项重新加载为第一项，并且上次第一项显示多高现在就显示多高
        mExpandableListView.setSelectionFromTop(LastUIHolder.contactSelectedItemPosition, LastUIHolder.contactFromTop);
    }

    private void initListView() {
        //定义分组的List信息
        String[] groupNames = {"分组一", "分组二", "分组三", "分组四", "分组五", "分组六", "分组七", "分组八", "分组九", "分组十"};

        //定义展开组内的各项信息的List
        String[] childNames = {"好友一", "好友二", "好友三", "好友四", "好友五", "好友六", "好友七", "好友八", "好友九", "好友十"};


        MyExpandableAdapter adapter = new MyExpandableAdapter(getActivity(), groupNames, childNames);
        mExpandableListView.setAdapter(adapter);
        //设置展开时不要滚动和保存展开组的position
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //因为我在第一项的View设置的是一些要被点击的TextView，所以不需要展开，如果不处理会出错，Adapter返回
                // 子View是null只是不给第一项的父View设置子View罢了
                if (groupPosition == 0)
                    return true;

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    //合并时把信息类中保存展开组position的对象删掉
                    LastUIHolder.selectedGroupPositions.remove((Object) groupPosition);

                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                    //展开组position加入信息保存类
                    LastUIHolder.selectedGroupPositions.add(groupPosition);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
        //监听滑动，随时保存滑动后的位置信息
        mExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //                这个得到的是整个组件的滑动X、Y值，而不是内容滑动
                    /*LastUIHolder.scrollX = view.getScrollX();
                    LastUIHolder.scrollY = view.getScrollY();*/
                //如果停止滑动就记录可见的第一项和他距离顶端的值
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LastUIHolder.contactSelectedItemPosition = mExpandableListView.getFirstVisiblePosition();
                    View v = mExpandableListView.getChildAt(0);
                    LastUIHolder.contactFromTop = v.getTop();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        //界面销毁时标签置为非第一次进入，因为在刚打开程序时在Main界面已经置为true了，那时是第一次进入
        MainActivity.isFirstShowUI = false;
        super.onDestroyView();
    }
}
