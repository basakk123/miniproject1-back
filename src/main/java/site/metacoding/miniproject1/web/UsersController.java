package site.metacoding.miniproject1.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.miniproject1.domain.users.Users;
import site.metacoding.miniproject1.service.UsersService;
import site.metacoding.miniproject1.web.dto.request.users.UsersLoginReqDto;
import site.metacoding.miniproject1.web.dto.request.users.UsersPasswordReqDto;
import site.metacoding.miniproject1.web.dto.request.users.UsersUpdateReqDto;
import site.metacoding.miniproject1.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final UsersService usersService;
	private final HttpSession session;

	@GetMapping("/loginpage")
	public String loginpage(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				model.addAttribute(cookie.getName(), cookie.getValue());
			}
		}
		return "users/loginpage";
	}

	@PostMapping("/api/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody UsersLoginReqDto loginDto, HttpServletResponse response) {
		if (loginDto.isRemember()) {
			Cookie cookie = new Cookie("userId", loginDto.getUserId());
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("userId", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		Users principal = usersService.?????????(loginDto);
		if (principal == null) {
			return new CMRespDto<>(-1, "???????????????", null);
		}
		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "???????????????", null);
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		System.out.println("???????????? ????????????.");
		return "redirect:/loginpage";
	}

	@GetMapping("/users/as/{id}")
	public String accountsetting(@PathVariable Integer id, Model model) {
		Users usersPS = usersService.??????????????????(id);
		model.addAttribute("users", usersPS);
		return "users/accountsetting";
	}

	@PutMapping("/api/users/as/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UsersUpdateReqDto updateReqDto) {
		Users usersPS = usersService.??????????????????(id, updateReqDto);
		session.setAttribute("principal", usersPS); // ?????? ?????????
		return new CMRespDto<>(1, "?????????????????? ??????", null);
	}

	@GetMapping("/users/password/{id}")
	public String password(@PathVariable Integer id, Model model) {
		Users usersPS = usersService.??????????????????(id);
		model.addAttribute("users", usersPS);
		return "users/as_password";
	}

	@PutMapping("/api/users/password/{id}")
	public @ResponseBody CMRespDto<?> updateByPassword(@PathVariable Integer id,
			@RequestBody UsersPasswordReqDto passwordReqDto) {
		Users usersPS = usersService.??????????????????(id, passwordReqDto);
		session.setAttribute("principal", usersPS); // ?????? ?????????
		return new CMRespDto<>(1, "?????????????????? ??????", null);
	}

	@GetMapping("/users/delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {
		Users usersPS = usersService.??????????????????(id);
		model.addAttribute("users", usersPS);
		return "users/as_delete";
	}

	@DeleteMapping("/api/users/delete/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable Integer id, HttpServletResponse response) {
		usersService.????????????(id);
		Cookie cookie = new Cookie("userId", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		session.invalidate();
		return new CMRespDto<>(1, "??????????????????", null);
	
	@GetMapping("/users/profile")
	public String profile() {
		return "users/profile";
	}
	
	@GetMapping("/users/joinpage")
	public String joinpage() {
		return "users/joinpage";
	}

}
