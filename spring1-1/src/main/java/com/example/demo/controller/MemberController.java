package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.entity.RegistrationRequest;
import com.example.demo.entity.Response;
import com.example.demo.service.MemberService;
import com.example.demo.utils.BCrypt;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/membercontroller")
public class MemberController {
	
	@Autowired
	private Response response;
		
	@PostMapping("/members")
	public Response test2(@RequestBody List<Member> members) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource[] params = 
			new MapSqlParameterSource[members.size()];
		for (int i = 0; i < members.size(); i++) {
			Member member = members.get(i);
			params[i] = new MapSqlParameterSource();
			params[i].addValue("account", member.getAccount());
			params[i].addValue("password", BCrypt.hashpw(
					member.getPassword(), BCrypt.gensalt()));
			params[i].addValue("cname", member.getCname());				
		}
		
		List<Map<String,Object>> ids = keyHolder.getKeyList();
		
		for (Map<String,Object> id : ids) {
			System.out.println(id.get("GENERATED_KEY"));
		}
		
		return response;
	}
	
	@Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
        try {
            memberService.setMember(request);
            return ResponseEntity.ok("已寄出驗證信，請至信箱點擊驗證連結完成註冊");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("系統錯誤");
        }
    }
}