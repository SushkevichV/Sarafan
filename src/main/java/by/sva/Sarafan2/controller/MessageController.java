package by.sva.Sarafan2.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import by.sva.Sarafan2.entity.Message;
import by.sva.Sarafan2.entity.User;
import by.sva.Sarafan2.entity.Views;
import by.sva.Sarafan2.exceptions.MessageNotFoundException;
import by.sva.Sarafan2.repository.MessageRepository;
import by.sva.Sarafan2.repository.UserDetailRepository;

@RestController
@RequestMapping("message")
public class MessageController {
	/* переход на работу с БД
	private int count = 4;
	
	// создание начального списка сообщений
	private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
		add(new HashMap<String, String>() {{put("id", "1"); put("text", "First message"); }});
		add(new HashMap<String, String>() {{put("id", "2"); put("text", "Second message"); }});
		add(new HashMap<String, String>() {{put("id", "3"); put("text", "Third message"); }});
	}};
	*/
	
	private final MessageRepository messageRepository;
	private final UserDetailRepository userDetailRepository;
	
	public MessageController (MessageRepository messageRepository, UserDetailRepository userDetailRepository) {
		this.messageRepository = messageRepository;
		this.userDetailRepository = userDetailRepository;
	}
	
	@GetMapping
	/*
	public List<Map<String,String>> list() {
		return messages;
	}
	*/
	@JsonView(Views.IdName.class) // аннотация отправляет в браузер только поля объекта, помеченные аннотацией
	public List<Message> list() {
		return messageRepository.findAll();
	}
	
	@GetMapping("{id}")
	// В Google Chrome клавиша F12 -> вкладка Console
	// fetch('/message/5').then(response => response.json().then(console.log))
	/*
	public Map<String, String> showMessage(@PathVariable String id){
		return getMessage(id);
	}
	*/
	@JsonView(Views.FullMessage.class) // аннотация отправляет в браузер только поля объекта, помеченные аннотацией
	public Message showMessage(@PathVariable("id") Message message){ // получить сразу объект из БД по его id
		return message;
	}
	
	/* этот метод не нужен после перехода на работу с БД
	public Map<String, String> getMessage(String id){
		return messages.stream()
				.filter(message -> message.get("id").equals(id))
				.findFirst()
				.orElseThrow(MessageNotFoundException::new);
	}
	*/
	
	/* В Google Chrome клавиша F12 -> вкладка Console
	 * fetch('/message', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({text: 'Fourth message'})}).then(result => console.log(result))
	 * fetch - ассинхронный запрос браузера
	 * '/message' - URL запроса
	 * 'Content-Type': 'application/json' - формат запроса - json (поумолчанию - text)
	 * JSON.stringiy({text: 'Fourth message')} - пользовательский ввод, переведенный в текстовый формат
	 * then(result => console.log(result)) - результат положить в console
	 */
	@PostMapping
	/*
	public Map<String, String> create(@RequestBody Map<String, String> message){
		message.put("id", String.valueOf(count++));
		messages.add(message);
		
		return message;
	}
	*/
	// посмотреть json-объект в Chrome: F12 -> Network -> Response
	public Message create(@RequestBody Message message){
		message.setCreationDate(LocalDateTime.now());
		return messageRepository.save(message);
	}
	
	// fetch('/message/4', {method: 'PUT', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({text: 'Fourth message (edited)'})}).then(result => console.log(result))
	@PutMapping("{id}")
	/*
	public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message){
		Map<String, String> messageFromDb = getMessage(id);
		messageFromDb.putAll(message);
		messageFromDb.put("id", id);
		return messageFromDb;
	}
	*/
	public Message update(
			@PathVariable("id") Message messageFromDb, // получить сразу объект из БД по его id
			@RequestBody Message message				//	Spring распарсит Json из @RequestBody и создаст объект Message
	){
		BeanUtils.copyProperties(message, messageFromDb, "id"); // скопировать поля из message в messageFromDb, кроме поля "id"
		return messageRepository.save(messageFromDb);
	}
	
	// fetch('/message/4', {method: 'DELETE'}).then(result => console.log(result))
	@DeleteMapping("{id}")
	/*
	public void delete(@PathVariable String id) {
		Map<String, String> message = getMessage(id);
		messages.remove(message);
	}
	*/
	public void delete(@PathVariable("id") Message message) {
		messageRepository.delete(message);
	}
	
	// получить параметры авторизованного пользователя
	@GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		Map<String, Object> attr = principal.getAttributes(); // получить все атрибуты пользователя
		User user = userDetailRepository.findById((String) principal.getName()).orElse(new User());
		
		user.setId((String) principal.getName());
		user.setName((String) attr.get("name"));
		user.setEmail((String) attr.get("email"));
		user.setGender((String) attr.get("gender"));
		user.setLocale((String) attr.get("locale"));
		user.setUserpic((String) attr.get("picture"));
		user.setLastVisit(LocalDateTime.now()); // записать текущую дату и время
		
		userDetailRepository.save(user);
        return Collections.singletonMap("name", principal.getAttribute("name")); // получить атрибут пользователя "name"
    }
	
	// показать параметры авторизованного пользователя
	@GetMapping("user/show")
	public Principal showUser(Principal principal) {
	    System.out.println("username : " + principal.getName());
	    return principal;
	}

}
