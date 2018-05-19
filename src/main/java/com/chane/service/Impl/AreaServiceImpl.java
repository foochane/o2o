package com.chane.service.Impl;

import com.chane.dao.AreaDao;
import com.chane.entity.Area;
import com.chane.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fucheng on 2018/5/19.
 */

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> queryArea() {
        return areaDao.queryArea();
    }
}
