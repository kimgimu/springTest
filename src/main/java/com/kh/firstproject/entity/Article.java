package com.kh.firstproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

//JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 붙여야하고 엔티티라고 부른다
//@Entity 어노테이션이 붙은 클래스 이름으로 springboot가 자동으로 테이블을 만들고 클래스 내부에 선언한 필드 이름으로 테이블을 구성하는 컬럼을 만든다


@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article {

	//기본키로 사용할 필드 선언 primarykey 
	@Id
	@GeneratedValue //기본키 값을 자동으로 생성한다 auto-increment
	private Long id;

	//테이블 컬럼과 매핑한다
	@Column
	private String title;
	@Column
	private String content;
	



	
	
	
	
}
