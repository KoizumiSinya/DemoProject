package jp.sinya.hoffixdemo2;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * @author Koizumi Sinya
 * @date 2018/01/28. 21:35
 * @edithor
 * @date
 */
public class FixDexManager {

    private Context context;
    private File dexDir;

    public FixDexManager(Context context) {
        this.context = context;
        //获取应用可以访问的dex目录
        dexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    public void loadFixDix() throws NoSuchFieldException, IllegalAccessException {
        File[] dexFiles = dexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();
        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);
    }

    private void fixDexFiles(List<File> fixDexFiles) throws NoSuchFieldException, IllegalAccessException {
        //1.先获取已经运行的dexElement
        ClassLoader appClassLoader = context.getClassLoader();
        Object appDexElements = getDexElementNByClassLoader(appClassLoader);

        File optDir = new File(dexDir, "odex");
        if (!optDir.exists()) {
            optDir.mkdirs();
        }

        //3. 把补丁的dexElement插入到已经运行的dexElement的最前面
        for (File fixDexFile : fixDexFiles) {
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(//
                    fixDexFile.getAbsolutePath(),//dex路径
                    optDir,//解压路径
                    null,//so相关的文件路径
                    appClassLoader // parent的classLoader
            );


            Object fixDexElements = getDexElementNByClassLoader(fixDexClassLoader);

            //合并后得到新的 appDexElements
            appDexElements = combineArray(fixDexElements, appDexElements);
        }

        //最后把已经和并的dexElements注入到类加载器中
        injectDexElements(appClassLoader, appDexElements);
    }


    /**
     * 修复dex包
     *
     * @param fixDexPath
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void fixDex(String fixDexPath) throws NoSuchFieldException, IllegalAccessException, IOException {

        File srcFile = new File(fixDexPath);

        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath + " 路径下找不到该文件");
        }

        //如果已经更新的dex文件已经存在，就表示已经下载过
        File destFile = new File(dexDir, srcFile.getName());
        if (destFile.exists()) {
            Log.i("Sinya", "patch [" + fixDexPath + "] is had");
            return;
        }

        //拷贝
        copyFile(srcFile, destFile);

        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        fixDexFiles(fixDexFiles);
    }

    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws NoSuchFieldException, IllegalAccessException {
        //先从 BaseDexClassLoader中获取 DexPathList的类对象 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //获取DexPathList类中的 Element[] dexElements参数
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(pathList, dexElements);
    }


    /**
     * 从classloader中获取 dexElements
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementNByClassLoader(ClassLoader classLoader) throws NoSuchFieldException, IllegalAccessException {
        //先从 BaseDexClassLoader中获取 DexPathList的类对象 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //获取DexPathList类中的 Element[] dexElements参数
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        Object dexElements = dexElementsField.get(pathList);

        return dexElements;
    }

    private static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }

            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileInputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);

        } catch (Exception e) {


        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 合并数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();//获取数组类型
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);//新数组的长度等于两个的和

        Object result = Array.newInstance(localClass, j);//创建一个数组对象

        for (int k = 0; k < j; ++k) {
            if (k < j) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }

        return result;
    }

}
