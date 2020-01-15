package com.bingo.widget.dropdownmenu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;


import com.bingo.widget.BRadioView;
import com.bingo.widget.R;
import com.bingo.widget.dropdownmenu.entity.SingleItemBean;
import com.bingo.widget.dropdownmenu.interfaces.OnFilterDoneListener;
import com.bingo.widget.dropdownmenu.interfaces.OnFilterItemClickListener;
import com.bingo.widget.dropdownmenu.typeview.SingleListView;
import com.bingo.widget.dropdownmenu.util.UIUtil;
import com.bingo.widget.dropdownmenu.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private Map<String,Object> dataMap;
    private Map<String,String> departmentList;
    private Map<String,String> attributeList;

    public DropMenuAdapter(Context context, Map<String,Object> dataMap, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.onFilterDoneListener = onFilterDoneListener;
        this.dataMap=dataMap;
        this.titles = (String[]) dataMap.get("title");
        this.departmentList = (Map<String, String>) dataMap.get("department");
        this.attributeList= (Map<String, String>) dataMap.get("attribute");
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createDepartmentListView();
                break;
            case 1:
                view = createAttributeListView();
                break;
            case 2:
                view = createSearchView();
                break;
            default:
                break;
        }

        return view;
    }
    private View createDepartmentListView() {
        SingleListView<SingleItemBean> singleListView = new SingleListView<SingleItemBean>(mContext)
                .adapter(new SimpleTextAdapter<SingleItemBean>(null, mContext) {
                    @Override
                    public String provideText(SingleItemBean item) {
                        return item.getContent();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<SingleItemBean>() {
                    @Override
                    public void onItemClick(SingleItemBean item,int position) {
                        onFilterDone(0,item.getContent(),item.getId());
                    }
                });

        List<SingleItemBean> list = new ArrayList<>();
        for (Map.Entry<String,String> entry:departmentList.entrySet()){
            SingleItemBean single = new SingleItemBean();
            single.setId(entry.getKey());
            single.setContent(entry.getValue());
            list.add(single);
        }

        singleListView.setList(list, -1);

        return singleListView;
    }
    private View createAttributeListView() {
        SingleListView<SingleItemBean> singleListView = new SingleListView<SingleItemBean>(mContext)
                .adapter(new SimpleTextAdapter<SingleItemBean>(null, mContext) {
                    @Override
                    public String provideText(SingleItemBean item) {
                        return item.getContent();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<SingleItemBean>() {
                    @Override
                    public void onItemClick(SingleItemBean item,int position) {
                        onFilterDone(1,item.getContent(),item.getId());
                    }
                });

        List<SingleItemBean> list = new ArrayList<>();
        for (Map.Entry<String,String> entry:attributeList.entrySet()){
            SingleItemBean bean =new SingleItemBean();
            bean.setId(entry.getKey());
            bean.setContent(entry.getValue());
            list.add(bean);
        }
        singleListView.setList(list, -1);

        return singleListView;
    }

//    private View createSingleListView() {
//        SingleListView<String> singleListView = new SingleListView<String>(mContext)
//                .adapter(new SimpleTextAdapter<String>(null, mContext) {
//                    @Override
//                    public String provideText(String string) {
//                        return string;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        int dp = UIUtil.dp(mContext, 15);
//                        checkedTextView.setPadding(dp, dp, 0, dp);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<String>() {
//                    @Override
//                    public void onItemClick(String item,int position) {
//                        FilterUrl.instance().singleListPosition = item;
//
//                        FilterUrl.instance().position = 0;
//                        FilterUrl.instance().positionTitle = item;
//
//                        onFilterDone();
//                    }
//                });
//
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            list.add("" + i);
//        }
//        singleListView.setList(list, -1);
//
//        return singleListView;
//    }
//
//
//    private View createDoubleListView() {
//        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
//                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
//                    @Override
//                    public String provideText(FilterType filterType) {
//                        return filterType.desc;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
//                    }
//                })
//                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
//                    @Override
//                    public String provideText(String s) {
//                        return s;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
//                        checkedTextView.setBackgroundResource(android.R.color.white);
//                    }
//                })
//                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
//                    @Override
//                    public List<String> provideRightList(FilterType item, int position) {
//                        List<String> child = item.child;
//                        if (CommonUtil.isEmpty(child)) {
//                            FilterUrl.instance().doubleListLeft = item.desc;
//                            FilterUrl.instance().doubleListRight = "";
//
//                            FilterUrl.instance().position = 1;
//                            FilterUrl.instance().positionTitle = item.desc;
//
//                            onFilterDone();
//                        }
//
//                        return child;
//                    }
//                })
//                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
//                    @Override
//                    public void onRightItemClick(FilterType item, String string) {
//                        FilterUrl.instance().doubleListLeft = item.desc;
//                        FilterUrl.instance().doubleListRight = string;
//
//                        FilterUrl.instance().position = 1;
//                        FilterUrl.instance().positionTitle = string;
//
//                        onFilterDone();
//                    }
//                });
//
//
//        List<FilterType> list = new ArrayList<>();
//
//        //第一项
//        FilterType filterType = new FilterType();
//        filterType.desc = "10";
//        list.add(filterType);
//
//        //第二项
//        filterType = new FilterType();
//        filterType.desc = "11";
//        List<String> childList = new ArrayList<>();
//        for (int i = 0; i < 13; ++i) {
//            childList.add("11" + i);
//        }
//        filterType.child = childList;
//        list.add(filterType);
//
//        //第三项
//        filterType = new FilterType();
//        filterType.desc = "12";
//        childList = new ArrayList<>();
//        for (int i = 0; i < 3; ++i) {
//            childList.add("12" + i);
//        }
//        filterType.child = childList;
//        list.add(filterType);
//
//        //初始化选中.
//        comTypeDoubleListView.setLeftList(list, 1);
//        comTypeDoubleListView.setRightList(list.get(1).child, -1);
//        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));
//
//        return comTypeDoubleListView;
//    }
//
//
//    private View createSingleGridView() {
//        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
//                .adapter(new SimpleTextAdapter<String>(null, mContext) {
//                    @Override
//                    public String provideText(String s) {
//                        return s;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
//                        checkedTextView.setGravity(Gravity.CENTER);
//                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<String>() {
//                    @Override
//                    public void onItemClick(String item,int position) {
//                        FilterUrl.instance().singleGridPosition = item;
//
//                        FilterUrl.instance().position = 2;
//                        FilterUrl.instance().positionTitle = item;
//
//                        onFilterDone();
//
//                    }
//                });
//
//        List<String> list = new ArrayList<>();
//        for (int i = 20; i < 39; ++i) {
//            list.add(String.valueOf(i));
//        }
//        singleGridView.setList(list, -1);
//
//
//        return singleGridView;
//    }
//
//
//    private View createBetterDoubleGrid() {
//
//        List<String> phases = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            phases.add("3top" + i);
//        }
//        List<String> areas = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            areas.add("3bottom" + i);
//        }
//
//
//        return new BetterDoubleGridView(mContext)
//                .setmTopGridData(phases)
//                .setmBottomGridList(areas)
//                .setOnFilterDoneListener(onFilterDoneListener)
//                .build();
//    }



    private View createSearchView(){
        View searchView = View.inflate(mContext,R.layout.filter_search_view,null);
        final EditText editText = searchView.findViewById(R.id.et_input);
        Button searchBtn = searchView.findViewById(R.id.btn_search);
        final BRadioView customRadioView = searchView.findViewById(R.id.custom_radio_view);
        final List<BRadioView.ItemBean> list= new ArrayList();
        list.add(new BRadioView.ItemBean("sparepart_code","Code",true));
        list.add(new BRadioView.ItemBean("location","Location",false));
        list.add(new BRadioView.ItemBean("sparepart_des","Description",false));
        customRadioView.setDatas(list);
        customRadioView.setOnItemClickListener(new BRadioView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BRadioView.ItemBean item) {
                switch (position){
                    case 0:
                        editText.setHint(mContext.getString(R.string.text_hint_filter_search_code));
                        break;
                    case 1:
                        editText.setHint(mContext.getString(R.string.text_hint_filter_search_location));
                        break;
                    case 2:
                        editText.setHint(mContext.getString(R.string.text_hint_filter_search_desc));
                        break;
                        default:
                            break;
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterDone(2,editText.getText().toString(),customRadioView.getData().getId());
                editText.setText("");
            }
        });
        return searchView;
    }


    private void onFilterDone(int position,String content,String id) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, content, id);
        }
    }

}
