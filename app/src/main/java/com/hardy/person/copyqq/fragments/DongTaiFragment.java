package com.hardy.person.copyqq.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.activities.ItemActivityFromDongtai;

/**
 * @author 马鹏昊
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class DongTaiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dongtai_layout,container,false);
        //整个都是ListView但无下拉刷新
        ListView listView = (ListView) view.findViewById(R.id.dongtaiList);
        final String[] itemNames = {null,"游戏","看点","动漫","音乐","直播",null,"附近的群","同城服务","运动"};
        final int[] itemIcons = {0,R.drawable.game,R.drawable.look,R.drawable.animation,R.drawable.music,
                R.drawable.liveshow,0,R.drawable.groups,R.drawable.service,R.drawable.sport};
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
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
                View view;
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                switch (position){
                    case 0:
                        view = layoutInflater.inflate(R.layout.dongtai_first_item_layout,null);
                        break;
                    case 6:
                        view = layoutInflater.inflate(R.layout.dongtai_six_divider_layout,null);
                        break;
                    default:
                        view = layoutInflater.inflate(R.layout.dongtai_otheritem_layout,null);
                        ImageView imageView = (ImageView) view.findViewById(R.id.dongtaiItemIcon);
                        imageView.setImageResource(itemIcons[position]);
                        TextView textView = (TextView) view.findViewById(R.id.dongtaiItemName);
                        textView.setText(itemNames[position]);
                }
                return view;
            }
        };
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 6) {
                    return;
                }
                TextView textView = (TextView) view.findViewById(R.id.dongtaiItemName);
                String title = textView.getText().toString();
                Intent intent = new Intent(getActivity(), ItemActivityFromDongtai.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        return view;

    }
}
