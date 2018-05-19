package com.chane.service;

import com.chane.BaseTest;
import com.chane.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fucheng on 2018/5/19.
 */
public class AreaServiceTest extends BaseTest{

    @Autowired
    private AreaService areaService;

    @Test
    @Ignore
    public void testGetAreaList(){
        List<Area> areaList = areaService.queryArea();
        assertEquals("北苑",areaList.get(0).getAreaName());
    }
}
