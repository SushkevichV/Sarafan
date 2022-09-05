package by.sva.Sarafan2.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.ToString;

@Entity
@Table(name = "usr")
@ToString(of = {"id", "name"})
public class User {
	@Id
	// GeneratedValue не писать. Id будут приходить от Google в формате String!
	private String id;
	private String name;
	private String userpic;
	private String email;
	private String gender; // у Google 4 варианта заполнения этого поля
	private String locale;
	private LocalDateTime lastVisit;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public LocalDateTime getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(LocalDateTime lastVisit) {
		this.lastVisit = lastVisit;
	}
	
	
}
