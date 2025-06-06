package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    
	@NotBlank(message = "帳號不能為空")
    @Email(message = "請輸入正確的 Email 格式")
	private String account;
    
    @NotBlank(message = "密碼不能為空")
    @Size(min = 6, max = 20, message = "密碼長度需在 6～20 字元之間")
    private String password;
    
    @NotBlank(message = "姓名不能為空")
    private String cname;

    public RegistrationRequest() {}

    public RegistrationRequest(String account, String password, String cname) {
        this.account = account;
        this.password = password;
        this.cname = cname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswd(String password) {
        this.password = password;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
