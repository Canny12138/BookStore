package cn.edu.xmut.soft.controller;

import cn.edu.xmut.common.Constant;
import cn.edu.xmut.soft.entity.UserRole;
import cn.edu.xmut.soft.mapper.UserRoleMapper;
import cn.edu.xmut.utils.DateTool;
import cn.edu.xmut.utils.JwtUtil;
import cn.edu.xmut.utils.MD5Util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.UserMapper;
import cn.edu.xmut.soft.entity.User;

import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/soft/user")
public class UserController extends BaseController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method= RequestMethod.POST,value="/login")
    public Result login( @RequestBody User user) throws Exception {
        String tmp = request.getHeader("token");
        System.out.println("token:"+tmp);

        Result result = new Result();
        QueryWrapper<User> warpper = new QueryWrapper<User>(new User());
        warpper.eq("username",user.getUsername());


        User userExit =
                userMapper.selectOne(warpper);

        if(userExit != null){
            String Md5 = MD5Util.getEncode(user.getPassword(),userExit.getSalt());
            if(Md5.equals(userExit.getPassword())){
                Map<String, Object> map =
                        new HashMap<>();
                map.put("user",userExit);
                String token = JwtUtil.generateToken(userExit.getId());
                map.put("token",token);

                result.setData(map);
                result.success("登陆成功");
            }else{
                result.fail("密码不正确！！");
            }


        }else{
            result.fail("用户名不存在！！");
        }


        return result;
    }

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String username,
        Integer useful) {
        Result result = new Result();
        Page<User> page = new Page<User>(pageNum, pageSize);
        User parms = new User();
        QueryWrapper<User> warpper = new QueryWrapper<User>(parms);

        if(username != null && !"".equals(username)){
            warpper.like("username",username);
        }
        if(useful != null){
            warpper.eq("useful",useful);
        }

        result.setData(userMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody User entity) throws Exception {
                Result result = new Result();
                if (entity.getId() == null) {//add
                    entity.setUseful(Constant.GL_USEFUL);
                    entity.setCreateTime(DateTool.getCurrTime());
                    String pwd = entity.getPassword();
                    String salt = UUID.randomUUID().toString();
                    String password = MD5Util.getEncode(pwd,salt);
                    entity.setSalt(salt);
                    entity.setPassword(password);
                    userMapper.insert(entity);

                    UserRole ur = new UserRole();
                    ur.setUserId(entity.getId());
                    ur.setRoleId("f4735bc4f3d65abbb18698de0598cff0");
                    userRoleMapper.insert(ur);
        } else {//update
            userMapper.updateById(entity);
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
        userMapper.deleteBatchIds(deleteIds);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
    public Result updateUseful( String ids,Integer useful) {
        Result result = new Result();

        for (String id : ids.split(",")) {

            //修改值
            User user = new User();
            user.setUseful(useful);

            //修改条件
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id", id);

            userMapper.update(user, userUpdateWrapper);
        }
        return result;
    }
}
