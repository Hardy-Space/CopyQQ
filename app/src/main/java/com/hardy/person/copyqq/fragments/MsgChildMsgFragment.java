package com.hardy.person.copyqq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.activities.MainActivity;
import com.hardy.person.copyqq.adapters.MyListAdapter;
import com.hardy.person.copyqq.pull_load_refresh_util.MyListener;
import com.hardy.person.copyqq.pull_load_refresh_util.PullToRefreshLayout;
import com.hardy.person.copyqq.utils.LastUIHolder;
import com.hardy.person.copyqq.utils.PullToRefreshLayoutHolder;

/**
 * @author 马鹏昊
 * @date {2016.7.1}
 * @des 新消息界面
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MsgChildMsgFragment extends Fragment {

    private PullToRefreshLayout mToRefreshLayout;
    private ListView mListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_layout, container, false);
        //去本地读取聊天记录并且时刻和服务器保持通信以至于能即时通讯，显示在ListView中，有下拉刷新
        mToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_msg);
        mToRefreshLayout.setOnRefreshListener(new MyListener());
        //持有当前刷新布局
        PullToRefreshLayoutHolder.sLayout = mToRefreshLayout;
        mListView = (ListView) view.findViewById(R.id.list_msg);
        if (MainActivity.isFirstShowUI)
            //如果是第一次进入此界面，就去初始化原始界面
            initialView();
        else
            //如果不是第一次进入就去恢复到上次退出这个界面时的显示界面
            restoreLastUI();
        return view;
    }

    /*
            恢复上次显示消息的界面
    */
    private void restoreLastUI() {
        //先初始化数据
        initialView();
        //恢复页面时把上次退出时的第一项重新加载为第一项，并且上次第一项显示多高现在就显示多高
        mListView.setSelectionFromTop(LastUIHolder.msgSelectedItemPosition, LastUIHolder.msgFromTop);
    }

    private void initialView() {
        final String[] names = {"Kobe", "Iverson", "Stephen.Curry", "TracyMac", "追梦Green", "Klay.Tompson",
                "路人甲", "路人乙", "路人丙", "路人丁", "张三", "李四", "王五", "赵六"};
        String ss = "xxxxxxxxx......xxx";
        String[] latestNews = {"Hi,Memba!", "Hi,AI!", "Hi,Curry!", "Hi,T-Mac!", "Hi,追梦", "Hi,God Tang!", ss, ss, ss
                , ss, ss, ss, ss, ss};
        String tt = "5:40";
        String[] times = {"10:37", "7:53", "昨天", "6-24", "16:05", "昨天", tt, tt, tt
                , tt, tt, tt, tt, tt};
        MyListAdapter adapter = new MyListAdapter(getActivity(), null, names, latestNews, times);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //                这个得到的是整个组件的滑动X、Y值，而不是内容滑动
                    /*LastUIHolder.scrollX = view.getScrollX();
                    LastUIHolder.scrollY = view.getScrollY();*/
                //如果停止滑动就记录可见的第一项和他距离顶端的值
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LastUIHolder.msgSelectedItemPosition = mListView.getFirstVisiblePosition();
                    View v = mListView.getChildAt(0);
                    LastUIHolder.msgFromTop = v.getTop();
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
