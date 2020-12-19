package org.egc.semantic.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/16 16:42
 */
public class StringUtil {
    /**
     * 获得jena可读取的本体文件地址，以file:或http://开头
     *
     * @param filepath 路径，可以是文件目录地址 H:\*\*.owl|file:或http://开头地址
     * @return 转换之后的地址 uri file path
     */
    public static String getUriFilePath(String filepath) {
        if (filepath.startsWith("file:") || filepath.startsWith("http://")) {
            return filepath;
        } else {
            filepath = "file:" + filepath;
            filepath = filepath.replaceAll("\\\\", "/");
            return filepath;
        }
    }

    /**
     * 只移除第一个下划线
     *
     * @param src the src
     * @return string
     * @Houzw at 2016年2月1日下午5:38:54
     */
    public static String removeFirstUnderline(String src) {
        if (src.indexOf('_') == 0)
            src = src.substring(1);
        return src;
    }

    public static String lang(String lang) {
        return StringUtils.isNotBlank(lang) ? lang : "en";
    }
}
