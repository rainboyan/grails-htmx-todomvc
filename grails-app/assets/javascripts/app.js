// htmx.logAll();

htmx.on('#new-todo-input', 'htmx:afterRequest', function (event) { 
    event.detail.elt.value = ''; 
});

htmx.on('htmx:afterSwap', function (event) {
    let toggleAll = document.getElementById('toggle-all');
    let totalItems = document.querySelectorAll('#todo-list li');
    let checkedItems = document.querySelectorAll('#todo-list li input[type=checkbox][checked]');
    let mainSection = document.getElementById('main-section');

    if (totalItems.length > 0) {
        mainSection.classList.remove('hidden');
        if (totalItems.length == checkedItems.length) {
            toggleAll.checked = true;
        } else {
            toggleAll.checked = false;
        }
    } else {
        mainSection.classList.add('hidden');
    }
});
