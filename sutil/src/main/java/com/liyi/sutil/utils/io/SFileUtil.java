package com.liyi.sutil.utils.io;

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

import com.liyi.sutil.utils.prompt.SLogUtil;

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
import java.io.RandomAccessFile;
import java.io.Serializable;

public class SFileUtil {
    private final String TAG = SFileUtil.class.getSimpleName();

    private SFileUtil() {

    }

    public static SFileUtil getInstance() {
        return SFileUtilHolder.INSTANCE;
    }

    private static class SFileUtilHolder {
        private static final SFileUtil INSTANCE = new SFileUtil();
    }

    /**
     * Does it have an external sd card
     */
    public boolean isHasSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * Get the sd card path
     *
     * @return
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * Create folders (you must create folders before you can create files, otherwise you won't find paths)
     *
     * @param path
     */
    public boolean createDir(@NonNull String path) {
        boolean isSuccess;
        File file = new File(path);
        if (!file.exists()) {
            isSuccess = file.mkdirs();
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * Create a file
     *
     * @param path
     * @return
     */
    public boolean createFile(@NonNull String path) {
        boolean isSuccess = false;
        File file = new File(path);
        if (!file.exists()) {
            try {
                isSuccess = file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * Delete the file
     *
     * @param path
     */
    public boolean delete(@NonNull String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                if (file.isFile()) {
                    return deleteFile(path);
                } else {
                    return deleteDir(path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SLogUtil.e(TAG, "Failed to delete file ========> " + e.getMessage());
        }
        return false;
    }

    /**
     * Get the specified file size
     *
     * @param path
     * @return
     */
    public long getFileSize(@NonNull String path) {
        long size = 0;
        try {
            File file = new File(path);
            if (file.exists()) {
                if (file.isFile()) {
                    size += getSingleFileSize(file);
                } else {
                    size += getFileDirSize(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SLogUtil.e(TAG, "Failed to get the specified file size ========> " + e.getMessage());
        }
        return size;
    }


    /**
     * Delete single file
     *
     * @param path
     * @return
     */
    public boolean deleteFile(@NonNull String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Delete file directory
     *
     * @param dir
     * @return
     */
    public boolean deleteDir(@NonNull String dir) {
        // If dir does not end with a file delimiter, the file delimiter is automatically added
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File fileDir = new File(dir);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return false;
        }
        boolean isSuccess = true;
        File[] files = fileDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                isSuccess = deleteFile(files[i].getAbsolutePath());
                if (!isSuccess) {
                    break;
                }
            } else if (files[i].isDirectory()) {
                isSuccess = deleteDir(files[i].getAbsolutePath());
                if (!isSuccess) {
                    break;
                }
            }
        }
        if (!isSuccess) {
            return false;
        }
        return fileDir.delete();
    }

    /**
     * Get the specified file size
     *
     * @param file
     * @return
     * @throws Exception
     */
    public long getSingleFileSize(File file) {
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
     * Get the specified folder size
     *
     * @param dir
     * @return
     * @throws Exception
     */
    public long getFileDirSize(File dir) {
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

    /**
     * 保存string数据
     */
    public static void put(String dir, String key, String value) throws IOException {
        // new File()并未创建文件，只是获取这个路径下的文件对象，但是FileOutputStream会在该文件不存在时，自动创建一个文件
        File file = new File(dir, key);
        // 不用将字符转化为字节数组，高效写入字符
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file), 1024);
            out.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    // 真正的将流强制写入文件中
                    out.flush();
                    // 关闭输出流
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取string数据
     *
     * @return
     */
    public static String getAsString(String dir, String key) {
        File file = new File(dir, key);
        if (!file.exists())
            return null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String readString = "";
            String currentLine;
            // 一行一行读
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
     * 保存byte数据
     */
    public static void put(String dir, String key, byte[] value) {
        File file = new File(dir, key);
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
     * 读取byte[]数据
     *
     * @return
     */
    public static byte[] getAsBinary(String dir, String key) {
        File file = new File(dir, key);
        // 随机读取类，可从指定位置读写文件内容
        RandomAccessFile RAFile = null;
        if (!file.exists())
            return null;
        try {
            // "r"表示只读方式打开文件，"rw"表示读写方式
            RAFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) RAFile.length()];
            RAFile.read(byteArray);
            return byteArray;

//            FileInputStream in = null;
//            in=new FileInputStream(file);
//            byte[] byteArray=new byte[in.available()];
//            // 将文件中内容读取到字节数据中
//            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (RAFile != null) {
                try {
                    RAFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存序列化对象(注：只有序列化对象才可以被保存)
     */
    public static void put(String dir, String key, Serializable value) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            put(dir, key, data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取Serializable数据
     *
     * @return
     */
    public static Object getAsObject(String dir, String key) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            byte[] data = getAsBinary(dir, key);
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
     * 保存bitmap
     */
    public static void put(String dir, String key, Bitmap value) {
        put(dir, key, bitmap2Byte(value));
    }

    /**
     * 获取bitmap
     *
     * @return
     */
    public static Bitmap getAsBitmap(String dir, String key) {
        if (getAsBinary(dir, key) == null) {
            return null;
        }
        return byte2Bitmap(getAsBinary(dir, key));
    }

    /**
     * 将bitmap转化为byte数组
     *
     * @param bmp
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bmp) {
        if (bmp == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩法，将bitmap压缩为jpeg格式，100表示不被压缩
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将byte转化为bitmap
     *
     * @param bytes
     * @return
     */
    public static Bitmap byte2Bitmap(byte[] bytes) {
        if (bytes.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.l
     */
    @TargetApi(19)
    public static String getRealPath(final Context context, final Uri uri) {
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
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
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
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
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
