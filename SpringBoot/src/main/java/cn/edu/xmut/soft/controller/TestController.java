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
import cn.edu.xmut.soft.mapper.TestMapper;
import cn.edu.xmut.soft.entity.Test;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/test")
public class TestController extends BaseController {
    @Autowired
    private TestMapper testMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        Result result = new Result();
        Page<Test> page = new Page<Test>(pageNum, pageSize);
        Test parms = new Test();
        QueryWrapper<Test> warpper = new QueryWrapper<Test>(parms);

        result.setData(testMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody Test entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            testMapper.insert(entity);
        } else {
            testMapper.updateById(entity);
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
        testMapper.deleteBatchIds(deleteIds);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
    public Result updateUseful( String ids,Integer useful) {
        Result result = new Result();

        for (String id : ids.split(",")) {

            //修改值
            Test test = new Test();

            //修改条件
            UpdateWrapper<Test> testUpdateWrapper = new UpdateWrapper<>();
            testUpdateWrapper.eq("id", id);

            testMapper.update(test, testUpdateWrapper);
        }
        return result;
    }
}
