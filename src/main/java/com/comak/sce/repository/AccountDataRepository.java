package com.comak.sce.repository;

import com.comak.sce.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDataRepository extends JpaRepository<Account, Long> {
}
