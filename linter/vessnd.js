'use strict';

/*eslint-disable no-console*/

var fs = require('fs');
var path = require('path');
var util = require('util');
var vessnd = require('./lib/vessnd/vessnd_config');
var vessndTypes = require('./lib/vessnd/vessnd_types');

try {
    var filename = path.join(__dirname, 'lib/vessnd/vessnd_types.yml'),
        contents = fs.readFileSync(filename, 'utf8'),
        data = vessnd.load(contents);
        // data = vessnd.load(contents, {schema: vessndTypes.ROOT_SCHEMA});

    console.log(util.inspect(data, false, 10, true));
} catch (err) {
    console.log(err.stack || String(err));
}

