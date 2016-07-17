package com.hardy.person.copyqq.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.fragments.ContactFragment;
import com.hardy.person.copyqq.fragments.DongTaiFragment;
import com.hardy.person.copyqq.fragments.MsgChildMsgFragment;
import com.hardy.person.copyqq.fragments.MsgChildPhoFragment;
import com.hardy.person.copyqq.pull_load_refresh_util.PullToRefreshLayout;
import com.hardy.person.copyqq.utils.GetApplicationContext;
import com.hardy.person.copyqq.utils.PullToRefreshLayoutHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 马鹏昊
 * @date {2016.6.22}
 * @des 登陆进去后的主界面
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class MainActivity extends Activity implements View.OnClickListener {

    //侧滑栏
    private DrawerLayout mDrawer;
    //SlidingMenu对象
    private SlidingMenu mSlidingMenu;
    //主界面的头像显示
    private ImageView mMainHead;
    //自定义ToolBar标题显示s
    private TextView mTitle;
    //拓展操作按钮
    private ImageButton mExtraMenu;
    //盛放主界面切换使用的各个Fragment
    private FrameLayout mFragments;
    //下方导航栏的新消息按钮
    private TextView mMessage;
    //下方导航栏的联系人按钮
    private TextView mContact;
    //下方导航栏的动态按钮
    private TextView mDongtai;
    //侧滑菜单的头像显示
    private ImageView mHeadPortrait;
    //侧滑菜单的昵称显示
    private TextView mNickname;
    //侧滑菜单的菜单列表
    private ListView mMenu;
    //侧滑菜单的设置按钮
    private TextView mSetting;
    //消息标题自定义控件
    private View messageView;
    //包含自定义标题控件的父容器
    private LinearLayout container;
    //自定义控件中的消息按钮
    private TextView messageSwitch;
    //自定义控件中的电话按钮
    private TextView phoneSwitch;
    //消息标题标志位
    private final int MESSAGE_FLAG = 0;
    //电话标题标志位
    private final int PHONE_FLAG = 1;
    //联系人标题标志位
    private final int CONTACT_FLAG = 2;
    //动态标题标志位
    private final int DONGTAI_FLAG = 3;
    //是否是第一次打开联系人界面
    public static boolean isFirstShowUI = true;
    //侧滑栏最上面的控件的初始化高度
    private int headerHeight;
    //主布局的最上面的控件的初始化高度
    private int toolBarHeight;
    //两个占位View
    private TextView header;
    private TextView toolBar;
    /*//屏幕宽度
    private float SCREEN_WIDTH;*/

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };


    private void assignViews() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        //在打开的时候让SlidingMenu的刷新和加载布局消失，否则有时候它会卡在那不hide
        mSlidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                PullToRefreshLayoutHolder.sLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                PullToRefreshLayoutHolder.sLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        mMainHead = (ImageView) findViewById(R.id.SmainHead);
        mTitle = (TextView) findViewById(R.id.title);
        mExtraMenu = (ImageButton) findViewById(R.id.extraMenu);
        mExtraMenu.setOnClickListener(this);
        mFragments = (FrameLayout) findViewById(R.id.fragments);
        mMessage = (TextView) findViewById(R.id.message);
        mMessage.setOnClickListener(this);
        mContact = (TextView) findViewById(R.id.contact);
        mContact.setOnClickListener(this);
        mDongtai = (TextView) findViewById(R.id.dongtai);
        mDongtai.setOnClickListener(this);
        mHeadPortrait = (ImageView) findViewById(R.id.SheadPortrait);
        mNickname = (TextView) findViewById(R.id.Snickname);
        mMenu = (ListView) findViewById(R.id.menu);
        //填充ListView内容
        addContentToListViewMenu(mMenu);
        mSetting = (TextView) findViewById(R.id.setting);
        messageView = getLayoutInflater().inflate(R.layout.message_view_layout, null);
        container = (LinearLayout) findViewById(R.id.container);
        messageSwitch = (TextView) messageView.findViewById(R.id.messageSwitch);
        messageSwitch.setOnClickListener(this);
        phoneSwitch = (TextView) messageView.findViewById(R.id.phoneSwitch);
        phoneSwitch.setOnClickListener(this);
        /*WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        SCREEN_WIDTH = metrics.widthPixels;*/


    }

    /*
        主布局头像随侧滑栏滑动渐隐渐现，未实现
    */
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return this.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float behindWidth = mSlidingMenu.getWidth();
        float firstPointX = 0;
        float firstPointY = 0;
        float scale;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstPointX = event.getX();
                firstPointY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = event.getX() - firstPointX;
                float distanceY = event.getY() - firstPointY;
                if (distanceX > distanceY && distanceX > 5) {
                    scale = distanceX / SCREEN_WIDTH;
                    if (scale >= 0 && scale <= 1.0) {
                        if (!mSlidingMenu.isMenuShowing())
                            mMainHead.setAlpha(1 - scale);
                    }
                    if (scale >= -1.0 && scale < 0) {
                        if (mSlidingMenu.isMenuShowing())
                            mMainHead.setAlpha(-scale);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                firstPointX = 0;
                firstPointY = 0;
                break;
        }
        return super.dispatchTouchEvent(event);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        assignViews();
        //判断登陆的账号是哪一方的并据此更新UI
        updateUIInfo();
        //实现沉浸式状态栏
        realizeImmerseStatus();
        //初始化Fragment
        initializeFragment();
    }

    private void updateUIInfo() {
        Intent intent = getIntent();
        int source = intent.getIntExtra("SourceActivity", -1);
        switch (source) {
            //非登录界面进来的
            case -1:
                break;
            //使用本应用服务器注册的账号登陆的
            case 0:
                break;
            //使用QQ第三方账号登陆的
            case 1:
                getQQUserInfos();
                break;
            //使用微博第三方账号登陆的
            case 2:
                break;
        }
    }

    /*
        获取qq账号用户信息
    */
    private void getQQUserInfos() {
        QQToken qqToken = LoginActivity.mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(GetApplicationContext.returnApplicationContext(), qqToken);
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject userInfoObject = (JSONObject) o;
//                                Log.d("###", o.toString());
                try {
                    String nickName = userInfoObject.getString("nickname");
                    String gender = userInfoObject.getString("gender");
                    //qq_2获得的图片比qq_1更清楚
                    String portraitUrl = userInfoObject.getString("figureurl_qq_2");
                    mNickname.setText(nickName);
                    final URL url = new URL(portraitUrl);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("GET");
                                InputStream inputStream = urlConnection.getInputStream();
                                final Drawable drawable = Drawable.createFromStream(inputStream, "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mHeadPortrait.setImageDrawable(drawable);
                                        mMainHead.setImageDrawable(drawable);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }


    /*
        SlidingMenu下实现沉浸式效果，默认不会适用于SlidingMenu
    */
    private void realizeImmerseStatus() {
        //4.4之后才有沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //因为onCreate时还没有绘制控件，所以最后得到的statusBarHeight总是0
            /*//获取屏幕高度
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenHeight = metrics.heightPixels;
            //获取状态栏下面区域的高度
            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int contentViewHeight = rect.height();
            int statusHeight = screenHeight - contentViewHeight;
            Log.d("###", "ScreenHeight:  "+screenHeight);
            Log.d("###", "ContentHeight:  "+contentViewHeight);
            Log.d("###", "StatusHeight:  "+statusHeight);*/

            //使用反射来获取状态栏高度，同样是因为onCreate时还没有绘制控件
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            //sbar是状态栏高度
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            //            Log.d("###", "StatusHeight:  " + sbar);
            //得到SlidingMenu的侧滑栏最上方的控件
            header = (TextView) mSlidingMenu.getMenu().findViewById(R.id.topView_menu);
            //得到SlidingMenu的主布局最上方的控件
            toolBar = (TextView) mSlidingMenu.getContent().findViewById(R.id.topView_content);
            /*//全局监听在draw之前才能获取到控件长宽，因为onCreate时还没有绘制控件
            ViewTreeObserver vto1 = header.getViewTreeObserver();
            vto1.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
              //  public boolean onPreDraw() {
                    header.getViewTreeObserver().removeOnPreDrawListener(this);
                    Log.d("###", "HeaderHeight:  " + header.getMeasuredHeight());
                    headerHeight = header.getMeasuredHeight();
                    return true;
                }
            });
            ViewTreeObserver vto2 = toolBar.getViewTreeObserver();
            vto2.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
              //  public boolean onPreDraw() {
                    toolBar.getViewTreeObserver().removeOnPreDrawListener(this);
                    Log.d("###", "toolBarHeight:  " + toolBar.getMeasuredHeight());
                    toolBarHeight = toolBar.getMeasuredHeight();
                    return true;
                      });*/

            //开始设置新高度
            //修改SlidingMenu的侧滑栏最上方的控件的高度加上状态栏高度作为新高度
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, sbar);
            //设置新高度
            header.setLayoutParams(params1);
            //不设置这个的话虽然控件下边缘回到原先位置，可内容还没有回到原先位置，这里使内容下移
            //            header.setPadding(0,sbar,0,0);
            //修改SlidingMenu的主布局最上方的控件的高度加上状态栏高度作为新高度
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, sbar);
            //设置新高度
            toolBar.setLayoutParams(params2);
            //不设置这个的话虽然控件下边缘回到原先位置，可内容还没有回到原先位置，这里使内容下移
            //            toolBar.setPadding(0,sbar,0,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message:
                //消息按钮切换Fragment
                addFragment(new MsgChildMsgFragment());
                //修改标题
                changeTitle(MESSAGE_FLAG);
                //标识当前导航键被选中
                notifyAllNavigationState(mMessage);
                break;
            case R.id.contact:
                //联系人按钮切换Fragment
                addFragment(new ContactFragment());
                //标识当前导航键被选中
                notifyAllNavigationState(mContact);
                //修改标题显示当前选中的标题页
                changeTitle(CONTACT_FLAG);
                break;
            case R.id.dongtai:
                //动态按钮切换Fragment
                addFragment(new DongTaiFragment());
                //标识当前导航键被选中
                notifyAllNavigationState(mDongtai);
                //修改标题显示当前选中的标题页
                changeTitle(DONGTAI_FLAG);
                break;
            case R.id.messageSwitch:
                //如果消息标题已经被选中则不需要任何操作
                if (!messageSwitch.isSelected()) {
                    //按钮背景变化提示用户操作结果
                    changeTitle(MESSAGE_FLAG);
                    //切换Fragment
                    addFragment(new MsgChildMsgFragment());
                }
                break;
            case R.id.phoneSwitch:
                //如果已经被选中则不需要任何操作
                if (!phoneSwitch.isSelected()) {
                    //按钮背景变化提示用户操作结果
                    changeTitle(PHONE_FLAG);
                    //切换Fragment
                    addFragment(new MsgChildPhoFragment());
                }
                break;
            case R.id.extraMenu:
                //屏幕上右上方的加号，拓展操作
                PopupMenu popupMenu = new PopupMenu(this, mExtraMenu);
                getMenuInflater().inflate(R.menu.add_popup_menu, popupMenu.getMenu());
                popupMenu.show();
                break;
            case R.id.mainHead:
                mSlidingMenu.showMenu();
                break;
        }
    }

    /*
    填充侧滑菜单的ListView
    */
    private void addContentToListViewMenu(ListView listView) {
        //侧滑栏的ListView列表项名称
        String[] itemName = {"开通会员", "CC钱包", "个性装扮", "我的收藏", "我的相册", "我的文件", "我的名片夹"};
        //列表图标
        int[] itemIcon = {R.drawable.myhuiyuan, R.drawable.mywallet, R.drawable.mydrawboard, R.drawable.mycollect,
                R.drawable.myphoto, R.drawable.mydocument, R.drawable.mycard};
        //装载列表项内容的List集合
        List<Map<String, Object>> list = new ArrayList<>();
        //装载List集合
        for (int i = 0; i < itemName.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", itemName[i]);
            map.put("icon", itemIcon[i]);
            list.add(map);
        }
        //定义SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.simpleadapter_layout, new String[]{"name", "icon"}, new int[]{R.id.content, R.id.itemIcon});
        //设置适配器
        listView.setAdapter(simpleAdapter);
    }

    /*
    初始化初始界面的Fragment
    */
    private void initializeFragment() {
        //初始化界面默认为“信息”界面的上方的子信息界面，加载子信息Fragment，实际Fragment要呈现的界面交给Fragment去处理
        addFragment(new MsgChildMsgFragment());
        //标识当前为消息导航键被选中状态
        notifyAllNavigationState(mMessage);
        //修改标题显示当前选中的消息标题页
        mTitle.setVisibility(View.GONE);
        //初始化消息标题被选中，电话标题默认状态（未选中）即可
        messageSwitch.setSelected(true);
        messageSwitch.setTextColor(getResources().getColor(R.color.drawerBack));
        //标题初始化为消息/电话的自定义控件
        container.addView(messageView);
    }

    /*
        统一设置下方导航键选中状态
    */
    private void notifyAllNavigationState(TextView selectedTextView) {
        //先把所有的导航键恢复为未选中状态
        mMessage.setSelected(false);
        mMessage.setTextColor(Color.BLACK);
        mContact.setSelected(false);
        mContact.setTextColor(Color.BLACK);
        mDongtai.setSelected(false);
        mDongtai.setTextColor(Color.BLACK);
        //再对选中的导航键特殊对待
        selectedTextView.setSelected(true);
        selectedTextView.setTextColor(getResources().getColor(R.color.navigationSelectedTextColor));
    }

    /*
        添加Fragment
    */
    private void addFragment(Fragment fragment) {
        //上转型对象统一添加Fragment
        //获取此Context下的FragmentManager
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragments, fragment)
                .commit();

    }

    /*
        统一修改标题以及自定义标题按钮选中状态
    */
    private void changeTitle(int flag) {
        switch (flag) {
            case MESSAGE_FLAG://点击消息标签或者下方消息导航时调用的此方法
                //当点击下方的消息导航时默认显示自定义控件的消息页
                //在此判断是点击的下方导航消息标签还是上方标题，如果是下方消息按钮已经选中则只修改是选中消息标题还是电话标题就行了
                if (!mMessage.isSelected()) {
                    //标题消失
                    mTitle.setVisibility(View.GONE);
                    //把上次添加的先移除掉，否则会冲突
                    container.removeView(messageView);
                    //添加自定义控件
                    container.addView(messageView);
                }
                //消息按钮为被选中状态
                messageSwitch.setSelected(true);
                messageSwitch.setTextColor(getResources().getColor(R.color.drawerBack));
                //电话按钮为未选中状态
                phoneSwitch.setSelected(false);
                phoneSwitch.setTextColor(getResources().getColor(R.color.mainBack));
                break;
            case PHONE_FLAG://点击电话标签调用的此方法
                //电话按钮为被选中状态
                phoneSwitch.setSelected(true);
                phoneSwitch.setTextColor(getResources().getColor(R.color.drawerBack));
                //消息按钮为未选中状态
                messageSwitch.setSelected(false);
                messageSwitch.setTextColor(getResources().getColor(R.color.mainBack));
                break;
            case CONTACT_FLAG://点击联系人标签调用的此方法
                container.removeView(messageView);
                mTitle.setText(mContact.getText().toString());
                mTitle.setVisibility(View.VISIBLE);
                break;
            case DONGTAI_FLAG://点击动态标签调用的此方法
                container.removeView(messageView);
                mTitle.setText(mDongtai.getText().toString());
                mTitle.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //如果侧滑栏已打开，按下返回键时先关闭侧滑栏，否则直接退出
        if (mSlidingMenu.isMenuShowing()) {
            //监测当前状态，自动打开或关闭
            mSlidingMenu.toggle();
            return;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if (LoginActivity.mTencent != null)
            LoginActivity.mTencent.logout(this);
        super.onDestroy();
    }
}
