package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.CalcDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@Controller
// @RequestMapping("/sample")
public class SampleController {

    // @RequestMapping("/basic", method=RequestMethod.GET)
    // public void basic() {
    // log.info("basic 컨트롤러 동작");
    // // log.~~ == System.out.println(); 과 같음
    // }

    // basic() -> ()안에 들어가는 값은 forward방식으로 사용됨

    // void : basic <- templates 폴더 아래 경로로 인식
    // /basic => basic.html
    // /sample/ex2 => /sample/ex2.html

    // String : test <- templates 폴더 아래 경로로 인식 <== redirect 하는 경우이거나 template 파일명을
    // 임의 대로 지정하는 경우
    // return "test" => test.html
    // return "/sample/ex2" => sample폴더 아래 - ex2.html

    // 입력값 가져오기
    // 1) HttpServletRequest 사용가능(입력값을 가져오는 용도로 잘 사용하지 않음)
    // 2) 매개변수 선언 (변수명과 이름을 맞추는게 편함)
    // 3) DTO 사용 (POST 메소드가 끝난 후 보여지는 페이지에서 DTO 사용가능)
    // CalcDTO c <== class명인 CalcDTO를 소문자로만 바꾼다 ==> ${calcDTO?.num1}

    // 컨트롤러가 가지고있는 어떤 값을 화면과 공유하는 방법
    // redirect로 움직이지 않는 경우
    // 1) ~~DTO : 기본 공유 가능(클래스명과 동일(첫 자는 소문자))
    // 2) 변수에 들어있는 값을 공유 : model.addAttribute("이름", 변수명(또는 객체명))
    // 3) method(@ModelAttribute int bno) : bno를 공유하고 싶다면
    // 4) method(@ModelAttribute("uDto") UserDTO userDto) : userDto를 공유하고 싶은데
    // 이름(키값)을 다르게 공유

    // redirect로 움직이는 경우
    // RedirectAttributes 이용 - .addAttribute(주소표시), .addFlashAttribute(주소에서 숨김)
    // 1) addAttribute : 경로에 ?다음 직접 따라감
    // 2) addFlashAttribute : 세션 이용 ;jsessionid=임의의 문자집단

    // Model 과 RedirectAttribute 차이점 : 움직이는 방식 / 객체 상태로 담을수 있느냐 없는냐

    @GetMapping("/basic2")
    public String basic2(RedirectAttributes rttr) { // recirect 시 주소의 파라메터로 딸려보내기
        log.info("basic2 컨트롤러 동작");

        rttr.addAttribute("age", 15); // http://localhost:8080/ex1?age=15
        rttr.addAttribute("name", "hong"); // http://localhost:8080/ex1?age=15&name=hong
        rttr.addFlashAttribute("addr", "seoul");

        return "redirect:/ex1"; // sendRedirect() : redirect :경로
    }

    @GetMapping("/basic")
    public String basic(RedirectAttributes rttr) {
        log.info("basic 컨트롤러 동작");

        // session 을 사용하는 것과 동일하나 일시적 보관
        rttr.addFlashAttribute("addr", "seoul"); // http://localhost:8080/ex1;jsessionid=A5037D6D473E3EE850ADC3688AF6AF9E

        return "redirect:/ex1";
    }

    @GetMapping("/ex1")
    public void getEx1() {
        log.info("ex1 컨트롤러 동작");
    }

    @GetMapping("/sample/ex2")
    public void getEx2(@ModelAttribute("param") String param1, String param2) {
        log.info("ex2 컨트롤러 동작");
        log.info(param1);
        log.info(param2);
        log.info("{}, {}", param1, param2);

        // (String param1, String param2, Model model)
        // model.addAttribute("param3", param1);
        // model.addAttribute("param2", param2);
    }

    @GetMapping("/ex3")
    public String getEx3() {
        return "test";
    }

    @GetMapping("/ex4")
    public String getEx4() {
        log.info("ex4 컨트롤러 동작");
        return "/sample/ex2";
    }

    @GetMapping("/sample/calc1")
    public void getCalc1() {
        log.info("calc1 폼 요청");
    }

    /*
     * @PostMapping("/sample/calc1")
     * public void postCalc1(int num1, int num2) {
     * log.info("calc 입력값 가져오기");
     * log.info("{} + {} = {}", num1, num2, (num1 + num2));
     * }
     */

    @PostMapping("/sample/calc1")
    public void postCalc1(CalcDTO calcDTO, Model model) {
        log.info("calc 입력값 가져오기");
        log.info("{} + {} = {}", calcDTO.getNum1(), calcDTO.getNum2(), (calcDTO.getNum1() + calcDTO.getNum2()));

        int result = calcDTO.getNum1() + calcDTO.getNum2();

        // result 값을 화면에 보여주기
        model.addAttribute("result", result);
    }

    @GetMapping("/sample/calc2")
    public void getCalc2() {
        log.info("calc2 폼 요청");
    }

    /*
     * @PostMapping("/sample/calc2")
     * public void postCalc2(int num1, int num2, String op) {
     * log.info("calc2 결과 출력");
     * int result = 0;
     * switch (op) {
     * case "+":
     * result = num1 + num2;
     * break;
     * case "-":
     * result = num1 - num2;
     * break;
     * case "*":
     * result = num1 * num2;
     * break;
     * case "/":
     * result = num1 / num2;
     * break;
     * case "%":
     * result = num1 % num2;
     * break;
     * }
     * 
     * log.info("{} {} {} = {}", num1, op, num2, result);
     * }
     */

    @PostMapping("/sample/calc2")
    public void postCalc2(CalcDTO calcDTO, Model model) {
        log.info("calc2 결과 출력");
        int result = 0;
        switch (calcDTO.getOp()) {
            case "+":
                result = calcDTO.getNum1() + calcDTO.getNum2();
                break;
            case "-":
                result = calcDTO.getNum1() - calcDTO.getNum2();
                break;
            case "*":
                result = calcDTO.getNum1() * calcDTO.getNum2();
                break;
            case "/":
                result = calcDTO.getNum1() / calcDTO.getNum2();
                break;
            case "%":
                result = calcDTO.getNum1() % calcDTO.getNum2();
                break;
        }

        log.info("{} {} {} = {}", calcDTO.getNum1(), calcDTO.getOp(),
                calcDTO.getNum2(), result);
        model.addAttribute("result", result);
    }

    @GetMapping("/fragments/separate")
    public void getSeparate() {
        log.info("separate 페이지 요청");
    }

    @GetMapping("/sample/main")
    public void getMain() {
        log.info("main 페이지 요청");
    }

    @GetMapping("/layouts/layout")
    public void getLayout() {
        log.info("layout 페이지 요청");
    }

    @GetMapping("/sample/list")
    public void getList() {
        log.info("list 페이지 요청");
    }

}
