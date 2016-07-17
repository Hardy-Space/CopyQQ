package com.hardy.person.copyqq.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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
public class MyExpandableAdapter extends BaseExpandableListAdapter {

    String[] groupNames ;
    String[] childNames ;
    String personalSign = "显示好友的个性签名...";
    Context mContext ;

    public MyExpandableAdapter(Context context, String[] groupNames, String[] childNames) {
        mContext = context ;
        this.groupNames = groupNames;
        this.childNames = childNames;
    }


    @Override
    public int getGroupCount() {
        return groupNames.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childNames.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (groupPosition==0){
            View view = View.inflate(mContext,R.layout.contact_item0_layout,null);
            return view;
        }
        View view = View.inflate(mContext, R.layout.group_layout,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.groupIcon);
        imageView.setImageResource(R.drawable.expandable);
        TextView textView = (TextView) view.findViewById(R.id.groupName);
        textView.setText(groupNames[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (groupPosition==0){
            return null;
        }
        View view = View.inflate(mContext,R.layout.child_layout,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.childHead);
        imageView.setImageResource(R.drawable.head);
        TextView textView = (TextView) view.findViewById(R.id.childName);
        textView.setText(childNames[childPosition]);
        TextView textView1 = (TextView) view.findViewById(R.id.childSign);
        textView1.setText(personalSign);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
