package org.rainboyan.demo

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                initSampleData()
            }
        }
    }
    def destroy = {
    }

    @Transactional
    protected void initSampleData() {
        new TodoItem(title: "This is a todo item#1", completed: true).save()
        new TodoItem(title: "This is a todo item#2").save()
        new TodoItem(title: "This is a todo item#3").save()
    }

}
