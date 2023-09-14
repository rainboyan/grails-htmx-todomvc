package org.rainboyan.demo

import grails.converters.JSON

class TodoController {

    def todoItemService

    def index() {
        String filter = params.filter ?: 'all'
        Long totalNumberOfItems = todoItemService.count()
        Long numberOfActiveItems = todoItemService.countByCompleted(false)
        Long numberOfCompletedItems = totalNumberOfItems - numberOfActiveItems
        List<TodoItem> todoItems = getTodoItems(filter)

        def model = [
                filter: filter,
                todoItems: todoItems,
                totalNumberOfItems: totalNumberOfItems,
                numberOfActiveItems: numberOfActiveItems,
                numberOfCompletedItems: numberOfCompletedItems
        ]

        withFormat {
            html {
                model
            }
            json {
                render model as JSON
            }
        }
    }

    def list() {
        List<TodoItem> todoItems = todoItemService.list()
        def model = [todoItems: todoItems]

        withFormat {
            htmx {
                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def create() {
        String title = params.title

        TodoItem todoItem = new TodoItem(title: title)

        todoItemService.save(todoItem)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemAdded'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def update(Long id) {
        TodoItem todoItem = todoItemService.get(id)
        todoItem.title = params.title

        todoItemService.save(todoItem)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemUpdated'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def delete(Long id) {
        todoItemService.delete(id)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemDeleted'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def deleteCompleted() {
        List itemsCompleted = getTodoItems('completed')
        itemsCompleted.each {
            todoItemService.delete(it.id)
        }

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemDeleted'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def toggle(Long id) {
        TodoItem item = todoItemService.get(id)
        item.completed = !item.completed

        todoItemService.save(item)

        String filter = params.filter ?: 'all'
        List<TodoItem> todoItems = getTodoItems(filter)
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemCompletionToggled'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
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
        def model = [filter: filter, todoItems: todoItems]

        withFormat {
            htmx {
                response.htmx.trigger = 'itemCompletionToggled'

                render(contentType: 'text/html', template: "/todo/todoItem", model: model)
            }
            json {
                render model as JSON
            }
        }
    }

    def mainFooter() {
        String filter = params.filter ?: 'all'
        Long totalNumberOfItems = todoItemService.count()
        Long numberOfActiveItems = todoItemService.countByCompleted(false)
        Long numberOfCompletedItems = totalNumberOfItems - numberOfActiveItems

        def model = [
                filter: filter,
                totalNumberOfItems: totalNumberOfItems,
                numberOfActiveItems: numberOfActiveItems,
                numberOfCompletedItems: numberOfCompletedItems
        ]

        withFormat {
            htmx {
                render(contentType: 'text/html', template: "/todo/mainFooter", model: model)
            }
            json {
                render model as JSON
            }
        }
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
