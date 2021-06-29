package com.example.api.controller.v1;

import com.example.api.response.AccountResult;
import com.example.api.response.Response;
import com.example.api.response.ResponseCode;
import com.example.api.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class AccountController {

    private final AccountService accountService;

    //-----------------------------------------------------
    // Rest API 서비스
    //-----------------------------------------------------

    @ApiOperation(value = "모든 계정 조회", notes = "시스템의 모든 계정을 조회한다.")
    @GetMapping(value = "/accounts")
    public Response<Object> getAllAccounts() {
        List<Map<String, Object>> accounts = this.accountService.getAllAccounts();

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), accounts);
    }

    @ApiOperation(value = "계정 조회", notes = "User ID 로 계정 ID를 확인한다.")
    @GetMapping(value = "/account/{user_id}")
    public Response<Object> getAccount(
            @ApiParam(value = "User ID", required = true) @PathVariable(value = "user_id") String userId) {
        AccountResult result = this.accountService.getAccount(userId);

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), result);
    }

    @ApiOperation(value = "계정의 정보 변경", notes = "User ID 로 계정의 정보를 변경한다.")
    @PutMapping(value = "/account")
    public Response<Object> updateAccountPassword(
            @ApiParam(value = "User ID", required = true) @RequestParam(value = "user_id") String userId,
            @ApiParam(value = "User Password") @RequestParam(value = "account_password", required = false) String accountPassword) {

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), Collections.emptyMap());
    }

    @ApiOperation(value = "계정 삭제", notes = "User ID 로 계정을 삭제한다.")
    @DeleteMapping(value = "/account/{user_id}")
    public Response<Object> deleteAccount(
            @ApiParam(value = "User ID", required = true) @PathVariable(value = "user_id") String userId) {

        return Response.of(ResponseCode.SUCCESS.ordinal(), ResponseCode.SUCCESS.name(), Collections.emptyMap());
    }
}
