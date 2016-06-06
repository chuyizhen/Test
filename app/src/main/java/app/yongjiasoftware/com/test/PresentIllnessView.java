package app.yongjiasoftware.com.test;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Title PresentIllnessView
 * @Description: 现病史
 * @Author: alvin
 * @Date: 2016/5/19.16:09
 * @E-mail: 49467306@qq.com
 * [["发热","头痛","不眠多梦","焦虑恐惧","幻觉妄想","皮肤挫裂伤","伤口感染","白带多有腥味","皮肤疱疹伴疼痛"],
 * ["咳嗽","咯痰","咯血","哮喘","呼吸困难"],
 * ["厌食","暖气胃酸","吞咽困难","腹痛","腹泻","恶心呕吐","呕血","脓血便","黄疸"],
 * ["胸闷","胸痛","心悸","头晕","血脂高"],["口干","\"三多一少\"","血糖高","手足麻木抽搐"],
 * ["腰酸伴下腹痛","尿频尿急尿痛","眼睑或下肢浮肿","血尿"],
 * ["耳痛或流脓水","耳鸣眩晕","鼻塞流涕","咽喉痛","咽异感","声斯"],
 * ["睑结膜充血泪多","怕光视力模糊","口腔溃疡","龋齿","牙龈红肿"]]
 */
public class PresentIllnessView extends LinearLayout {
    private static final String TAG = PresentIllnessView.class.getSimpleName();
    private ExpandableListView mListView;
    private Context mContext;
    private List<String> mGroupList;
    private Resources res;
    private List<List<String>> mChildList;
    private HashMap<String, ArrayList<String>> selectMap;
    private int mPadding, mChildPadding;
    private MyAdapter mAdapter;
    private HashMap<String, Boolean> isCheckedMap;
    private String mJSON = "[" +
            "[\"发热\",\"头痛\",\"不眠多梦\",\"焦虑恐惧\",\"幻觉妄想\",\"皮肤挫裂伤\",\"伤口感染\",\"白带多有腥味\",\"皮肤疱疹伴疼痛\"]," +
            "[\"咳嗽\",\"咯痰\",\"咯血\",\"哮喘\",\"呼吸困难\"]," +
            "[\"厌食\",\"暖气胃酸\",\"吞咽困难\",\"腹痛\",\"腹泻\",\"恶心呕吐\",\"呕血\",\"脓血便\",\"黄疸\"]," +
            "[\"胸闷\",\"胸痛\",\"心悸\",\"头晕\",\"血脂高\"]," +
            "[\"口干\",\"\\\"三多一少\\\"\",\"血糖高\",\"手足麻木抽搐\"]," +
            "[\"腰酸伴下腹痛\",\"尿频尿急尿痛\",\"眼睑或下肢浮肿\",\"血尿\"]," +
            "[\"耳痛或流脓水\",\"耳鸣眩晕\",\"鼻塞流涕\",\"咽喉痛\",\"咽异感\",\"声斯\"]," +
            "[\"睑结膜充血泪多\",\"怕光视力模糊\",\"口腔溃疡\",\"龋齿\",\"牙龈红肿\"]" +
            "]";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
        }
    };

    public PresentIllnessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initDatas();
    }


    public PresentIllnessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PresentIllnessView(Context context) {
        this(context, null);
    }

    private void init() {
        mListView = new ExpandableListView(mContext);
        selectMap = new HashMap<String, ArrayList<String>>();
        selectMap.clear();
        isCheckedMap = new HashMap<String, Boolean>();
        isCheckedMap.clear();
        res = mContext.getResources();
        mPadding = (int) (res.getDisplayMetrics().density * 10);
        mChildPadding = mPadding / 2;
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        addView(mListView);
    }

    private void initDatas() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mGroupList = Arrays.asList(res.getStringArray(R.array.illness_type));
                mChildList = new ArrayList<List<String>>();
                mChildList = new Gson().fromJson(mJSON, new TypeToken<List<List<String>>>() {
                }.getType());
                mHandler.sendEmptyMessage(0);
            }
        }.start();

    }


    class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            if (mGroupList == null)
                return 0;
            return mGroupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (mChildList == null || mChildList.size() <= 0)
                return 0;
            return mChildList.get(groupPosition).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return mGroupList.get(groupPosition);
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {

            return mChildList.get(groupPosition).get(childPosition);
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
            GroupHolder groupHolder;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                groupHolder.mGroupTextView = new TextView(mContext);
                convertView = groupHolder.mGroupTextView;
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.mGroupTextView.setText(getGroup(groupPosition));
            groupHolder.mGroupTextView.setPadding(4 * mPadding, mPadding, mPadding, mPadding);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHodler childHodler;
            if (convertView == null) {
                childHodler = new ChildHodler();
                childHodler.mChildCheckBox = new CheckBox(mContext);
                convertView = childHodler.mChildCheckBox;
                convertView.setTag(childHodler);
            } else {
                childHodler = (ChildHodler) convertView.getTag();
            }
            childHodler.mChildCheckBox.setText(getChild(groupPosition, childPosition));
            childHodler.mChildCheckBox.setPadding(mChildPadding, mChildPadding, mChildPadding, mChildPadding);
            String str = childHodler.mChildCheckBox.getText().toString();
            if (isCheckedMap.containsKey(str)) {
                childHodler.mChildCheckBox.setChecked(isCheckedMap.get(str));
            } else {
                childHodler.mChildCheckBox.setChecked(false);
            }
            childHodler.mChildCheckBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    String text = checkBox.getText().toString();
                    String group = "";
                    for (int i = 0; i < getGroupCount(); i++) {
                        for (String str : mChildList.get(i)) {
                            if (text.equals(str)) {
                                group = getGroup(i);
                                break;
                            }
                        }
                        if (!TextUtils.isEmpty(group)) {
                            break;
                        }
                    }
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(true);
                        if (!isCheckedMap.containsKey(text))
                            isCheckedMap.put(text, true);
                        else {
                            isCheckedMap.remove(text);
                            isCheckedMap.put(text, true);
                        }

                        if (!selectMap.containsKey(group)) {
                            ArrayList<String> list = new ArrayList<String>();
                            list.add(text);
                            selectMap.put(group, list);
                        } else {
                            ArrayList<String> list = selectMap.get(group);
                            list.add(text);
                        }
                    } else {
                        checkBox.setChecked(false);
                        if (!isCheckedMap.containsKey(text))
                            isCheckedMap.put(text, false);
                        else {
                            isCheckedMap.remove(text);
                            isCheckedMap.put(text, false);
                        }
                        ArrayList<String> list = selectMap.get(group);
                        if (list.size() > 0 && list != null) {
                            list.remove(text);
                        }
                        if (selectMap.containsKey(group) && list.size() <= 0) {
                            selectMap.remove(group);
                        }
                    }
                    Log.e(TAG, new Gson().toJson(selectMap));
                    Log.e(TAG, new Gson().toJson(isCheckedMap));
                }
            });
//            childHodler.mChildCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//
//                    } else {
//
//                    }
//
//                }
//            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class GroupHolder {
        TextView mGroupTextView;
    }

    class ChildHodler {
        CheckBox mChildCheckBox;
    }
}