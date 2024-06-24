package com.henry.diagnosisTest.activity;

import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.henry.basic.BR;
import com.henry.basic.R;
import com.henry.basic.databinding.ActivityDiagnosisuploadlogBinding;
import com.henry.diagnosisTest.adapter.DiagnosisUploadLogAdapter;
import com.henry.diagnosisTest.base.BaseActivity;
import com.henry.diagnosisTest.inter.DiagnosisUploadLogNav;
import com.henry.diagnosisTest.model.DiagnosisKkLogBean;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.Contancts;
import com.henry.diagnosisTest.utils.DDSManager;
import com.henry.diagnosisTest.viewMdodel.DiagnosisUploadLogViewModel;
import com.quectel.communication.util.LogUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DiagnosisUploadLogActivity extends BaseActivity<ActivityDiagnosisuploadlogBinding, DiagnosisUploadLogViewModel> implements DiagnosisUploadLogNav {

    String TAG = getClass().getSimpleName();
    private DiagnosisUploadLogViewModel viewModel;
    private ActivityDiagnosisuploadlogBinding binding;
    private DiagnosisUploadLogAdapter adapter;
    private ArrayAdapter<String> timeAdapter;
    private ArrayAdapter<String> fileSizeAdapter;
    private ArrayAdapter<String> fileNumAdapter;
    private ArrayAdapter<String> fileLocationAdapter;
    private ArrayAdapter<String> spdiagAdapter;
    private String time;
    private int filePathIndex;
    private int fileSizeIndex;
    private int fileNumIndex;
    private String filePath;
    private String fileSize;
    private String fileNum;
    private boolean isSendQxdm, isFilePath, isFileSize, isFileNum = false;
    private boolean isFileOpen = true;
    private ArrayList<DiagnosisModule> diagnosisModuleList;
    HashMap<String, Object> dkReq = new LinkedHashMap<String, Object>();
    HashMap<String, Object> dkList = new LinkedHashMap<String, Object>();

    private int saveTimePosition;
    private boolean saveTimeStatus = false;
    private boolean isFirstTimeOn = true;

    private boolean savaLogStatus = false;
    private boolean isFirstLogOn = true;

    private int saveDiagPosition;
    public String saveDiagTime;
    private boolean saveDiagStatus = false;

    @Override
    public int getBindingVariable() {
        return BR.mDiagnosisUploadLogViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosisuploadlog;
    }

    @Override
    public DiagnosisUploadLogViewModel getViewModel() {
        viewModel = new ViewModelProvider(this).get(DiagnosisUploadLogViewModel.class);
        viewModel.setNavigator(this);
        viewModel.getDiagnosisModuleList(this);
        viewModel.diagnosisModulelist.observe(this, new Observer<ArrayList<DiagnosisModule>>() {
            @Override
            public void onChanged(ArrayList<DiagnosisModule> diagnosisModules) {
                diagnosisModuleList = diagnosisModules;
            }
        });
        viewModel.mSuccessMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                Toast.makeText(DiagnosisUploadLogActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        return viewModel;
    }


    @Override
    protected void initView() {
        super.initView();
        binding = getViewDataBinding();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiagnosisUploadLogAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        timeAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_stytle, this.getResources().getStringArray(R.array.time_options_entries));
        binding.spTime.setAdapter(timeAdapter);

        //binding 空口日志
        fileSizeAdapter = new ArrayAdapter<>(this, R.layout.dropdown_stytle, this.getResources().getStringArray(R.array.qxdm_file_size_values));
        binding.spFileSize.setAdapter(fileSizeAdapter);

        //binding 诊断时长
        spdiagAdapter = new ArrayAdapter<>(this, R.layout.dropdown_stytle, this.getResources().getStringArray(R.array.diag_options_entries));
        binding.spDiag.setAdapter(spdiagAdapter);

        //binding 文件数量
        fileNumAdapter = new ArrayAdapter<>(this, R.layout.dropdown_stytle, this.getResources().getStringArray(R.array.qxdm_file_num_values));
        binding.spFileNum.setAdapter(fileNumAdapter);

        //binding 日志路径
        fileLocationAdapter = new ArrayAdapter<>(this, R.layout.dropdown_stytle, this.getResources().getStringArray(R.array.qxdm_file_path));
        binding.spFilePath.setAdapter(fileLocationAdapter);

        filePath = getResources().getStringArray(R.array.qxdm_file_path)[0];
        fileSize = getResources().getStringArray(R.array.qxdm_file_size_values)[0];
        fileNum = getResources().getStringArray(R.array.qxdm_file_num_values)[0];
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = view.getResources().getStringArray(R.array.time_options_values)[i];
                saveTimePosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnUploadConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveTimeStatus) {
                    Toast.makeText(getViewDataBinding().btnUploadConfim.getContext(), "发送周期上传时间给模组=" + time, Toast.LENGTH_SHORT).show();
                }
                binding.spTime.setEnabled(false);
                saveTimeStatus = true;
            }
        });

        binding.btnUploadCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getViewDataBinding().btnUploadCancle.getContext(), "关闭", Toast.LENGTH_SHORT).show();
                binding.spTime.setEnabled(true);
                saveTimeStatus = false;
            }
        });

        binding.tbTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //TODO  发送周期上传的时间给模组
                    if (!saveTimeStatus && isFirstTimeOn) {
                        Toast.makeText(getViewDataBinding().tbTime.getContext(), "发送周期上传时间给模组=" + time, Toast.LENGTH_SHORT).show();
                    }
                    binding.spTime.setEnabled(false);
                    saveTimeStatus = true;
                    isFirstTimeOn = false;
                } else {
                    Toast.makeText(getViewDataBinding().tbTime.getContext(), "关闭", Toast.LENGTH_SHORT).show();
                    binding.spTime.setEnabled(true);
                    saveTimeStatus = false;
                    isFirstTimeOn = true;
                }
            }
        });

        binding.btnDiagConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spDiag.setEnabled(false);
                saveDiagStatus = true;
                //设置周期诊断
                HashMap<String, Object> param = new LinkedHashMap<String, Object>();
                param.put("cmd", "timer");
                param.put("arg", saveDiagTime);
                DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                Log.d(TAG, "onClick: DiagConfim :" + param.toString());
                Toast.makeText(getViewDataBinding().btnDiagConfim.getContext(), "设置周期诊断！", Toast.LENGTH_LONG).show();
            }
        });

        binding.btnDiagCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spDiag.setEnabled(true);
                saveDiagStatus = false;
                //取消周期诊断 恢复成30s
                HashMap<String, Object> param = new LinkedHashMap<String, Object>();
                param.put("cmd", "timer");
                param.put("arg", "30");
                DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                Log.d(TAG, "onClick: DiagCancle :" + param.toString());
                Toast.makeText(getViewDataBinding().btnDiagCancle.getContext(), "取消周期诊断！", Toast.LENGTH_LONG).show();
            }
        });

        binding.tbDiag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.spDiag.setEnabled(false);
                    saveDiagStatus = true;
                } else {
                    binding.spDiag.setEnabled(true);
                    saveDiagStatus = false;
                }

            }
        });

        binding.spDiag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                saveDiagPosition = i;
                saveDiagTime = view.getResources().getStringArray(R.array.diag_options_values)[saveDiagPosition];
                Log.d(TAG, "onItemSelected: saveDiagTime " + saveDiagTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //文件存储路径
        binding.spFilePath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filePathIndex = position;
                filePath = view.getResources().getStringArray(R.array.qxdm_file_path)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.filePathButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFilePath = isChecked;
                binding.spFilePath.setEnabled(!isChecked);
                LogUtils.d(TAG, "filePathButton->isFilePath = " + isFilePath);
            }
        });

        //单个文件大小
        binding.spFileSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fileSizeIndex = position;
                fileSize = view.getResources().getStringArray(R.array.qxdm_file_size_values)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fileSizeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFileSize = isChecked;
                binding.spFileSize.setEnabled(!isChecked);
                LogUtils.d(TAG, "fileSizeButton->isFileSize = " + isFileSize);
            }
        });

        //总文件数量
        binding.spFileNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fileNumIndex = position;
                fileNum = view.getResources().getStringArray(R.array.qxdm_file_num_values)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fileNumButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFileNum = isChecked;
                binding.spFileNum.setEnabled(!isChecked);
                LogUtils.d(TAG, "fileNumButton->isFileNum = " + isFileNum);
            }
        });


        binding.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (dkList.size() != 0) {
                    dkList.clear();
                }
                if (dkReq.size() != 0) {
                    dkReq.clear();
                }
                isFileOpen = b;
                LogUtils.d(TAG, "toggleButton->isFileOpen = " + isFileOpen);
                LogUtils.d(TAG, "toggleButton isFileOpen = " + isFileOpen);
            }
        });

        binding.btnDebugDiagConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnDebugDiagConfim onClick:  " + isFileOpen);
                if (isFilePath && isFileSize && isFileNum && isFileOpen) {
                    ObservableArrayList<DiagnosisKkLogBean> items = adapter.getItems();
                    DiagnosisKkLogBean bean = new DiagnosisKkLogBean();
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(String.valueOf("file_dir:" + filePath + " "));
                    spannableStringBuilder.append(String.valueOf("file_size:" + fileSize + "M "));
                    spannableStringBuilder.append(String.valueOf("file_num:" + fileNum + " "));
                    bean.setValue(spannableStringBuilder.toString());
                    boolean contains = items.contains(bean);
                    LogUtils.d(TAG, "bean = " + bean.toString() + ",,contains = " + contains);
                    if (contains) {
                        Toast.makeText(getViewDataBinding().addBtn.getContext(), "参数重复！", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.getItems().add(bean);
                        LogUtils.d(TAG, "savaLogStatus = " + savaLogStatus + ",,isFirstLogOn = " + isFirstLogOn + ",,,isSendQxdm = " + isSendQxdm);
                        //send to tbox
                        dkReq.put("cmd", "start_qxdm");
                        dkList.put("output_dir", filePath);
                        dkList.put("file_size", fileSize);
                        dkList.put("file_no", fileNum);
                        dkList.put("extra args", "");
                        dkReq.put("args", dkList);
                        viewModel.UploadLog(diagnosisModuleList.get(0), getApplicationContext(), new Gson().toJson(dkReq));
                        Toast.makeText(getViewDataBinding().addBtn.getContext(), "打开qxdm!", Toast.LENGTH_SHORT).show();
                        getViewDataBinding().addBtn.setText(getResources().getStringArray(R.array.qxdm_btn_text)[1]);
                        isSendQxdm = true;
                    }
                } else {
                    isSendQxdm = false;
                    Toast.makeText(getViewDataBinding().addBtn.getContext(), "空口日志开关未打开！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDebugDiagCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSendQxdm) {
                    //关闭
                    getViewDataBinding().addBtn.setText(getResources().getStringArray(R.array.qxdm_btn_text)[0]);
                    dkReq.clear();
                    dkReq.put("cmd", "stop_qxdm");
                    viewModel.UploadLog(diagnosisModuleList.get(0), getApplicationContext(), new Gson().toJson(dkReq));
                    Toast.makeText(getViewDataBinding().addBtn.getContext(), "关闭qxdm!", Toast.LENGTH_SHORT).show();
                    isSendQxdm = false;
                    //恢复索引并关闭其他空口日志开关
                    Log.d(TAG, "onClick: filePathButton " + isSendQxdm);
                    getViewDataBinding().filePathButton.setChecked(isSendQxdm);
                    isFilePath = isSendQxdm;
                    binding.spFilePath.setSelection(0);

                    getViewDataBinding().fileNumButton.setChecked(isSendQxdm);
                    isFileSize = isSendQxdm;
                    binding.spFileSize.setSelection(0);

                    getViewDataBinding().fileSizeButton.setChecked(isSendQxdm);
                    isFileNum = isSendQxdm;
                    binding.spFileNum.setSelection(0);
                }
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d(TAG, "HHH->isSendQxdm = " + isSendQxdm + ",,isFilePath = " + isFilePath + ",,isFileNum = " + isFileNum + ",,isFileSize = " + isFileSize + ",,,isFileOpen = " + isFileOpen);
                if (isSendQxdm) {
                    //关闭
                    getViewDataBinding().addBtn.setText(getResources().getStringArray(R.array.qxdm_btn_text)[0]);
                    dkReq.clear();
                    dkReq.put("cmd", "stop_qxdm");
                    viewModel.UploadLog(diagnosisModuleList.get(0), getApplicationContext(), new Gson().toJson(dkReq));
                    Toast.makeText(getViewDataBinding().addBtn.getContext(), "关闭qxdm!", Toast.LENGTH_SHORT).show();
                    isSendQxdm = false;
                    return;
                }
                if (isFilePath && isFileSize && isFileNum && isFileOpen) {
                    ObservableArrayList<DiagnosisKkLogBean> items = adapter.getItems();
                    DiagnosisKkLogBean bean = new DiagnosisKkLogBean();
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(String.valueOf("file_dir:" + filePath + " "));
                    spannableStringBuilder.append(String.valueOf("file_size:" + fileSize + "M "));
                    spannableStringBuilder.append(String.valueOf("file_num:" + fileNum + " "));
                    bean.setValue(spannableStringBuilder.toString());
                    boolean contains = items.contains(bean);
                    LogUtils.d(TAG, "bean = " + bean.toString() + ",,contains = " + contains);
                    if (contains) {
                        Toast.makeText(getViewDataBinding().addBtn.getContext(), "参数重复！", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.getItems().add(bean);
                        LogUtils.d(TAG, "savaLogStatus = " + savaLogStatus + ",,isFirstLogOn = " + isFirstLogOn + ",,,isSendQxdm = " + isSendQxdm);
                        //send to tbox
                        dkReq.put("cmd", "start_qxdm");
                        dkList.put("output_dir", filePath);
                        dkList.put("file_size", fileSize);
                        dkList.put("file_no", fileNum);
                        dkList.put("extra args", "");
                        dkReq.put("args", dkList);
                        viewModel.UploadLog(diagnosisModuleList.get(0), getApplicationContext(), new Gson().toJson(dkReq));
                        Toast.makeText(getViewDataBinding().addBtn.getContext(), "打开qxdm!", Toast.LENGTH_SHORT).show();
                        getViewDataBinding().addBtn.setText(getResources().getStringArray(R.array.qxdm_btn_text)[1]);
                        isSendQxdm = true;
                    }
                } else {
                    isSendQxdm = false;
                    Toast.makeText(getViewDataBinding().addBtn.getContext(), "空口日志开关未打开！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading("DiagnosisUploadLogActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSettingData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSettingData();
        if (null != viewModel) {
            viewModel.resetListener();
        }
    }

    /**
     * SharedPreferences保存选项开关数据
     */
    public void saveSettingData() {
        //保存周期上传设置的参数
        ed.putBoolean("time_checked_statue", saveTimeStatus);
        ed.putInt("time_value", saveTimePosition);
        //保存周期诊断设置的参数
        ed.putBoolean("diag_checked_statue", saveDiagStatus);
        ed.putInt("diag_value", saveDiagPosition);
        LogUtils.d(TAG, "HHH->saveSettingData isSendQxdm = " + isSendQxdm + ",,isFilePath = " + isFilePath + ",,isFileNum = " + isFileNum + ",,isFileSize = " + isFileSize + ",,,isFileOpen = " + isFileOpen);
        LogUtils.d(TAG, "HHH->saveSettingData filePathIndex = " + filePathIndex + ",,fileSizeIndex = " + fileSizeIndex + ",,fileSizeIndex = " + fileSizeIndex + ",,,isFileOpen = " + isFileOpen);
        LogUtils.d(TAG, "HHH->saveSettingData filePath = " + filePath + ",,fileSize = " + fileSize + ",,fileNum = " + fileNum);
        //保存空口日志路径,索引
        ed.putString(Contancts.QXDM_LOG_FILE_SAVE_PATH, filePath);
        ed.putInt(Contancts.QXDM_LOG_FILE_SAVE_PATH_INDEX, filePathIndex);
        ed.putBoolean(Contancts.QXDM_LOG_FILE_SAVE_PATH_STATUS, isFilePath);
        //保存单个空口日志大小
        ed.putString(Contancts.QXDM_ONCE_LOG_FILE_SIZE, fileSize);
        ed.putInt(Contancts.QXDM_ONCE_LOG_FILE_SIZE_INDEX, fileSizeIndex);
        ed.putBoolean(Contancts.QXDM_ONCE_LOG_FILE_SIZE_STATUS, isFileSize);

        //保存总空口日志数量
        ed.putString(Contancts.QXDM_LOG_FILE_NUMBER, fileNum);
        ed.putInt(Contancts.QXDM_LOG_FILE_NUMBER_INDEX, fileNumIndex);
        ed.putBoolean(Contancts.QXDM_LOG_FILE_NUMBER_STATUS, isFileNum);
        //空口日志开关
        ed.putBoolean(Contancts.QXDM_LOG_FILE_STATUS, isFileOpen);
        ed.apply();
    }

    /**
     * 加载数据
     */
    public void loadSettingData() {
        saveTimeStatus = sp.getBoolean("time_checked_statue", false);
        if (saveTimeStatus) {
            binding.tbTime.toggle();
        }
        saveTimePosition = sp.getInt("time_value", 0);
        binding.spTime.setSelection(saveTimePosition);

        saveDiagStatus = sp.getBoolean("diag_checked_statue", false);
        if (saveDiagStatus) {
            binding.tbDiag.toggle();
        }
        saveDiagPosition = sp.getInt("diag_value", 0);
        binding.spDiag.setSelection(saveDiagPosition);

        //保存空口日志路径
        filePath = sp.getString(Contancts.QXDM_LOG_FILE_SAVE_PATH, filePath);
        filePathIndex = sp.getInt(Contancts.QXDM_LOG_FILE_SAVE_PATH_INDEX, 0);
        binding.spFilePath.setSelection(filePathIndex);
        isFilePath = sp.getBoolean(Contancts.QXDM_LOG_FILE_SAVE_PATH_STATUS, false);

        //保存单个空口日志大小
        fileSize = sp.getString(Contancts.QXDM_ONCE_LOG_FILE_SIZE, fileSize);
        fileSizeIndex = sp.getInt(Contancts.QXDM_ONCE_LOG_FILE_SIZE_INDEX, fileSizeIndex);
        binding.spFileSize.setSelection(fileSizeIndex);
        isFileSize = sp.getBoolean(Contancts.QXDM_ONCE_LOG_FILE_SIZE_STATUS, false);

        //保存总空口日志数量
        fileNum = sp.getString(Contancts.QXDM_LOG_FILE_NUMBER, fileNum);
        fileNumIndex = sp.getInt(Contancts.QXDM_LOG_FILE_NUMBER_INDEX, fileNumIndex);
        binding.spFileNum.setSelection(fileNumIndex);
        isFileNum = sp.getBoolean(Contancts.QXDM_LOG_FILE_NUMBER_STATUS, false);

        //空口日志开关
        isFileOpen = sp.getBoolean(Contancts.QXDM_LOG_FILE_STATUS, isFileOpen);

        LogUtils.d(TAG, "HHH->loadSettingData isSendQxdm = " + isSendQxdm + ",,isFilePath = " + isFilePath + ",,isFileNum = " + isFileNum + ",,isFileSize = " + isFileSize + ",,,isFileOpen = " + isFileOpen);
        LogUtils.d(TAG, "HHH->loadSettingData filePath = " + filePath + ",,fileSize = " + fileSize + ",,fileNum = " + fileNum);

        if (isFilePath) {
            binding.filePathButton.toggle();
        }
        if (isFileSize) {
            binding.fileSizeButton.toggle();
        }
        if (isFileNum) {
            binding.fileNumButton.toggle();
        }
        if (isFileOpen) {
            binding.toggleButton.toggle();
        }
    }
}
