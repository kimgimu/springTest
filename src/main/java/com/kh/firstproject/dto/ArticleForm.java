package com.kh.firstproject.dto;

import com.kh.firstproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//롬북
//set get 자동으로 생성해주는 어노테이션 사용
@Setter
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ArticleForm {

	private String title;
	private String content;
	
	public Article toEntity() {
		return new Article(null,title,content);
	}

	@Override
	public String toString() {
		return "ArticleForm [title=" + title + ", content=" + content + "]";
	}
	
	
	
}
