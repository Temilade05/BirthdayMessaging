package com.demtem.birthday_messaging.models;

import com.demtem.birthday_messaging.models.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {

    private RoleName name;
}
