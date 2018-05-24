package com.chane.service;

import com.chane.entity.Area;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.util.List;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface  AreaService {

    /**
     *
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    List<Area> getAreaList() ;

    List<Area> queryArea();
}
