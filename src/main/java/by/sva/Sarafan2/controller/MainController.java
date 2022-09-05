package by.sva.Sarafan2.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import by.sva.Sarafan2.entity.User;
import by.sva.Sarafan2.repository.MessageRepository;
import by.sva.Sarafan2.repository.UserDetailRepository;

//Не обязательный контроллер. Без него тоже работает

@Controller
@RequestMapping("/")
public class MainController {
	private final MessageRepository messageRepository;
	private final UserDetailRepository userDetailRepository;
	
	public MainController(MessageRepository messageRepository, UserDetailRepository userDetailRepository) {
		this.messageRepository = messageRepository;
		this.userDetailRepository = userDetailRepository;
	}
	
	@GetMapping
	public String main(Model model, @AuthenticationPrincipal OAuth2User principal) {
		HashMap<Object, Object> data = new HashMap<>();
		
		if(principal == null) { // если пользователь не авторизован
			data.put("profile", null);
			model.addAttribute("frontendData", data);
			return "index";
		}
		
		Map<String, Object> attr = principal.getAttributes(); // получить все атрибуты пользователя
		// получить пользователя из репозитория или создать нового
		User user = userDetailRepository.findById((String) principal.getName()).orElse(new User());
		user.setId((String) principal.getName()); // получить уникальное имя пользователя
		user.setName((String) attr.get("name"));
		user.setEmail((String) attr.get("email"));
		user.setGender((String) attr.get("gender"));
		user.setLocale((String) attr.get("locale"));
		user.setUserpic((String) attr.get("picture"));
		user.setLastVisit(LocalDateTime.now()); // записать текущую дату и время
		
		userDetailRepository.save(user);
		
		data.put("profile", user); // передать пользователя
		data.put("messages", messageRepository.findAll()); // передать сообщения
		model.addAttribute("frontendData", data);
		return "index";
	}

}
