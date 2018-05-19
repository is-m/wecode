package com.chinasoft.it.wecode.fw.spring.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinasoft.it.wecode.base.BaseEntity;
import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 基础 Api
 * 
 * @author Administrator
 *
 * @param <E>
 *            实体对象
 * @param <D>
 *            实体对应的标准DTO
 * @param <R>
 *            返回结果DTO
 * @param <Q>
 *            查询DTO
 */
public class CrudApi<E extends BaseEntity, D extends BaseDto, R extends BaseDto, Q extends BaseDto>
		extends CudApi<E, D, R> {

	/**
	 * 查询分页
	 * 
	 * @param request
	 * @param condition
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "查询（分页）", notes = "查询（分页）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"), })
	public Page<R> findPagedData(HttpServletRequest request, Q condition) {
		return service.findPagedList(WebUtils.getPageable(request), condition);
	}

	/**
	 * 按ID查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "按ID查询", notes = "按ID查询")
	public R findOne(@PathVariable("id") String id) {
		return service.findOne(id);
	}

	/**
	 * 按
	 * 
	 * @param ids
	 * @return
	 */
	@GetMapping("/s")
	public Map<String, R> findMapIn(@RequestParam("ids") String ids) {
		String[] idArray = ids.split(",");
		Map<String, R> result = new HashMap<String, R>();
		for (String id : idArray) {
			if (!result.containsKey(id)) {
				result.put(id, service.findOne(id));
			}
		}
		return result;
	}
}
