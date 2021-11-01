package com.chinasoft.it.wecode.todo.service;

public interface ITodoQueryService {

    /**
     * 我的代办
     *
     * @return
     */
    Object getMyTodo();

    /**
     * 我的申请
     * @return
     */
    Object getMyApplication();

    /**
     * 我参与的
     * @return
     */
    Object getMyParticipate();
}
