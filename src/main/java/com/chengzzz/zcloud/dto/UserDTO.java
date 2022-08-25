package com.chengzzz.zcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * USERDTO
 *
 * @author Yet
 * @date 2022/08/25 21:36
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    String username;
    String password;
}