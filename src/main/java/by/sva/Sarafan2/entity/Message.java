package by.sva.Sarafan2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})

public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Объявление интерфейсов в классе Views
	@JsonView(Views.Id.class) // в браузер будет передано поле, помеченное этой аннотацией, если эта аннотация есть в методе контроллера
	private Long id;
	@JsonView(Views.IdName.class) // в браузер будет передано поле, помеченное этой аннотацией, если эта аннотация есть в методе контроллера
	private String text;
	
	@Column(updatable = false) // значение поля не будет обновляться
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // отформатировать значение и преобразовать в String
	@JsonView(Views.FullMessage.class) // в браузер будет передано поле, помеченное этой аннотацией с учетом наследования!!!, если эта аннотация есть в методе контроллера
	private LocalDateTime creationDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

}
