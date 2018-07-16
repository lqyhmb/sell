package com.lqy.sell.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by Rodriguez
 * 2018/7/16 14:43
 */
@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    /**
     * 手动方式
     * @param code
     */
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        log.info("进入auth方法...");
        log.info("code={}", code);
        //获取code后，请求以下链接获取access_token：
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx67ea954b523c12ee&secret=3cd4b136aebb4450b16255ee0fb96a55&code=" +
                code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);

        Gson gson = new Gson();
        Map map = gson.fromJson(response, Map.class);
        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        //刷新access_token（如果需要）
        //获取第二步的refresh_token后，请求以下链接获取access_token：
        //https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        // 拉取用户信息
        String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                access_token + "&openid=" + openid + "&lang=zh_CN";
        String userResponse = restTemplate.getForObject(userUrl, String.class);
        log.info("userResponse={}", userResponse);
        // {"access_token":"11_nLQyu2h9L9WILInu7wDoag8lSJgh1RwgyArXmx49NNdtb-d6kBorML981s9yhzlp8-3StqHDFA16DxRVsOamN_nwwaaWue6NPvW4onHCfhs","expires_in":7200,"refresh_token":"11_xn6NBKjGDbJKROlJSlQDQysmoY9F3qFvNmAcXIQUPVUU7k_AYwhR10fUhANLNeZOPl00vgNUFZozAeXNF5eBxbKqGgQwf-tavREXxl_i8-s","openid":"ollSMwATLXTlGUm9nboDkuPzLXcM","scope":"snsapi_base"}
        // "openid":"ollSMwATLXTlGUm9nboDkuPzLXcM"
    }

}
