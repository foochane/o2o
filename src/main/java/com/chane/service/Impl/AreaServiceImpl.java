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

    private static String AREALISTKEY = "arealist";

    @Override
    public List<Area> getAreaList() {
//        String key = AREALISTKEY;
//        List<Area> areaList = null;
//        ObjectMapper mapper = new ObjectMapper();
//        if (!jedisKeys.exists(key)) {
//            areaList = areaDao.queryArea();
//            String jsonString = mapper.writeValueAsString(areaList);
//            jedisStrings.set(key, jsonString);
//        } else {
//            String jsonString = jedisStrings.get(key);
//            JavaType javaType = mapper.getTypeFactory()
//                    .constructParametricType(ArrayList.class, Area.class);
//            areaList = mapper.readValue(jsonString, javaType);
//        }
//        return areaList;
        return areaDao.queryArea();
    }

    @Override
    public List<Area> queryArea() {
        return areaDao.queryArea();
    }
}
