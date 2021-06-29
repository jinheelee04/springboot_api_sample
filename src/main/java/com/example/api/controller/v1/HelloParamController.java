package com.example.api.controller.v1;

import com.example.api.request.param.Param;
import com.example.api.response.AccountResult;
import com.example.api.response.Response;
import com.example.api.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class HelloParamController {

    /**
     * - PathVariable: 파라미터를 URL 경로에 포함시키는 방법
     * curl -v 'http://192.168.0.114:8080/v1/hello/100'
     *
     * @param userId
     * @return
     */
    @GetMapping("/hello/{userId}")
    public Long hello(@PathVariable Long userId) {
        log.debug("userId: {}", userId);
        return userId;
    }

    /**
     * - RequestParam: 요청 파라미터를 메소드에서 1:1로 받기 위해서 사용
     * curl -v 'http://192.168.0.114:8080/v1/hello?userId=100&userName=userName01' | jq
     *
     * @param userId
     * @param userName
     * @return
     */
    @GetMapping(value = "/hello")
    public Response<Object> hello(
        @RequestParam(value = "userId") Long userId,
        @RequestParam(value = "userName", required = false, defaultValue = "demo")String userName) {

        log.debug("userId: {}, userName: {}", userId, userName);

        // Map<String, Object> result = new HashMap<>();
        AccountResult result = new AccountResult();
        result.setAccountNum(1);
        result.setAccountId("account01");

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), result);
    }

    /**
     * ModelAttribute: 여러 파라미터들을 1대1로 객체에 바인딩
     *
     * curl -v 'http://192.168.0.114:8080/v1/hello-attr?userId=user02&paramId=200&paramName=param02'
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/hello-attr")
    public Response<Object> helloAttribute(
            @RequestParam(value = "userId") String userId,
            @ModelAttribute Param param) {

        log.debug("userId: {}, Param: {}", userId, param);

        // Map<String, Object> result = new HashMap<>();
        AccountResult result = new AccountResult();
        result.setAccountNum(2);
        result.setAccountId("account02");

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), result);
    }

    /**
     * RequestBody: Http 요청의 Body내용을 Java Object로 변환시켜주는 역할
     * POST방식으로 Json의 형태로 넘겨온 데이터를 객체로 바인딩하기 위해 사용
     *
     * curl -v -X POST -H 'Content-Type: application/json' -d '{"paramId": 300, "paramName": "param03"}' 'http://192.168.0.114:8080/v1/hello-body?userId=user03'
     *
     * @param userId
     * @param param
     * @return
     */
    @PostMapping(value = "/hello-body")
    public Response<Object> helloBody(
            @RequestParam(value = "userId") String userId,
            @RequestBody Param param) {

        log.debug("userId: {}, Param: {}", userId, param);

        AccountResult result = new AccountResult();
        result.setAccountNum(3);
        result.setAccountId("account03");

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), result);
    }

}
