package cn.edu.xmut.soft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.StudentMapper;
import cn.edu.xmut.soft.entity.Student;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/student")
public class StudentController extends BaseController {
    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        Result result = new Result();
        Page<Student> page = new Page<Student>(pageNum, pageSize);
        Student parms = new Student();
        QueryWrapper<Student> warpper = new QueryWrapper<Student>(parms);

        result.setData(studentMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody Student entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            studentMapper.insert(entity);
        } else {
            studentMapper.updateById(entity);
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
        studentMapper.deleteBatchIds(deleteIds);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
    public Result updateUseful( String ids,Integer useful) {
        Result result = new Result();

        for (String id : ids.split(",")) {

            //修改值
            Student student = new Student();
            student.setUseful(useful);

            //修改条件
            UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
            studentUpdateWrapper.eq("id", id);

            studentMapper.update(student, studentUpdateWrapper);
        }
        return result;
    }
}
