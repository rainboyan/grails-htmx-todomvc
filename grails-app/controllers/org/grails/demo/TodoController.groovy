package org.grails.demo

class TodoController {

    def todoItemService

    def index() {
        String filter = params.filter ?: 'all'
        Long totalNumberOfItems = todoItemService.count()
        Long numberOfActiveItems = todoItemService.countByCompleted(false)
        Long numberOfCompletedItems = totalNumberOfItems - numberOfActiveItems
        List<TodoItem> todoItems = getTodoItems(filter)

        [filter: filter, todoItems: todoItems, totalNumberOfItems: totalNumberOfItems, numberOfActiveItems: numberOfActiveItems, numberOfCompletedItems: numberOfCompletedItems]
    }

    def list() {
        List<TodoItem> todoItems = todoItemService.list()

        render(contentType: 'text/html', template: "/todo/todoItem", model: [todoItems: todoItems])
    }

    def create() {
        String title = params.title

        TodoItem todoItem = new TodoItem(title: title)

        todoItemService.save(todoItem)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)

        response.setHeader("HX-Trigger", "itemAdded")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def update(Long id) {
        TodoItem todoItem = todoItemService.get(id)
        todoItem.title = params.title

        todoItemService.save(todoItem)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)

        response.setHeader("HX-Trigger", "itemUpdated")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def delete(Long id) {
        todoItemService.delete(id)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        
        response.setHeader("HX-Trigger", "itemDeleted")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def deleteCompleted() {
        List itemsCompleted = getTodoItems('completed')
        itemsCompleted.each {
            todoItemService.delete(it.id)
        }

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        
        response.setHeader("HX-Trigger", "itemDeleted")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def toggle(Long id) {
        TodoItem item = todoItemService.get(id)
        item.completed = !item.completed

        todoItemService.save(item)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)

        response.setHeader("HX-Trigger", "itemCompletionToggled")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def toggleAll() {
        String filter = params.filter ?: 'all'

        boolean checked = params.toggleAll ?: false
        List todoItems = getTodoItems(filter)
        todoItems.each { item ->
            item.completed = checked
            todoItemService.save(item)
        }

        todoItems = getTodoItems(filter)

        response.setHeader("HX-Trigger", "itemCompletionToggled")

        render(contentType: 'text/html', template: "/todo/todoItem", model: [filter: filter, todoItems: todoItems])
    }

    def mainFooter() {
        String filter = params.filter ?: 'all'
        Long totalNumberOfItems = todoItemService.count()
        Long numberOfActiveItems = todoItemService.countByCompleted(false)
        Long numberOfCompletedItems = totalNumberOfItems - numberOfActiveItems

        def model = [filter: filter, totalNumberOfItems: totalNumberOfItems, numberOfActiveItems: numberOfActiveItems, numberOfCompletedItems: numberOfCompletedItems]

        render(contentType: 'text/html', template: "/todo/mainFooter", model: model)
    }

    private List<TodoItem> getTodoItems(String filter) {
        if (filter == 'all') {
            return todoItemService.list()
        } else if (filter == 'active') {
            return todoItemService.findAllByCompleted(false)
        } else if (filter == 'completed') {
            return todoItemService.findAllByCompleted(true)
        }
        return []
    }
}
