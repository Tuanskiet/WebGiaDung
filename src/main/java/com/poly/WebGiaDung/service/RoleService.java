package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.RoleApp;
import com.poly.WebGiaDung.enums.RoleName;
import com.poly.WebGiaDung.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleApp create(RoleApp role){
        if(findRole(role.getName()) != null){
            throw new RuntimeException("Role already exist!");
        }
        return roleRepo.save(role);
    }

    public RoleApp findRole(RoleName roleName){
        return roleRepo.findByName(roleName);
    }
}
