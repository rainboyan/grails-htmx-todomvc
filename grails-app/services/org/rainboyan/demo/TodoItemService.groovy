package org.rainboyan.demo

import grails.gorm.services.Query
import grails.gorm.services.Service

@Service(TodoItem)
interface TodoItemService {

    TodoItem get(Serializable id)

    List<TodoItem> list(Map args)

    List<TodoItem> findAllByCompleted(boolean completed)

    Long count()

    Long countByCompleted(boolean completed)

    void delete(Serializable id)

    TodoItem save(TodoItem todoItem)
}