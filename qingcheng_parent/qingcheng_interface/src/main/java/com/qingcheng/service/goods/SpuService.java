package com.qingcheng.service.goods;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Goods;
import com.qingcheng.pojo.goods.Spu;

import java.util.*;

/**
 * spu业务逻辑层
 */
public interface SpuService {

    public void recoverySpu(String id);

    public void deleteSpu(String id);

    public int pullMany(String ids[]);


    public int putMany(String ids[]);

    public void put(String id);

    public void pull(String id);

    public void audit(String id, String status, String message);

    public Goods findGoodsById(String id);

    public void saveGoods(Goods goods);

    public List<Spu> findAll();


    public PageResult<Spu> findPage(int page, int size);


    public List<Spu> findList(Map<String,Object> searchMap);


    public PageResult<Spu> findPage(Map<String,Object> searchMap,int page, int size);


    public Spu findById(String id);

    public void add(Spu spu);


    public void update(Spu spu);


    public void delete(String id);

}
