package com.chinasoft.it.wecode.admin.repository;

import com.chinasoft.it.wecode.admin.domain.RichText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RichTextRepository extends JpaRepository<RichText, String>, JpaSpecificationExecutor<RichText> {


}
