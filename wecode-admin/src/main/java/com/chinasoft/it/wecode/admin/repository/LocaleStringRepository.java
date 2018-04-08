package com.chinasoft.it.wecode.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinasoft.it.wecode.admin.domain.LocaleString;

public interface LocaleStringRepository extends JpaRepository<LocaleString, String> {

}
