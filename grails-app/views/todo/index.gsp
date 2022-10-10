<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Grails • TodoMVC</title>
</head>
<body>

    <section class="todoapp" hx-boost="true">
        <header class="header">
            <h1>todos</h1>
                <input id="new-todo-input" class="new-todo" placeholder="What needs to be done?" autofocus
                    autocomplete="false"
                    name="title"
                    hx-target="#todo-list"
                    hx-swap="innerHTML"
                    hx-post="/todo/create?filter=${filter}"
                    hx-trigger="keyup[key=='Enter']">
        </header>
        <section id="main-section" class="main ${totalNumberOfItems == 0 ? 'hidden' : ''}">
            <input id="toggle-all" class="toggle-all" type="checkbox" name="toggleAll" <g:if test="${numberOfActiveItems == 0}">checked</g:if>
                   hx-post="/todo/toggleAll?filter=${filter}"
                   hx-target="#todo-list"
                   hx-trigger="click">
            <label for="toggle-all">Mark all as complete</label>
            <ul id="todo-list" class="todo-list">
                <!-- These are here just to show the structure of the list items -->
                <!-- List items should get the class `editing` when editing and `completed` when marked as completed -->
                <g:render template="/todo/todoItem" model="[todoItems: todoItems]" />
            </ul>
        </section>
        <footer id="main-footer" class="footer ${totalNumberOfItems == 0 ? 'hidden' : ''}"
            hx-get="/todo/mainFooter?filter=${filter}"
            hx-swap="outerHTML"
            hx-trigger="load, itemAdded from:body, itemCompletionToggled from:body, itemDeleted from:body">
        </footer>
    </section>
    <footer class="info">
        <p>Double-click to edit a todo</p>
        <p>Created by <a href="http://sindresorhus.com">Sindre Sorhus</a></p>
        <p>Part of <a href="http://todomvc.com">TodoMVC</a></p>
    </footer>

</body>
</html>
