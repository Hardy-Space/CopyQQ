package com.hardy.person.copyqq.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardy.person.copyqq.R;

/**
 * @author 马鹏昊
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MyListAdapter extends BaseAdapter{

    private Context mContext ;
    private int[] headPics ;
    private String[] peopleNames ;
    private String[] msgContents ;
    //对方发送消息的时间，ListVie应该把所有消息按时间排序，新消息置顶
    private String[] times ;

    public MyListAdapter(Context context,int[] headPics, String[] peopleNames,
                         String[] msgContents, String[] times) {
        this.headPics = headPics;
        this.peopleNames = peopleNames;
        this.msgContents = msgContents;
        this.times = times;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return peopleNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.listmsg_item_layout,null);
        TextView searcher = (TextView) view.findViewById(R.id.search_msg);
        if (position!=0){
            searcher.setVisibility(View.GONE);
        }
        ImageView pic = (ImageView) view.findViewById(R.id.msgHead);
        pic.setImageResource(R.drawable.head);
        TextView name = (TextView) view.findViewById(R.id.msgName);
        name.setText(peopleNames[position]);
        TextView content = (TextView) view.findViewById(R.id.msgContent);
        content.setText(msgContents[position]);
        TextView time = (TextView) view.findViewById(R.id.msgTime);
        time.setText(times[position]);
        return view;
    }
}
