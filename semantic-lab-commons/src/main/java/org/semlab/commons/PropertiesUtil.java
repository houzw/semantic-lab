package org.semlab.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/16 17:51
 */
public class PropertiesUtil
{
    @Autowired
    static Environment env;

    /**
     * get property value from application-[profile].yml
     * @param property key
     * @return property value
     */
    public static String getProperty(String property)
    {
        return env.getProperty(property);
    }
}
