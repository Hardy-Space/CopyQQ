package com.hardy.person.copyqq.pull_load_refresh_util;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.utils.GetApplicationContext;

/**
 * @author 马鹏昊
 * @date {date}
 * @des 在这里处理数据刷新操作和加载数据操作，即在这里产生添加最新刷新出来的数据
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MyListener implements PullToRefreshLayout.OnRefreshListener
{
    // 下拉刷新的具体操作
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
    {
        switch (pullToRefreshLayout.getId()){
            //联系人界面的下拉刷新布局
            case R.id.refresh_view:
                //只是设置停在加载状态3秒，加载完成后Toast提示，实际应用时加载时间依据真实的程序加载数据时间
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        Toast.makeText(GetApplicationContext.returnApplicationContext(), "已刷新出最新数据，在PullToRefreshLayout.OnRefreshListener的实现接口里的onRefresh方法里处理！", Toast.LENGTH_SHORT).show();
                    }
                }.sendEmptyMessageDelayed(0, 3000);
                break;
            //消息界面下拉刷新布局
            case R.id.refresh_msg:
                //只是设置停在加载状态3秒，加载完成后Toast提示，实际应用时加载时间依据真实的程序加载数据时间
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        Toast.makeText(GetApplicationContext.returnApplicationContext(), "已刷新出最新数据，在PullToRefreshLayout.OnRefreshListener的实现接口里的onRefresh方法里处理！", Toast.LENGTH_SHORT).show();
                    }
                }.sendEmptyMessageDelayed(0, 3000);
                break;
        }

    }

    // 上拉加载的具体操作
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
    {

        switch (pullToRefreshLayout.getId()){
            //联系人界面的下拉刷新布局
            case R.id.refresh_view:
                //只是设置停在加载状态3秒，加载完成后Toast提示，实际应用时加载时间依据真实的程序加载数据时间
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件加载完毕了哦！
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        Toast.makeText(GetApplicationContext.returnApplicationContext(), "已加载出更多数据，在PullToRefreshLayout.OnRefreshListener的实现接口里的onLoadMore方法里处理！", Toast.LENGTH_SHORT).show();
                    }
                }.sendEmptyMessageDelayed(0, 3000);
                break;
            //消息界面下拉刷新布局
            case R.id.refresh_msg:
                //只是设置停在加载状态3秒，加载完成后Toast提示，实际应用时加载时间依据真实的程序加载数据时间
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件加载完毕了哦！
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        Toast.makeText(GetApplicationContext.returnApplicationContext(), "已加载出更多数据，在PullToRefreshLayout.OnRefreshListener的实现接口里的onLoadMore方法里处理！", Toast.LENGTH_SHORT).show();
                    }
                }.sendEmptyMessageDelayed(0, 3000);
                break;
        }


    }

}
