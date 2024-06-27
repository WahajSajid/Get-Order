package com.application.foodapp

class Tables {
    var TableName: String? = null
    var isExpanded:Boolean = false
    constructor() {}

    constructor(name :String) {

        this.TableName = name
    }
}