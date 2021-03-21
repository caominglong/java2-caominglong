package com.java2.firstweek;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) {
        byte[] bytes = null;
        try {
            bytes = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 读取文件，对byte处理进行处理（x=255-x）
     * @return byte[]
     * @throws IOException
     */
    public byte[] readFile() throws IOException {
        File file = new File("/Users/cao/Desktop/思维导图学习文件/java2期训练营/第一周/Hello.xlass");
        FileInputStream fileInputStream = new FileInputStream(file);
        // fileInputStream.available()从文件描述符中获取文件大小
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        // 将数组里面的每个字节都用255去减
        byte[] newBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = (byte) (255 - (int) bytes[i]);
        }
        return newBytes;

    }

    public static void main(String[] args) {
        try {
            Class xlass = new CustomClassLoader().findClass("Hello");
            Method method = xlass.getDeclaredMethod("hello");
            method.invoke(xlass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}