package com.henry.diagnosisTest.communicationImp;

import android.content.Context;


import com.henry.diagnosisTest.communication.CommunicationModule;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.util.FileUtils;

/**
 *
 * Android本地读取配置文件
 */


/**
 * 模块生成器,获取断言文件夹下的json文件
 */
public class ModuleBuilder extends CommunicationBuilderBase {

    private final Context context;

    public ModuleBuilder(Context context, CommunicationModule communicationModule) {
        super(communicationModule);
        this.context = context;
    }

    /**
     * 提供具体的通信动作
     * 读取assets文件夹下的json文件
     */
    public String getCommunicationAction() throws Exception{
        return FileUtils.getFromAssets(context, "moduleList.json");
    }


}
