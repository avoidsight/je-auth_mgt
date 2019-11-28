package com.jiezhan.auth.feign;

import com.jiezhan.auth.model.vo.Employee;
import com.jiezhan.auth.utils.Response;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: zp
 * @Date: 2019-11-28 15:08
 * @Description:
 */
//@FeignClient(url = "${expoIp}:${expoPort}/api/v2.0/expo/emp",value = "empFeign")
public interface EmployeeFeign {
//    @GetMapping("/getByAccount")
    Response<Employee> getByAccount(@RequestParam String account);
}
