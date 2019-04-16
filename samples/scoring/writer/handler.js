let MongoClient = require('mongodb').MongoClient;
let url = 'mongodb://mongo';
let getStdin = require("get-stdin");


getStdin().then(content => {
    console.log('received content');
    console.log(content);

    let request = JSON.parse(content);

    MongoClient.connect(url, function (err, client) {

        if (!err) {
            console.log('we are connected!');
        }

        const db = client.db('scoring');

        db.collection('users').insertOne(request);

        client.close();

    })
});