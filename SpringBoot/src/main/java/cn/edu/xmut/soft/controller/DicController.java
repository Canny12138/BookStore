package cn.edu.xmut.soft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.DicMapper;
import cn.edu.xmut.soft.entity.Dic;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/dic")
public class DicController extends BaseController {
    @Autowired
    private DicMapper dicMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String dictypeId) {
        Result result = new Result();
        Page<Dic> page = new Page<Dic>(pageNum, pageSize);
        Dic parms = new Dic();
        QueryWrapper<Dic> warpper = new QueryWrapper<Dic>(parms);

        warpper.eq("dictype_id",dictypeId);

        result.setData(dicMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody Dic entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            dicMapper.insert(entity);
        } else {
            dicMapper.updateById(entity);
        }
        return result;
    }
    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete( String ids) {
        Result result = new Result();
        List<String> deleteIds = new ArrayList<String>();
        for (String id : ids.split(",")) {
            deleteIds.add(id);
        }
        dicMapper.deleteBatchIds(deleteIds);
        return result;
    }

}
