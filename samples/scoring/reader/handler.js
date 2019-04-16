let MongoClient = require('mongodb').MongoClient;
let url = 'mongodb://mongo';

MongoClient.connect(url, function (err, client) {

    if (!err) {
        console.log('we are connected!');
    }

    const db = client.db('scoring');

    db.collection('users').find({}).toArray(function (err, docs) {
        console.log("Found the following records");
        console.log(docs)
    });

    client.close();

});