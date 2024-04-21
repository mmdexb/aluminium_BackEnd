package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
        private String cid;
        private String realname;
        private String password;
        private String level;
        private String email;
        private Integer OnlineTime;


}
