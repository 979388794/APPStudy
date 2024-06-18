package com.quectel.communication.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * 扫描外存，返回指定路径下文件集合
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @param isAudio  是否录音文件，是的话，就从根目录读取， 不是就从 根目录/quectel 下读取
     * @return 文件集合
     */
    public static List<File> getSpecificPathOfFile(Context context, String filePath, boolean isAudio) {
        List<String> fileUrls = new ArrayList<>();
        //从外存中获取
        Uri fileUri = Uri.parse(filePath);
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection = new String[]{MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE};
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        Cursor cursor;
        if (isAudio) {
            cursor = resolver.query(fileUri, projection, MediaStore.Files.FileColumns.DATA + " LIKE '%.amr%'", null,
                    sortOrder);
        } else {
            cursor = resolver.query(fileUri, projection, MediaStore.Files.FileColumns.DATA + " LIKE '%quectel%'",
                    null, sortOrder);
        }
        if (cursor == null) {
            return null;
        }
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        if (cursor.moveToLast()) {
            do {
                //输出文件的完整路径
                String data = cursor.getString(0);
                Log.e(FileUtils.class.getSimpleName(), data);
                fileUrls.add(data);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        List<File> rets = new ArrayList<>();
        for (int i = 0; i < fileUrls.size(); i++) {
            File file = new File(fileUrls.get(i));
            rets.add(file);
        }
        Log.e(FileUtils.class.getSimpleName(), "文件大小: " + rets.size());
        return rets;
    }

    /**
     * 扫描外存，删除指定路径下文件集合
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @param isAudio  是否录音文件，是的话，就从根目录读取， 不是就从 根目录/quectel 下读取
     */
    public static void deleteSpecificPathOfFile(Context context, String filePath, boolean isAudio) {
        //从外存中获取
        Uri fileUri = Uri.parse(filePath);
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        if (isAudio) {
            resolver.delete(fileUri, MediaStore.Files.FileColumns.DATA + " LIKE '%.amr%'", null);
        } else {
            resolver.delete(fileUri, MediaStore.Files.FileColumns.DATA + " LIKE '%quectel%'",
                    null);
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setData(fileUri);
        context.sendBroadcast(intent);
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param param 文件路径
     * @return 获取一个用于打开APK文件的intent
     */
    public static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param param 文件路径
     * @return 获取一个用于打开APK文件的intent
     */
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }


    /**
     * Android获取一个用于打开Html文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开Html文件的intent
     */
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
                .encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }


    /**
     * Android获取一个用于打开PPT文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开PPT文件的intent
     */
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开Excel文件的intent
     */
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * Android获取一个用于打开Word文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开Word文件的intent
     */
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * Android获取一个用于打开CHM文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开CHM文件的intent
     */
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @param param        文件路径
     * @param paramBoolean 参数是否是文件路径
     * @return Android获取一个用于打开文本文件的intent
     */
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     *
     * @param param 文件路径
     * @return Android获取一个用于打开PDF文件的intent
     */
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 文件链表
     */
    public static List<File> searchFileInDir(File dir, String fileName) {
        if (dir == null || !dir.isDirectory()) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
//                (str.indexOf("ABC")!=-1
                if (file.getName().toUpperCase().contains(fileName.toUpperCase())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(searchFileInDir(file, fileName));
                }
            }
        }
        return list;
    }

    public static String getFromAssets(Context context, String fileName) throws Exception {
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName), StandardCharsets.UTF_8);
            bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufReader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputReader != null) {
                inputReader.close();
            }
            if(bufReader != null) {
                bufReader.close();
            }
        }
        return "";
    }

    /**
     * 存储JSON文件到本地
     * @param context
     * @param json
     * @param fileName
     */
    public static boolean writeJson(Context context, String json, String fileName){
        OutputStream out = null;
        try {
            File file = new File(context.getExternalFilesDir(null),fileName);
            out = new FileOutputStream(file);
            out.write(json.getBytes(StandardCharsets.UTF_8));
            //out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try{
                if(out != null){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        /**
        File dir = new File(context.getExternalFilesDir(null)+"/changan/");
        if(!dir.exists()){
            dir.mkdirs();
            try {
                File file = new File(dir,fileName);
                OutputStream out = new FileOutputStream(file);
                out.write(json.getBytes(StandardCharsets.UTF_8));
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }**/
    }

    /**
     * 读取本地JSON文件
     * @param filePath
     * @return
     */
    public static String readJsonFile(String filePath){
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        try {
            File file = new File(filePath);
            in = new FileInputStream(file);
            int tmp;
            while((tmp = in.read()) != -1){
                sb.append((char)tmp);
            }
            //in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
