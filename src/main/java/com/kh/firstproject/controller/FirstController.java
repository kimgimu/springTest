package com.kh.firstproject.controller;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//view Templates 
//화면을 담당하는 기술
//웹페이지를 하나의 틀로 만들고 여기에 변수를 삽입하게 한다
//틀이 되는 페이지가 변수의 값에 따라서 수많은 페이지로 바뀔 수 있다
//그 도구중 하나가 Mustach 

@Controller
public class FirstController {


	@GetMapping("/hi")
	//viewpage로 데이터를 넘길 때 사용할 model 인터페이스객체를 인수로 넣어준다
	public String niceToMeetYou(Model model) {
		//model 인터페이스 객체에 addAttribute() 메서드로 
		//viewpage로 넘겨줄 데이터를 넣어서 보내준다
		model.addAttribute("username","홍길동");
		return "greetings";//viewpage 이름	
	}
	
	@GetMapping("/bye")
	//viewpage로 데이터를 넘길 때 사용할 model 인터페이스객체를 인수로 넣어준다
	public String seeYouNext(Model model) {
		//model 인터페이스 객체에 addAttribute() 메서드로 
		//viewpage로 넘겨줄 데이터를 넣어서 보내준다
		model.addAttribute("username","이서희");
		return "goodbye";//viewpage 이름	
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login.do")
	public String loginDo(String id, String pw, @NotNull Model model) {
		model.addAttribute("id",id);
		model.addAttribute("pw", pw);


		return "Articles/new" ;
	}
	
}
