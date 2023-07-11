package cn.edu.xmut.springboot.controller;

import cn.edu.xmut.springboot.utils.Result;
import org.springframework.web.bind.annotation.*;
import cn.edu.xmut.springboot.entity.User;

@RestController
@RequestMapping("/test")
public class Test {

    @RequestMapping("/hello")
    public String hello(){
        return "hello springboot";
    }

    @GetMapping("/get")
    public Result get(){
        Result result = new Result();
        result.unauthorized("unauthorized");
        //result.fail("Fail..");
        //result.success("success.....");
        return result;
    }

    //@RequestMapping(value = "postTest",method = RequestMethod.POST)
    @PostMapping("/postTest")
    public Result postTest(String id){
        Result result = new Result();
        result.success(id);
        return result;
    }

    @PostMapping("/postJson")
    public Result postJson(@RequestBody  User user){
//        Result result = new Result();
//        result.success(user.getName());
//        return result;
        Result result = new Result();
        result.againLogin();

        return result;
    }

}
