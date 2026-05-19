package com.hr_management.repository;

import com.hr_management.entity.NhanSu;
import com.hr_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NhanSuRepository extends JpaRepository<NhanSu, String> {
}
