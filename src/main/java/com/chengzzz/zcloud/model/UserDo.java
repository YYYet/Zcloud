package com.chengzzz.zcloud.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDo implements Serializable {
    String username;
    String password;
}