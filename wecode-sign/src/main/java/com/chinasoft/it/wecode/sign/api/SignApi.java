package com.chinasoft.it.wecode.sign.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.chinasoft.it.wecode.common.util.DESUtil;
import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;
import com.chinasoft.it.wecode.sign.dto.SignDto;
import com.chinasoft.it.wecode.sign.dto.SignResultDto;
import com.chinasoft.it.wecode.sign.service.impl.SignService;
import com.chinasoft.it.wecode.sign.util.SignConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Sign Api
 * 
 * @author Administrator
 *
 */
@Api("Sign Api")
@RestController
@RequestMapping("/sn/sign")
public class SignApi {

	@Autowired
	private SignService service;

	@Deprecated
	@ApiOperation(value = "签到(临时)", notes = "临时签到，测试用，后续前台使用DES加密后，该接口将会被屏蔽")
	@PostMapping("/temp")
	public SignResultDto sign(SignDto dto) {
		String encryption = null;
		String waitFor = dto.getUserId() + "," + dto.getPoint();

		try {
			encryption = DESUtil.encryption(waitFor, SignConstant.SIGN_DES_KEY);
		} catch (Exception e) {
			throw new IllegalArgumentException("加密失败", e);
		}

		if (StringUtils.isEmpty(encryption)) {
			throw new IllegalArgumentException("加密失败");
		}
		return service.sign(encryption);
	}

	@ApiOperation(value = "安全签到", notes = "安全签到")
	@PostMapping
	public SignResultDto secretSign(String secretString) {
		return service.sign(secretString);
	}

	@ApiOperation(value = "我的打卡记录", notes = "分页查询我的打卡记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"), })
	@GetMapping("/my/{userId}")
	public Page<SignResultDto> findByPage(HttpServletRequest req, @PathVariable("userId") String userId) {
		return service.findMySignPagedList(WebUtils.getPageable(req), userId);
	}
}
