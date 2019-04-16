'use strict';

var MongoClient = require('mongodb').MongoClient;

// Connect to the db
MongoClient.connect("mongodb://mongo:27017/scoring", function(err, db) {

    if(!err) {
        console.log("We are connected");
    }

    db.collection('test', function(err, collection) {});

    var doc1 = {'hello':'doc1'};
    var doc2 = {'hello':'doc2'};
    var lotsOfDocs = [{'hello':'doc3'}, {'hello':'doc4'}];

    db.collection.insert(doc1);

});