package org.grails.demo

class TodoItem {
    String title

    boolean completed

    static constraints = {
        title(blank: false, nullable: false, maxSize: 255)
    }
}
