package com.example.mghelper

class Record {
    var id: Int? = null
    var date: Long? = null
    var recordType: Int? = null

    constructor()

    constructor(id: Int?, date: Long?, recordType: Int?){
        this.id = id
        this.date = date
        this.recordType = recordType
    }
}