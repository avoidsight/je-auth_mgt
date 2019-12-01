package com.jiezhan.auth.feign;

import com.jiezhan.auth.model.vo.AccountVo;
import com.jiezhan.auth.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: zp
 * @Date: 2019-11-28 15:08
 * @Description:
 */
@FeignClient(url = "${expoIp}:${expoPort}/api/v2.0/expo/emp",value = "empFeign")
public interface EmployeeFeign {
    /**
     * 登陆账号验证
     * @param account
     * @return
     */
    @GetMapping("/getByAccount")
    Response<AccountVo> getByAccount(@RequestParam("account") String account);
}
