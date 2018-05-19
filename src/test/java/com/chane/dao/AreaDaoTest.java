package com.chane.dao;

import com.chane.BaseTest;
import com.chane.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fucheng on 2018/5/19.
 */
public class AreaDaoTest extends BaseTest{

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2,areaList.size());
    }

}
