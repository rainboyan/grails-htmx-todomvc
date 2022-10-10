<g:each var="item" in="${todoItems}">
    <li id="list-item-${item.id}" class="${item.completed ? 'completed' : ''}">
        <div class="view">
            <input class="toggle" type="checkbox" ${item.completed ? 'checked' : ''}
                   hx-put="/todo/toggle/${item.id}?filter=${filter}" 
                   hx-target="#todo-list"
                   hx-trigger="click"
                   hx-swap="innerHTML">
            <label _="on dblclick toggle .editing on the closest parent <li/> 
                      then send focused to the <input.edit/> in <li.editing/>">
                ${item.title}
            </label>
            <button class="destroy" 
                    hx-delete="/todo/delete/${item.id}?filter=${filter}" 
                    hx-target="#todo-list" 
                    hx-trigger="click" 
                    hx-swap="innerHTML"></button>
        </div>
        <g:if test="${!item.completed}">
        <input class="edit" type="text" name="title" value="${item.title}" 
               hx-post="/todo/update/${item.id}?filter=${filter}"
               hx-target="#todo-list"
               hx-trigger="focusout changed"
               hx-swap="innerHTML"
               _="on blur toggle .editing on the closest parent <li/>
                  on focused focus() on me
                  on keyup[key is 'Escape' or key is 'Enter'] blur() on me">
        </g:if>
    </li>
</g:each>