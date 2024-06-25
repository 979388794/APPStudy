package com.henry.diagnosisTest.utils;

import android.text.TextUtils;

import com.quectel.communication.util.LogUtils;

public class FormatChangeUtils {

    public static String test = "LTE:\n\tCID=158245684,PLMN=0x64 0xf0 0x10,TAC=39444,PCI=10,EARFCN=1825,RSSI=-64,MCC=460,MNC=01\n";
    public static String test2 = "LTE网络小区信息:\n\t小区标识(CID)=158245684,公共陆地移动网(PLMN)=0x64 0xf0 0x10,跟踪区域代码(TAC)=39444,物理小区标识(PCI)=10,绝对无线频率信道号(EARFCN)=1825,接收信号强度指示(RSSI)=-89,移动信号国家码(MCC)=460,移动网络代码(MNC)=01\n\t小区标识(CID)=0,公共陆地移动网(PLMN)=0x00 0x00 0x00,跟踪区域代码(TAC)=0,物理小区标识(PCI)=0,绝对无线频率信道号(EARFCN)=100,接收信号强度指示(RSSI)=0,移动信号国家码(MCC)=0,移动网络代码(MNC)=00\n\t小区标识(CID)=0,公共陆地移动网(PLMN)=0x00 0x00 0x00,跟踪区域代码(TAC)=0,物理小区标识(PCI)=0,绝对无线频率信道号(EARFCN)=300,接收信号强度指示(RSSI)=0,移动信号国家码(MCC)=0,移动网络代码(MNC)=00\n";

    //CID=   ---> 小区标识(CID)=
    public static final String CID = "CID=";
    public static final String CID_REPLACE = "小区标识(CID)=";

    //PLMN=   ---> 公共陆地移动网(PLMN)=
    public static final String PLMN = "PLMN=";
    public static final String PLMN_REPLACE = "公共陆地移动网(PLMN)=";

    //LAC  ---  位置区码
    public static final String LAC = "LAC=";
    public static final String LAC_REPLACE = "位置区码(LAC)=";

    //ARFCN ---  绝对无线频道编号
    public static final String ARFCN = "ARFCN=";
    public static final String ARFCN_REPLACE = "绝对无线频道编号(ARFCN)=";

    //BSIC ---  基站识别码
    public static final String BSIC = "BSIC=";
    public static final String BSIC_REPLACE = "基站识别码(BSIC)=";

    //RSSI ---  接收信号强度指示
    public static final String RSSI = "RSSI=";
    public static final String RSSI_REPLACE = "接收信号强度指示(RSSI)=";

    //MCC  ---  移动信号国家码
    public static final String MCC = "MCC=";
    public static final String MCC_REPLACE = "移动信号国家码(MCC)=";


    //MNC  ---  移动网络代码
    public static final String MNC = "MNC=";
    public static final String MNC_REPLACE = "移动网络代码(MNC)=";


    //LCID ---  UTRAN小区标识
    public static final String LCID = "LCID=";
    public static final String LCID_REPLACE = "UTRAN小区标识(LCID)=";


    //UARFCN ---  绝对无线频率信道号
    public static final String UARFCN = "UARFCN=";
    public static final String UARFCN_REPLACE = "绝对无线频率信道号(UARFCN)=";


    //PSC  ---  主扰码
    public static final String PSC = "PSC=";
    public static final String PSC_REPLACE = "主扰码(PSC)=";


    //TAC  ---  跟踪区域代码
    public static final String TAC = "TAC=";
    public static final String TAC_REPLACE = "跟踪区域代码(TAC)=";


    //PCI  ---  物理小区标识
    public static final String PCI = "PCI=";
    public static final String PCI_REPLACE = "物理小区标识(PCI)=";


    //EARFCN ---  绝对无线频率信道号
    public static final String EARFCN = "EARFCN=";
    public static final String EARFCN_REPLACE = "绝对无线频率信道号(EARFCN)=";


    //RSRP ---  参考信号接收功率
    public static final String RSRP = "RSRP=";
    public static final String RSRP_REPLACE = "参考信号接收功率(RSRP)=";

    //RSRQ ---  参考信号接收质量
    public static final String RSRQ = "RSRQ=";
    public static final String RSRQ_REPLACE = "参考信号接收质量(RSRQ)=";


    //SNR  ---  信噪比
    public static final String SNR = "SNR=";
    public static final String SNR_REPLACE = "信噪比(SNR)=";

    //ECIO ---  载干比
    public static final String ECIO = "ECIO=";
    public static final String ECIO_REPLACE = "载干比(ECIO)=";


    //LEVEL ---  信号强度等级
    public static final String LEVEL = "LEVEL=";
    public static final String LEVEL_REPLACE = "信号强度等级(LEVEL)=";


    //en /cn 顺序一定要保持一致
    public static final String[] engArray = {
            LCID,
            PLMN,
            LAC,
            ARFCN,
            BSIC,
            RSSI,
            MCC,
            MNC,
            CID,
            UARFCN,
            PSC,
            TAC,
            PCI,
            EARFCN,
            RSRP,
            RSRQ,
            SNR,
            ECIO,
            LEVEL,
    };

    public static final String[] cnArray = {
            LCID_REPLACE,
            PLMN_REPLACE,
            LAC_REPLACE,
            ARFCN_REPLACE,
            BSIC_REPLACE,
            RSSI_REPLACE,
            MCC_REPLACE,
            MNC_REPLACE,
            CID_REPLACE,
            UARFCN_REPLACE,
            PSC_REPLACE,
            TAC_REPLACE,
            PCI_REPLACE,
            EARFCN_REPLACE,
            RSRP_REPLACE,
            RSRQ_REPLACE,
            SNR_REPLACE,
            ECIO_REPLACE,
            LEVEL_REPLACE
    };

    public static String formatChange(String json) {
        LogUtils.d(FormatChangeUtils.class,"formatChange = " + json);
        if (TextUtils.isEmpty(json)) {
            return json;
        }
        String tempStr = json;
        //todo 先完成LCID，再处理CID
        for (int i = 0; i < engArray.length; i++) {
            if (tempStr.contains(engArray[i])) {
                tempStr = tempStr.replaceAll(engArray[i], cnArray[i]);
            }
        }
        return tempStr;
    }
}
