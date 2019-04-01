'use strict';

let os = require('os');
const getStdin = require('get-stdin');

getStdin().then(content => {
    console.log(content);
    console.log('----------------');
    console.log(os.platform(), os.arch(), os.cpus(), os.uptime(), os.userInfo());
});