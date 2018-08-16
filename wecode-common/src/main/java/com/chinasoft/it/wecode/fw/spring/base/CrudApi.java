package com.chinasoft.it.wecode.fw.spring.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
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
	 * 查询全部
	 * 
	 * @param request
	 * @param condition
	 * @return
	 */
	@GetMapping("/s")
	@ApiOperation(value = "查询全部", notes = "查询全部(如果总记录树大于1000条将会出现异常，出现异常时建议使用分页查询)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"), })
	public List<R> findAll(HttpServletRequest request, Q condition,
			@RequestParam(name = "maxReturn", required = false, defaultValue = "false") Boolean maxReturn) {
		Page<R> pageResult = service.findPagedList(new PageRequest(0, maxReturn ? Integer.MAX_VALUE : 1000), condition);
		// 下面如果出现异常解决问题有两种方法，1.重新设置最大允许值 1000=?，2.设置maxReturn=true
		Assert.isTrue(!maxReturn && pageResult.getTotalPages() <= 1,
				"find all is not max return flag but return out of allow size 1000 to "
						+ pageResult.getTotalElements());
		return pageResult.getContent();
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
	@GetMapping("/map")
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
