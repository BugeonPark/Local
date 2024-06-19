package com.sds.weatherapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.weatherapp.domain.Urls;
import com.sds.weatherapp.model.image.ImgAPIService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestImgController {
	@Autowired
    private ImgAPIService imgAPIService;

    @GetMapping("/image")
    public ResponseEntity img(@RequestParam(value="keyword") String keyword) throws IOException {
        log.debug("keyword: " + keyword);
        log.debug("img() 호출됨");

        String keywordName = keyword + " 음식"; // 일식으로 검색 시 개기일식이 나옴. -> 일식 음식으로 검색해야 함.

        Urls result = imgAPIService.getImage(keywordName);
        log.debug("키워드 이미지 검색 결과 : " + result);
        ResponseEntity entity = ResponseEntity.ok(result);

        return entity;
    }
}
