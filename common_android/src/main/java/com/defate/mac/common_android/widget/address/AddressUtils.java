package com.defate.mac.common_android.widget.address;

import com.defate.mac.common_android.notification.data.BigPictureStyleSocialAppData;
import com.defate.mac.common_android.widget.address.data.AddressStore;

import java.util.ArrayList;

public class AddressUtils {

    /**
     * 获取省市信息
     * @param type  标记
     * @param value 地区 id
     */
    public static void getAddressInfo(String type, String value, MessageCallBack callBack){
        if(type.equals("province")){
            request(type, value, callBack);   //请求数据  根据数据源不同可以来自不同地方数据 这里展示测试数据
        }else if(type.equals("city")){
            request(type, value, callBack);
        }
    }

    //请求数据  根据数据源不同可以来自不同地方数据 这里展示测试数据
    public static void request(String type, String value, MessageCallBack callBack){
        ArrayList<ProvinceData> provinceData = new ArrayList<>();
        ArrayList<ProvinceData> cityData = new ArrayList<>();

        if(type.equals("province")) {
            int i = 56;
            while (i != 0) {
                ProvinceData datas = new ProvinceData();
                datas.setProvincename("sheng");
                datas.setProvinceid("123");
                datas.setAutoid("321");
                provinceData.add(datas);
                i--;
            }
            AddressStore.getInstance().setProvinceData(provinceData);
            callBack.OnMessageCallBack();
        }

        if(type.equals("city")) {
            int j = 50;
            while (j != 0) {
                ProvinceData datas = new ProvinceData();
                datas.setProvincename("shi or qu");
                datas.setProvinceid("123");
                datas.setAutoid("321");
                cityData.add(datas);
                j--;
            }
            AddressStore.getInstance().setCityData(cityData);
            callBack.OnMessageCallBack();
        }
    }


}
