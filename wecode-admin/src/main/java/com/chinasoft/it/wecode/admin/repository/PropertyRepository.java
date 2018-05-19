package com.chinasoft.it.wecode.admin.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.admin.domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

	@Modifying
	@Query(value = "update Property set path= (?2 || substr(path,len(?1))) where path like (?1 || '%')", nativeQuery = false)
	int updateParentPath(String oldPath, String newPath);

	Property findByPath(String path);

	List<Property> findByPid(String pid);

	List<Property> findByPathLike(String parentPath);

	List<Property> findByValueTypeIn(String[] valueTypes, Sort sort);
}
