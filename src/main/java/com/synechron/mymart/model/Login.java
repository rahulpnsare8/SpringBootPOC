package com.synechron.mymart.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @NotNull
    private String userName;
    
    @NotNull
    private String password;
    
    private int userId;
	
}
