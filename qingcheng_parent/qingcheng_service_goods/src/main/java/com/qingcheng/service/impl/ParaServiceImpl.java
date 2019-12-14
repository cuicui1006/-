package com.qingcheng.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ParaMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Para;
import com.qingcheng.pojo.goods.Template;
import com.qingcheng.service.goods.ParaService;
import com.qingcheng.service.goods.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = ParaService.class)
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;

    @Autowired
    private TemplateService templateService;

    /**
     * 返回全部记录
     * @return
     */
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<Para> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Para> paras = (Page<Para>) paraMapper.selectAll();
        return new PageResult<Para>(paras.getTotal(),paras.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<Para> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return paraMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Para> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Para> paras = (Page<Para>) paraMapper.selectByExample(example);
        return new PageResult<Para>(paras.getTotal(),paras.getResult());
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param para
     */
    @Transactional
    public void add(Para para) {
        paraMapper.insert(para);
        //增加的时候讲模板的数量也+1
        Template template = templateService.findById(para.getTemplateId());
        template.setParaNum(template.getParaNum()+1);
        templateService.update(template);
    }

    /**
     * 修改
     * @param para
     */
    public void update(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
    }

    /**
     *  删除
     * @param id
     */
    @Transactional
    public void delete(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
        //删除的时候将模板中的数量减一
        Para para = paraMapper.selectByPrimaryKey(id);
        Template template = templateService.findById(para.getTemplateId());
        template.setParaNum(template.getParaNum()-1);
        templateService.update(template);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 名称
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            // 选项
            if(searchMap.get("options")!=null && !"".equals(searchMap.get("options"))){
                criteria.andLike("options","%"+searchMap.get("options")+"%");
            }

            // id
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }
            // 排序
            if(searchMap.get("seq")!=null ){
                criteria.andEqualTo("seq",searchMap.get("seq"));
            }
            // 模板ID
            if(searchMap.get("templateId")!=null ){
                criteria.andEqualTo("templateId",searchMap.get("templateId"));
            }

        }
        return example;
    }

}
