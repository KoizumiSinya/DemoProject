package jp.sinya.multidexdemo1;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import jp.sinya.utils.LogUtils;

/**
 * @author Koizumi Sinya
 * @date 2018/01/06. 19:43
 * @edithor
 * @date
 */
public class FixDexUtils {

    /**
     * 手机程序根目录下的mydex文件夹  下载好的热修复dex文件 会被通过io流 拷贝到这里
     */
    public static final String DEX_DIR = "mydex";
    /**
     * 手机应用程序路径下 自定义的文件夹。因为类加载器只能读取应用安装的路径下的文件
     */
    public static final String LOCAL_DEX_DIR = "opt_dex";

    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    public static void loadFixedDex(Context context) {
        if (context == null) {
            return;
        }

        //遍历所有要修复的dex
        File fileDir = context.getDir(DEX_DIR, Context.MODE_PRIVATE);
        //拿到这个文件夹目录中的所有文件
        File[] listFiles = fileDir.listFiles();

        for (File file : listFiles) {
            if (file.getName().startsWith("classes") && file.getName().endsWith(".dex")) {
                loadedDex.add(file);//存入集合
            }
        }

        LogUtils.Sinya("loadedDexList.size: " + loadedDex.size());

        //新的已修复的dex，与之前手机系统中的dex进行合并
        doDexInject(context, fileDir);
    }

    private static void doDexInject(Context context, File fileDir) {
        String dirPath = fileDir.getAbsolutePath() + File.separator + LOCAL_DEX_DIR;
        LogUtils.Sinya("dirPath: " + dirPath);

        File copyFileDir = new File(dirPath);
        if (!copyFileDir.exists()) {
            copyFileDir.mkdirs();
            LogUtils.Sinya("创建文件夹: " + copyFileDir);
        }

        try {
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            for (File dex : loadedDex) {
                DexClassLoader classLoader = new DexClassLoader(//
                        dex.getAbsolutePath(),// dexPath
                        copyFileDir.getAbsolutePath(),// optimizedDirectory
                        null,// libraryPath
                        pathClassLoader);// ClassLoader parent

                Object dexObj = getPathList(classLoader);
                Object pathObj = getPathList(pathClassLoader);

                Object dexElementList = getDexElements(dexObj);
                Object pathDexElementList = getDexElements(pathObj);

                Object dexElement = combineArray(dexElementList, pathDexElementList);
                Object pathList = getPathList(pathClassLoader);
                setField(pathList, pathList.getClass(), "dexElements", dexElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.SinyaE(e.toString());
        }

    }

    private static Object getPathList(Object baseDexClassLoader) throws Exception {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object getDexElements(Object obj) throws Exception {
        return getField(obj, obj.getClass(), "dexElements");
    }


    private static Object getField(Object obj, Class<?> clazz, String fieldName) throws Exception {
        Field localField = clazz.getDeclaredField(fieldName);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    private static void setField(Object obj, Class<?> clazz, String fieldName, Object value) throws Exception {
        Field localFiled = clazz.getDeclaredField(fieldName);
        localFiled.setAccessible(true);
        localFiled.set(obj, value);
    }

    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; k++) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }
}
