package org.grails.demo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class TodoItemSpec extends Specification implements DomainUnitTest<TodoItem> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
