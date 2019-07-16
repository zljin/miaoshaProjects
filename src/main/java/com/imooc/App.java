package com.imooc;

import com.imooc.dao.UserDOMapper;
import com.imooc.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.imooc"})
@RestController
@MapperScan("com.imooc.dao")
public class App
{

    @Autowired
    private UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home() {
        UserDO userDo = userDOMapper.selectByPrimaryKey(1);
        if(userDo==null){
            return "用户对象不存在";
        }else{
            return userDo.getName()+" "+userDo.getTelephone();
        }
    }

    public static void main( String[] args )
    {
        System.out.println("Hello World!");
        SpringApplication.run(App.class,args);
    }
}
