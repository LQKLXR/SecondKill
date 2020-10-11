package com.lqk.secondkill.controller;

import com.lqk.secondkill.service.SecondKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lqk
 * @Date 2020/9/19
 * @Description
 */
@RestController
public class SecondKillController  {

    @Autowired
    private SecondKillService secondKillService;


    @GetMapping("/secondKillSynchronized")
    public String secondKillSynchronized(){
        secondKillService.secondKillUnSync();
        return "complete";
    }

    @GetMapping("/countRefresh")
    public void countRefresh(){
        secondKillService.countRefresh();
    }

}
