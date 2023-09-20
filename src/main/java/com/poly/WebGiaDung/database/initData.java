package com.poly.WebGiaDung.database;

import com.poly.WebGiaDung.entity.RoleApp;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.enums.RoleName;
import com.poly.WebGiaDung.service.MyUserService;
import com.poly.WebGiaDung.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class initData implements CommandLineRunner {
    private final RoleService roleService;
    private final MyUserService userService;
    @Override
    public void run(String... args) throws Exception {
        //        init role
        RoleApp roleApp1 = new RoleApp(null, RoleName.ROLE_ADMIN);
        RoleApp roleApp2 = new RoleApp(null, RoleName.ROLE_USER);
        roleService.create(roleApp1);
        roleService.create(roleApp2);

        //        init user
        Set<RoleApp> roles1 = new HashSet<>();
        roles1.add(roleApp1);
        UserApp userApp1 = new UserApp(null,"tk", "123", "Tuan Kiet", true, roles1);

        Set<RoleApp> roles2 = new HashSet<>();
        roles2.add(roleApp2);
        UserApp userApp2 = new UserApp(null,"stellaprimo99@gmail.com", "123", "Duc Cong", true, roles2);

        userService.create(userApp1);
        userService.create(userApp2);
    }
}
