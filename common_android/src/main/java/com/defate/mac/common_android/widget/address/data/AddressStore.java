package com.defate.mac.common_android.widget.address.data;

import com.defate.mac.common_android.widget.address.ProvinceData;

import java.util.ArrayList;

public class AddressStore{

    private static AddressStore sInstance = null;

    public static AddressStore getInstance() {
        if (sInstance == null) {
            sInstance = getSync();
        }
        return sInstance;
    }

    private static synchronized AddressStore getSync() {
        if (sInstance == null) {
            sInstance = new AddressStore();
        }

        return sInstance;
    }

    ArrayList<ProvinceData> provinceData = new ArrayList<>();   //记录省信息
    ArrayList<ProvinceData> cityData = new ArrayList<>();       //记录市区信息

    public static void setsInstance(AddressStore sInstance) {
        AddressStore.sInstance = sInstance;
    }

    public ArrayList<ProvinceData> getProvinceData() {
        return provinceData;
    }

    public void setProvinceData(ArrayList<ProvinceData> provinceData) {
        this.provinceData = provinceData;
    }

    public ArrayList<ProvinceData> getCityData() {
        return cityData;
    }

    public void setCityData(ArrayList<ProvinceData> cityData) {
        this.cityData = cityData;
    }
}