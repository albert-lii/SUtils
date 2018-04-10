package com.liyi.sutils.utils.io;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.liyi.sutils.utils.SUtils;
import com.liyi.sutils.utils.log.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * File 相关工具类
 */
public class FileUtil {
    private final String TAG = FileUtil.class.getSimpleName();

    private FileUtil() {
    }

    /***********************************************************************************************
     ****  公用方法
     **********************************************************************************************/

    /**
     * 判断是否有 SD 卡
     *
     * @return {@code true}: 有<br>{@code false}: 沒有
     */
    public static boolean isHasSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取 SD 卡路径
     *
     * @return SD 卡路径
     */
    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 判断是否是文件夹
     *
     * @param dirPath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(String dirPath) {
        return isDir(new File(dirPath));
    }

    /**
     * 判断是否是文件夹
     *
     * @param dir 文件对象
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(File dir) {
        if (dir == null || !dir.exists()) {
            return false;
        }
        return dir.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(String filePath) {
        return isFile(new File(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件对象
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return file.isFile();
    }

    /***********************************************************************************************
     ****  单例操作
     **********************************************************************************************/

    public static FileUtil getInstance() {
        return FileUtilHolder.INSTANCE;
    }

    private static class FileUtilHolder {
        private static final FileUtil INSTANCE = new FileUtil();
    }

    /**
     * 创建文件夹 （你必须先创建文件夹，才能创建文件，否则会报“找不到路径”）
     *
     * @param path 创建的文件夹的路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public boolean createDir(String path) {
        if (TextUtils.isEmpty(path)) return false;
        boolean isSuccess;
        File file = new File(path);
        if (!file.exists()) {
            // 如果文件夹已经存在执行此方法会返回false
            isSuccess = file.mkdirs();
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 创建文件
     *
     * @param path 创建的文件的路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public boolean createFile(String path) {
        if (TextUtils.isEmpty(path)) return false;
        boolean isSuccess = false;
        File file = new File(path);
        if (!file.exists()) {
            try {
                // 如果文件已经存在执行此方法会返回 false
                isSuccess = file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }


    /***********************************************************************************************
     ****  IO 操作
     **********************************************************************************************/

    /**
     * 保存 String 数据
     *
     * @param key   保存 String 数据的文件的路径
     * @param value 保存的 String 数据
     * @throws IOException
     */
    public void put(String key, String value) {
        File file = new File(key);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file), 1024);
            out.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取 String 数据
     *
     * @param key 保存 String 数据的文件的路径
     * @return 保存的 String 数据
     */
    public String getAsString(String key) {
        File file = new File(key);
        if (!file.exists()) {
            return null;
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String readString = "";
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                readString += currentLine;
            }
            return readString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存 byte[]
     *
     * @param key   保存 byte[] 数据的文件的路径
     * @param value 保存的 byte[] 数据
     */
    public void put(String key, byte[] value) {
        File file = new File(key);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取 byte[]
     *
     * @param key 保存 byte[] 数据的文件的路径
     * @return 保存的 byte[] 数据
     */
    public byte[] getAsBinary(String key) {
        File file = new File(key);
        if (!file.exists()) {
            return null;
        }
        byte[] byteArray = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            byteArray = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

    /**
     * 保存序列化对象
     *
     * @param key   保存序列化对象数据的文件的路径
     * @param value 保存的序列化对象数据
     */
    public void put(String key, Serializable value) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            put(key, data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取序列化对象
     *
     * @param key 保存序列化对象数据的文件的路径
     * @return 保存的序列化对象数据
     */
    public Object getAsObject(String key) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            byte[] data = getAsBinary(key);
            if (data == null || data.length == 0) return null;
            bais = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存 bitmap 数据
     *
     * @param key   保存 bitmap 数据的文件的路径
     * @param value 保存的 bitmap 数据
     */
    public void put(String key, Bitmap value) {
        put(key, bitmap2Byte(value));
    }

    /**
     * 读取 bitmap 数据
     *
     * @param key 保存 bitmap 数据的文件的路径
     * @return 保存的 bitmap 数据
     */
    public Bitmap getAsBitmap(String key) {
        if (getAsBinary(key) == null) return null;
        return byte2Bitmap(getAsBinary(key));
    }

    private byte[] bitmap2Byte(Bitmap bmp) {
        if (bmp == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private Bitmap byte2Bitmap(byte[] bytes) {
        if (bytes.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /***********************************************************************************************
     ****  拷贝文件以及文件夹到指定路径
     **********************************************************************************************/

    /**
     * 拷贝文件到指定路径
     *
     * @param oldPath 被拷贝文件的原始路径
     * @param newPath 文件被拷贝后的所在路径
     */
    public void copyFile(@NonNull String oldPath, @NonNull String newPath) {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        if (oldfile.exists()) {
            try {
                fis = new FileInputStream(oldPath);
                fos = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = fis.read(buffer)) != -1) {
                    bytesum += byteread;
                    fos.write(buffer, 0, byteread);
                }
                LogUtil.d(TAG, "copy file success, the total size of the file is ========> " + bytesum + " byte");
            } catch (IOException e) {
                LogUtil.e(TAG, "Copy file error ========> " + e.toString());
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            LogUtil.e(TAG, "Copy file error ========> source file does not exist!");
        }
    }

    /**
     * 拷贝文件夹内容到指定位置
     *
     * @param oldPath 被拷贝文件夹的原始路径
     * @param newPath 文件夹被拷贝后的所在路径
     */
    public void copyDir(@NonNull String oldPath, @NonNull String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File oldDir = new File(oldPath);
            String[] fileNameList = oldDir.list();
            File temp = null;
            for (int i = 0; i < fileNameList.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + fileNameList[i]);
                } else {
                    temp = new File(oldPath + File.separator + fileNameList[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = null;
                    if (newPath.endsWith(File.separator)) {
                        output = new FileOutputStream(newPath + (temp.getName()).toString());
                    } else {
                        output = new FileOutputStream(newPath + File.separator + (temp.getName()).toString());
                    }
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } else {
                    String op = null, np = null;
                    if (oldPath.endsWith(File.separator)) {
                        op = oldPath + fileNameList[i];
                    } else {
                        op = oldPath + File.separator + fileNameList[i];
                    }
                    if (newPath.endsWith(File.separator)) {
                        np = newPath + fileNameList[i];
                    } else {
                        np = newPath + File.separator + fileNameList[i];
                    }
                    copyDir(op, np);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定文件夹中文件的数量
     *
     * @param dir   指定文件夹路径
     * @param isAll {@code true}: 获取所有的文件数量<br>{@code false}: 只获取第一级的文件数量
     * @return 文件的数量
     */
    public int getFileCount(String dir, boolean isAll) {
        if (TextUtils.isEmpty(dir)) return 0;
        int count = 0;
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return count;
        }
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                count += 1;
            } else {
                if (isAll) {
                    getFileCount(files[i].getAbsolutePath(), true);
                }
            }
        }
        return count;
    }

    /***********************************************************************************************
     ****  删除文件
     **********************************************************************************************/

    /**
     * 删除文件或文件夹
     *
     * @param path 文件或文件夹的路径
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean delete(String path) {
        return TextUtils.isEmpty(path) ? true : delete(new File(path));
    }

    /**
     * 删除文件或文件夹
     *
     * @param file 文件或文件夹
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean delete(File file) {
        if (file == null) return true;
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    return deleteFile(file);
                } else {
                    return deleteDir(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "Failed to delete file ========> " + e.getMessage());
        }
        return false;
    }

    /**
     * 删除单个文件
     *
     * @param path 文件的路径
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean deleteFile(String path) {
        return TextUtils.isEmpty(path) ? true : deleteFile(new File(path));
    }

    /**
     * 删除单个文件
     *
     * @param file 文件
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean deleteFile(File file) {
        if (file != null && file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return true;
        }
    }

    /**
     * 删除文件夹
     *
     * @param dir 文件夹的路径
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean deleteDir(String dir) {
        if (TextUtils.isEmpty(dir)) return true;
        // 如果文件夹路径不是以文件分隔符结尾，则在路径末尾自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        return deleteDir(new File(dir));
    }

    /**
     * 删除文件夹
     *
     * @param fileDir 文件夹
     * @return {@code true}: 删除失败<br>{@code false}: 删除成功
     */
    public boolean deleteDir(File fileDir) {
        if (fileDir == null || !fileDir.exists() || !fileDir.isDirectory()) {
            return true;
        }
        boolean isSuccess = true;
        File[] files = fileDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果遍历到的是文件，则删除文件
            if (files[i].isFile()) {
                isSuccess = deleteFile(files[i]);
                if (!isSuccess) break;
            }
            // 如果遍历到的是文件夹，则调用删除文件夹方法，递归
            else if (files[i].isDirectory()) {
                isSuccess = deleteDir(files[i]);
                if (!isSuccess) break;
            }
        }
        if (!isSuccess) return false;
        return fileDir.delete();
    }

    /***********************************************************************************************
     ****  获取文件或者文件夹的大小
     **********************************************************************************************/

    /**
     * 获取指定文件或者文件夹的大小
     *
     * @param path 文件或文件夹的路径
     * @return 文件或文件夹的字节数
     */
    public long getFileSize(String path) {
        return TextUtils.isEmpty(path) ? 0 : getFileSize(new File(path));
    }

    /**
     * 获取指定文件或者文件夹的大小
     *
     * @param file 文件或文件夹
     * @return 文件或文件夹的字节数
     */
    public long getFileSize(File file) {
        if (file == null) return 0;
        long size = 0;
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    size += getSingleFileSize(file);
                } else {
                    size += getFileDirSize(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "Failed to get the specified file size ========> " + e.getMessage());
        }
        return size;
    }

    /**
     * 获取单个文件的大小
     *
     * @param path 文件的路径
     * @return 文件的字节数
     */
    public long getSingleFileSize(String path) {
        return TextUtils.isEmpty(path) ? 0 : getSingleFileSize(new File(path));
    }

    /**
     * 获取单个文件的大小
     *
     * @param file 文件
     * @return 文件的字节数
     * @throws Exception
     */
    public long getSingleFileSize(File file) {
        if (file == null) return 0;
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取指定文件夹的大小
     *
     * @param dir 文件夹的路径
     * @return 文件夹的字节数
     */
    public long getFileDirSize(String dir) {
        return TextUtils.isEmpty(dir) ? 0 : getFileDirSize(new File(dir));
    }

    /**
     * 获取指定文件夹的大小
     *
     * @param dir 文件夹
     * @return 文件夹的字节数
     */
    public long getFileDirSize(File dir) {
        if (dir == null) return 0;
        long size = 0;
        File flist[] = dir.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileDirSize(flist[i]);
            } else {
                size = size + getSingleFileSize(flist[i]);
            }
        }
        return size;
    }

    /***********************************************************************************************
     ****  获取文件的真实路径
     **********************************************************************************************/

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param uri The Uri to query.l
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getRealPath(@NonNull final Uri uri) {
        Context context = SUtils.getApp();
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(@NonNull Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
