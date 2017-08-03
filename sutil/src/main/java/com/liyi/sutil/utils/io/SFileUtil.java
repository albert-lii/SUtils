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
import java.io.Serializable;

public class SFileUtil {
    private final String TAG = SFileUtil.class.getSimpleName();

    private SFileUtil() {
    }

    public static SFileUtil get() {
        return SFileUtilHolder.INSTANCE;
    }

    private static class SFileUtilHolder {
        private static final SFileUtil INSTANCE = new SFileUtil();
    }


    /*--------------------------------------------------------------------------------------------*/

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
    /*--------------------------------------------------------------------------------------------*/


    /**
     * IO operations-------------------------------------------------------------------------------/
     */
    /**
     * Save string data
     *
     * @param dir
     * @param key
     * @param value
     * @throws IOException
     */
    public void put(String dir, String key, String value) {
        File file = new File(dir, key);
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
     * Read string data
     *
     * @return
     */
    public String getAsString(String folder, String key) {
        File file = new File(folder, key);
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
     * Save the byte array data
     */
    public void put(String dir, String key, byte[] value) {
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
     * Read the byte array data
     *
     * @return
     */
    public byte[] getAsBinary(String dir, String key) {
        File file = new File(dir, key);
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
     * Save the serialized object
     */
    public void put(String dir, String key, Serializable value) {
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
     * Read the Serializable data
     *
     * @return
     */
    public Object getAsObject(String dir, String key) {
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
     * Save the bitmap data
     */
    public void put(String dir, String key, Bitmap value) {
        put(dir, key, bitmap2Byte(value));
    }

    /**
     * Read the bitmap data
     *
     * @param dir
     * @param key
     * @return
     */
    public Bitmap getAsBitmap(String dir, String key) {
        if (getAsBinary(dir, key) == null) {
            return null;
        }
        return byte2Bitmap(getAsBinary(dir, key));
    }

    private byte[] bitmap2Byte(Bitmap bmp) {
        if (bmp == null) {
            return null;
        }
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
    /*--------------------------------------------------------------------------------------------*/


    /**
     * Copy File-----------------------------------------------------------------------------------/
     */
    /**
     * Copy a single file to the specified path
     *
     * @param oldPath
     * @param newPath
     */
    public void copyFile(String oldPath, String newPath) {
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
                SLogUtil.d(TAG, "Copy file success, the total size of the file is ========> " + bytesum + " byte");
            } catch (IOException e) {
                SLogUtil.e(TAG, "Copy file error ========> " + e.toString());
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
            SLogUtil.e(TAG, "Copy file error ========> source file does not exist!");
        }
    }

    /**
     * Copy the entire folder contents
     *
     * @param oldPath
     * @param newPath
     */
    public void copyDir(String oldPath, String newPath) {
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
     * Get the number of files in the specified directory
     *
     * @param dir
     * @param isAll True means to get the number of files, otherwise you only get the number of files at that level
     * @return
     */
    public int getFileCount(String dir, boolean isAll) {
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
    /*--------------------------------------------------------------------------------------------*/


    /**
     * Delete the file-----------------------------------------------------------------------------/
     */
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
    /*--------------------------------------------------------------------------------------------*/


    /**
     * Get the size of file------------------------------------------------------------------------/
     */
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
    /*--------------------------------------------------------------------------------------------*/


    /**
     * Get the real path of the file---------------------------------------------------------------/
     */
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
    public String getRealPath(final Context context, final Uri uri) {
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
    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
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
