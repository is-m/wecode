package com.chinasoft.it.wecode.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.chinasoft.it.wecode.common.exception.NoImplementedException;

public class WebUtils {

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Pageable getPageable(HttpServletRequest request) {
		switch (PageConstants.PARAM_HTTP_TYPE_DEFAULT) {
		case PageConstants.PARAM_HTTP_TYPE_QUERY:
			String page = request.getParameter(PageConstants.PARAM_PAGE);
			int curPage = NumberUtils.tryParse(page, 0);
			String size = request.getParameter(PageConstants.PARAM_SIZE);
			int pageSize = NumberUtils.tryParse(size, PageConstants.DEFAULT_SIZE);

			if (PageConstants.START_PAGE == 1 && curPage > 0) {
				curPage--;
			}

			Sort sort = getSort(request);
			return new PageRequest(curPage, pageSize, sort);
		default:
			throw new NoImplementedException();
		}
	}

	private static Sort getSort(HttpServletRequest request) {
		String[] sorts = request.getParameterValues(SortConstant.PARAM_SORT);
		if (sorts != null && sorts.length > 0) {
			List<Order> orders = new ArrayList<>();
			for (String sortString : sorts) {
				String[] splited = sortString.split(SortConstant.PARAM_SPLITTER);
				if (splited.length > 2)
					throw new IllegalArgumentException("request sort condition parse faild,sortString=" + sortString);
				String direction = splited.length > 1 ? splited[1] : SortConstant.DIRECTION_DEFAULT;
				orders.add(new Order(Direction.fromString(direction), splited[0]));
			}
			return new Sort(orders);
		}
		return null;
	}
}
