package com.hardy.person.copyqq.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马鹏昊
 * @date {2016.7.1}
 * @des 保存联系人界面上次销毁前的界面，因为比如说你打开了一个分组，
 * 滑到了某个位置，如果不加处理，再次回到此界面会自动去加载初始界面，很不方便
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class LastUIHolder {
    //保存所有展开项的group的position
    public static List<Integer> selectedGroupPositions = new ArrayList<>();
    //记录联系人界面ExpandableListView退出时滑动到哪了
    public static int contactSelectedItemPosition ;
    //记录联系人界面当前列表上限距离第一项的顶边的距离
    public static int contactFromTop ;
    //记录消息界面ListView退出时滑动到哪了
    public static int msgSelectedItemPosition ;
    //记录消息界面当前列表上限距离第一项的顶边的距离
    public static int msgFromTop ;


}
