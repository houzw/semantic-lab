package org.egc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author houzhiwei
 * @date 2020/12/22 23:41
 */
@Slf4j
public class GeneralTest {
    @Test
    public void testRad(){
        double v = Math.toDegrees(1/3600d*30d);
        System.out.println(v);
    }
}
