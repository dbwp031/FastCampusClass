package com.example.boardproject.repository;

import com.example.boardproject.domain.UserAccount;
import com.example.boardproject.domain.projection.UserAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
