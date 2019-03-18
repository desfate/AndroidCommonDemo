package com.defate.mac.common_android.widget.address;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.defate.mac.common_android.R;
import com.defate.mac.common_android.widget.address.data.AddressStore;


import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择控件  根据数据库地址信息  生成选择器
 */
public class AddressDialog extends Dialog implements MessageCallBack {

    private OnAddressListener listener;

    private List<String> mList = new ArrayList<>();  //这里储存的是地址数据
    private List<AddressItemData> idList = new ArrayList<>();  //保存数据对应的ID
    private List<AddressItemData> mDBlist = new ArrayList<>();  //当前显示的地址信息

    private List<List<AddressItemData>> DBinfo = new ArrayList<>();  //从数据库获取的地址信息

    private AddressAdapter mAdapter;

    private int tab = -1; //当前在哪个tab

    //顶部部分
    private ImageView close_img;  //关闭按钮

    //中间标题栏  占时写死最多四个
    private TextView province_txt;  //省级别
    private TextView city_txt;     //市级别
    private TextView area_txt;     //区
    private TextView street_txt;   //街道

    //底部可选列表
    private ListView mlistView;  //显示的列表
    private ProgressBar progressBar; //数据加载


    public AddressDialog(Context context){
        super(context);
    }

    public AddressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_address_dialog);
        setCanceledOnTouchOutside(true);  //点击外部 dialog 消失   !!!!!!! 这个我写在里面啦
        initView();
        initData();
        refreshTab();
    }

    private void initView(){
        close_img = findViewById(R.id.close_img);
        province_txt = findViewById(R.id.province_txt);
        city_txt = findViewById(R.id.city_txt);
        area_txt = findViewById(R.id.area_txt);
        street_txt = findViewById(R.id.street_txt);
        mlistView = findViewById(R.id.mlistView);
        progressBar = findViewById(R.id.progressBar);

        setListener();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //选择某一个条目
                AddressItemData data = mDBlist.get(position);

                if(mList.size() >= tab + 1){  //把后面多余的删除掉
                    for(int i = mList.size() - 1 ; i >= tab ; i --){
                        mList.remove(i);
                        clearState(i);
                    }
                }

                if(idList.size() >= tab + 1){
                    for(int i = idList.size() - 1 ; i >= tab ; i --){
                        idList.remove(i);
                        clearState(i);
                    }
                }

                clearState(tab);
                if(DBinfo.get(tab).size() != 0) {
                    DBinfo.get(tab).get(position).setState(true);  //设置被选中的数据 其他的全部设置成未选中
                    mList.add(DBinfo.get(tab).get(position).getAddressName());
                    idList.add(data);
                }

                refreshTab();
            }
        });

        mAdapter = new AddressAdapter(getContext(), mDBlist);
        mlistView.setAdapter(mAdapter);
    }

    /**
     * 初始化地址信息
     */
    private void initData(){
        if(DBinfo.size() == 0) {
            DBinfo.add(new ArrayList<AddressItemData>());
            DBinfo.add(new ArrayList<AddressItemData>());
            DBinfo.add(new ArrayList<AddressItemData>());
            DBinfo.add(new ArrayList<AddressItemData>());
        }
        requestProviceInfo();

    }

    private void setListener(){
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        province_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(province_txt , 0);  //列表也要变化
                changeList(0);
                tab = 0;
            }
        });

        city_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(city_txt , 1);
                changeList(1);
                tab = 1;
            }
        });

        area_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(area_txt, 2);
                changeList(2);
                tab = 2;
            }
        });

        street_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(street_txt, 3);
                changeList(3);
                tab = 3;
            }
        });


    }




    // 这里处理一下条目
    private void refreshTab(){
        if(mList == null)
            return;

        switch (mList.size()){
            case 0:
                tab = 0;
                hideAllTab();
                inTab(province_txt);   //标记当前在哪个tab
                changeListClear(0);
                break;
            case 1: //已经选了省
                tab = 1;
                hideAllTab();
                setChoiceTab(province_txt, 0);    //把上一次选择的信息 添加到其他tab里
                inTab(city_txt);
                changeListClear(1);

                break;
            case 2: //已经选了省市
                tab = 2;
                hideAllTab();
                setChoiceTab(province_txt ,0);
                setChoiceTab(city_txt ,1);
                inTab(area_txt);
                changeListClear(2);
                break;
            case 3:  //已经选择了省市区
//                hideAllTab();
//                setChoiceTab(province_txt, 0);
//                setChoiceTab(city_txt ,1);
//                setChoiceTab(area_txt ,2);
//                inTab(street_txt);
//                changeListClear(3);
//                tab = 3;

                setChoiceTab(province_txt, 0);
                setChoiceTab(city_txt ,1);
                setChoiceTab(area_txt ,2);
//                setChoiceTab(street_txt ,3);
                if(tab != 2) {
                    changeListClear(tab + 1);
                }else{
                    if(listener != null){
                        listener.onSuccess();
                    }
                    dismiss();
                }

                break;
            case 4:  //全部选好了
                setChoiceTab(province_txt, 0);
                setChoiceTab(city_txt ,1);
                setChoiceTab(area_txt ,2);
                setChoiceTab(street_txt ,3);
                if(tab != 3) {
                    changeListClear(tab + 1);
                }else{
                    if(listener != null){
                        listener.onSuccess();
                    }
                    dismiss();
                }

                break;
        }
    }

    private void clearState(int mtab){
        for(AddressItemData datas: DBinfo.get(tab)){
            datas.setState(false);
        }
    }

    /**
     * 影藏所有条目
     */
    private void hideAllTab(){
        province_txt.setVisibility(View.GONE) ;
        city_txt.setVisibility(View.GONE);
        area_txt.setVisibility(View.GONE);
        street_txt.setVisibility(View.GONE);
    }

    private void setAllTextColor(){
        province_txt.setTextColor(getContext().getResources().getColor(R.color.widget_address_txt_color));
        city_txt.setTextColor(getContext().getResources().getColor(R.color.widget_address_txt_color));
        area_txt.setTextColor(getContext().getResources().getColor(R.color.widget_address_txt_color));
        street_txt.setTextColor(getContext().getResources().getColor(R.color.widget_address_txt_color));
    }

    private void setChoiceTab(TextView view, int key){
        view.setVisibility(View.VISIBLE);  //显示省的数据选择
        view.setText(mList.get(key));
        view.setTextColor(getContext().getResources().getColor(R.color.widget_address_txt_color));
    }

    /**
     * 当前tab被选中状态
     */
    private void inTab(TextView view){
        view.setText(getContext().getString(R.string.p_widget_address_choice));
        view.setTextColor(getContext().getResources().getColor(R.color.widget_theme_color));
        view.setVisibility(View.VISIBLE);
    }

    private void changeTab(TextView view, int type){
        setAllTextColor();
        view.setTextColor(getContext().getResources().getColor(R.color.widget_theme_color));
        mDBlist = DBinfo.get(type);
        mAdapter.notifyDataSetChanged();
    }

    private void changeList(int type){
        int states = 0;

        if(DBinfo.get(type) != null){
            for(int i = 0; i < DBinfo.get(type).size(); i++){
                if(DBinfo.get(type).get(i).isState()){
                    states = i;
                }
            }
        }

        mAdapter.setData(DBinfo.get(type));
        mAdapter.notifyDataSetChanged();
        mlistView.setSelection(states);
    }

    private void changeListClear(int type){
        int states = 0;

        if(type == 1){ //请求市级
            if(idList != null && idList.size() > 0){
                requestCityInfo(idList.get(type - 1).getProvinceid());  //请求当前数据
                return;
            }
        }

        if(type == 2){ //请求区级别
            if(idList != null && idList.size() > 0){
                requestCityInfo(idList.get(type - 1).getProvinceid());  //请求区信息
                return;
            }

        }

        if(DBinfo.size() > 0 && DBinfo.get(type) != null){
            for(int i = 0; i < DBinfo.get(type).size(); i++){
                DBinfo.get(type).get(i).setState(false);
            }
            mAdapter.setData(DBinfo.get(type));
            mAdapter.notifyDataSetChanged();
            mlistView.setSelection(states);
        }


    }


    private void requestProviceInfo(){
        if(tab == -1) {
            AddressUtils.getAddressInfo("province", "", this); //请求省信息
            progressBar.setVisibility(View.VISIBLE);
            mlistView.setVisibility(View.INVISIBLE);
        }
    }

    private void requestCityInfo(String id){
            AddressUtils.getAddressInfo("city", id, this); //请求省信息
            progressBar.setVisibility(View.VISIBLE);
            mlistView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void OnMessageCallBack(){
        switch (tab){
            case -1:
            case 0:
                DBinfo.get(0).clear();
                ArrayList<ProvinceData> provinceData = AddressStore.getInstance().getProvinceData();  //省信息
                for (int i = 0; i < provinceData.size(); i++) {
                    AddressItemData data = new AddressItemData();
                    data.setAddressName(provinceData.get(i).getProvincename());
                    data.setAutoid(provinceData.get(i).getAutoid());
                    data.setProvinceid(provinceData.get(i).getProvinceid());
                    DBinfo.get(0).add(data);  //加入省的数据集
                }
                break;
            case 1:
                ArrayList<ProvinceData> cityData = AddressStore.getInstance().getCityData();
                if(cityData.size() == 0){
                    //返回了数据 但是为空 就直接进入下一级
                    if(listener != null){
                        listener.onSuccess();
                    }
                    dismiss();
                }
                DBinfo.get(1).clear();
                for (int i = 0; i < cityData.size(); i++) {
                    AddressItemData data = new AddressItemData();
                    data.setAddressName(cityData.get(i).getProvincename());
                    data.setAutoid(cityData.get(i).getAutoid());
                    data.setProvinceid(cityData.get(i).getProvinceid());
                    DBinfo.get(1).add(data);  //加入省的数据集
                }
                break;
            case 2:
                ArrayList<ProvinceData> areaData = AddressStore.getInstance().getCityData();
                if(areaData.size() == 0){
                    //返回了数据 但是为空 就直接进入下一级
                    if(listener != null){
                        listener.onSuccess();
                    }
                    dismiss();
                }
                DBinfo.get(2).clear();
                for (int i = 0; i < areaData.size(); i++) {
                    AddressItemData data = new AddressItemData();
                    data.setAddressName(areaData.get(i).getProvincename());
                    data.setAutoid(areaData.get(i).getAutoid());
                    data.setProvinceid(areaData.get(i).getProvinceid());
                    DBinfo.get(2).add(data);  //加入省的数据集
                }
                break;
        }
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                mlistView.setVisibility(View.VISIBLE);
                mDBlist = DBinfo.get(tab);
                mAdapter.setData(mDBlist);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    public void setListener(OnAddressListener listener){
        this.listener = listener;
    }

    public List<AddressItemData> getIdList() {
        return idList;
    }

    public void setIdList(List<AddressItemData> idList) {
        this.idList = idList;
    }

    public List<String> getmList() {
        return mList;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }


}
