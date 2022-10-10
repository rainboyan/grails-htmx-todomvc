<footer id="main-footer" class="footer ${totalNumberOfItems == 0 ? 'hidden' : ''}"
    hx-get="/todo/mainFooter?filter=${filter}"
    hx-swap="outerHTML"
    hx-trigger="itemAdded from:body, itemCompletionToggled from:body, itemDeleted from:body">
    <!-- This should be `0 items left` by default -->
    <span class="todo-count">
        <strong>${numberOfActiveItems}</strong> item left
    </span>
    <!-- Remove this if you don't implement routing -->
    <ul class="filters">
        <li>
            <a class="${filter == 'all' ? 'selected' : ''}" href="/todo?filter=all">All</a>
        </li>
        <li>
            <a class="${filter == 'active' ? 'selected' : ''}" href="/todo?filter=active">Active</a>
        </li>
        <li>
            <a class="${filter == 'completed' ? 'selected' : ''}" href="/todo?filter=completed">Completed</a>
        </li>
    </ul>
    <!-- Hidden if no completed items are left ↓ -->
    <button id="clear-button" class="clear-completed ${numberOfCompletedItems == 0 ? 'hidden' : ''}" 
        hx-post='/todo/deleteCompleted' _="on itemCompletionToggled remove .hidden">Clear completed</button>
</footer>