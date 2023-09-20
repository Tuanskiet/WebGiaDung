package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.RoleApp;
import com.poly.WebGiaDung.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleApp, Integer> {
    RoleApp findByName(RoleName roleName);
}
