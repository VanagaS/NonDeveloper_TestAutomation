'use strict';

/*eslint-disable max-len,no-use-before-define*/

var fs   = require('fs');
var path = require('path');
var util = require('util');
var yaml = require('js-yaml');

// var vessnd = require('./../vessnd_types');

function load() {
    try {
        var filename = path.join(__dirname, './vessnd/vessnd_types.yml'),
            contents = fs.readFileSync(filename, 'utf8'),
            data = yaml.load(contents);

        console.log(util.inspect(data, false, 10, true));
    } catch (err) {
        console.log(err.stack || String(err));
    }
}

module.exports.load = load;