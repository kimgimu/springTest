package com.kh.firstproject.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.firstproject.dto.ArticleForm;
import com.kh.firstproject.entity.Article;
import com.kh.firstproject.repository.ArticleRepository;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArticleController {

	//JpaRepository 인터페이스의 객체를 선언하고 @Autowired로 초기화한다
	//spring boot가 미리 생성해 놓은 객체를 가져다가 자동으로 연결한다 
	@Autowired
	private ArticleRepository articleRepository;
	
	//뷰페이지 만들기
	@GetMapping("/Articles/new")
	public String newArticleForm() {
		return "Articles/new";
	}
	
	//만약 form에서 넘어오는 데이터를 받겠다 하면 @PostMapping 
	@PostMapping("/Articles/create")
	public String createArticle(ArticleForm form) {
		System.out.println(form);
		
		//DTO의 데이터를 Entity로 변환한다
		Article article = form.toEntity();
		System.out.println(article);
		
		//Repository에게 Entity를 데이터베이스에 저장하게 한다
		//id가 자동으로 증가된다 
		Article saved = articleRepository.save(article);
		System.out.println(saved);

		System.out.println();

		//1건이 저장이 완료되었으면 redirect를 이용해서 (새로운 요청) 목록보기나 작성한 글보기로 넘겨준다
		return"redirect:/Articles/"+saved.getId();
		//return "redirect:/Articles/saved.getId() //작성한 글 보기
	}

	//브라우저에서 /articles/글번호 형태의 요청을 받아서 처리한다
	//ex) {}/articles/1, {}/articles/2,
	//{변수명}을 통해서 받는 데이터를 저장할 변수를 만들고
	//url의 들어오는 값을 변수명으로 매칭시키서 사용할 수 있도록 어노테이션을 사용한다
	@GetMapping("/Articles/{id}")
	public String show(@PathVariable Long id, Model model){
		System.out.println("컨트롤러의 show() 메서드 실행");
		System.out.println("id="+id);

		//id 한건 마다 해당되는 데이터를 테이블에서 가지고 온다
		//findById() id 값을 넣어주면 테이블에서 찾아서 결과를 반환. Article 타입으로 반환
		//만약 데이터가 없다면 orElse(null) 메서드가 실행하면
		//null 리턴시킨다

		//찾았는데 혹시라도 없으면 null을 반환하라는 메소드
		Article articleEntity = articleRepository.findById(id).orElse(null);

		//테이블에서 데이터를 가져와서 show.mustache 파일로 넘기기 위해서 model 인터페이스 객체에 넣어준다
		model.addAttribute("article",articleEntity);

		//뷰페이지로 이동한다
		return"/Articles/show";
	}
	@GetMapping("/Articles")
	public String index(Model model){
		System.out.println("컨트롤러의 index() 메서드 실행");
		//테이블에 저장된 모든 글 목록을 얻어온다
		List<Article> articlesEntityList = articleRepository.findAll();

		//가져온 글의 목록을 index.mustache로 넘겨주기 위해서 model 객체에 저장한다
		model.addAttribute("articleList",articlesEntityList);

		return "Articles/index";
	}

	//글번호를 가지고 수정하는 메서드
	@GetMapping("/Articles/{id}edit")
	public String edit(@PathVariable Long id,Model model){
		System.out.println("컨트롤러의 edit() 메서드를 실행");
		System.out.println("id=" + id);

		//수정할 데이터를 얻어온다
		Article articleEntity = articleRepository.findById(id).orElse(null);

		//수정할 데이터를 edit.mustache로 넘기기 위해 model인터페이스에 넣어준다
		model.addAttribute("article",articleEntity);

		return "Articles/edit";
	}

	@PostMapping("/Articles/update")
	//form에서 넘어오는 데이터 전체를 받기 위해서 커맨드 객체를 사용한다
	public String update(ArticleForm form,Long id){
		System.out.println("컨트롤러의 update() 메서드를 실행");
		System.out.println(form.toString());
		//DTO -> Entity로 변환
		Article article = form.toEntity();
		article.setId(id);
		System.out.println(article);
		//데이터베이스에 저장된 수정할 데이터를 얻어와서 Entity로 수정한 후 데이터베이스에 저장
		Article target = articleRepository.findById(article.getId()).orElse(null);
		if(target != null){
			articleRepository.save(article);
		}

		//수정한 글 1건만 보여주고싶을때는
		return "redirect:/Articles/"+id;
	}

	@GetMapping("/Articles/delete{id}")
	public String delete(@PathVariable Long id, RedirectAttributes rttr){
		Article article = articleRepository.findById(id).orElse(null);
		if(article != null){
		articleRepository.delete(article);
		rttr.addFlashAttribute("msg",id+"번 글 삭제완료");
		}

		return "redirect:/Articles";
	}

}
