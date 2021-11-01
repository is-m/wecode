package com.chinasoft.it.wecode.todo.service;

import com.chinasoft.it.wecode.todo.vo.MyTaskVO;

public interface ITodoService {

    Object createTodo(MyTaskVO myTaskVO);

    Object updateTodo(MyTaskVO myTaskVO);
}
