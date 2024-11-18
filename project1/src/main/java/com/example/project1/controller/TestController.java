package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.SampleDTO2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 컨트롤러에 어노테이션 추가
// @Controller : view(html, jsp, ...) 를 찾으러 감
//      @ResponseBody <= 원하는 Mapping 위에 붙여주면 @RestController같은 효과 가능

// @RestController : 리턴하는 값 자체가 브라우저에 보여짐
//      - 객체 리턴 가능 : 원래 객체는 브라우저에 띄울 수 없음
//                  ==> converter 가 필요함(spring boot 가 자동으로 라이브러리를 가지고 있음)
//                  ==>  객체 <==> json

@RestController
public class TestController {

    @GetMapping("/hello")
    public String getMethodName() {
        return "Hello World";
    }

    @GetMapping("/sample2")
    public SampleDTO2 getSample2() {
        return SampleDTO2.builder()
                .mno(11L)
                .firstName("hong")
                .lastName("dong")
                .build();
    }

    @GetMapping("/sample3")
    public List<SampleDTO2> getSample2_1() {

        List<SampleDTO2> list = new ArrayList<>();
        LongStream.rangeClosed(1, 10).forEach(i -> {
            list.add(SampleDTO2.builder()
                    .mno(i)
                    .firstName("hong")
                    .lastName("dong" + i)
                    .build());

        });

        return list;
    }

    @GetMapping("/test2")
    public ResponseEntity<SampleDTO2> check(double height, double weight) {
        SampleDTO2 sampleDTO2 = SampleDTO2.builder()
                .mno(11L)
                .firstName(String.valueOf(height))
                .lastName(String.valueOf(weight))
                .build();

        if (height < 160) {
            return new ResponseEntity<SampleDTO2>(sampleDTO2, HttpStatusCode.valueOf(500));
        } else {
            return new ResponseEntity<SampleDTO2>(sampleDTO2, HttpStatus.OK);
        }
    }

    // @PathVariable

    @GetMapping("/products/{cat}/{pid}")
    public String[] getMethodName(@PathVariable String cat, @PathVariable String pid) {
        return new String[] {
                "category : " + cat,
                "productId : " + pid
        };
    }

}
