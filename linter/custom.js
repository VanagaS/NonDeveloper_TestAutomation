'use strict';

/*eslint-disable no-console*/

var fs   = require('fs');
var path = require('path');
var util = require('util');
var yaml = require('js-yaml');
var custom = true;

if(custom) {
  var custom = require('./lib/custom_types');
  try {
    var filename = path.join(__dirname, 'lib/custom_types.yml'),
        contents = fs.readFileSync(filename, 'utf8'),
        data     = yaml.load(contents, { schema: custom.SPACE_SCHEMA });

    console.log(util.inspect(data, false, 10, true));
  } catch (err) {
    console.log(err.stack || String(err));
  }
} else {
  var vessnd = require('./lib/vessnd_types');
  try {
    var filename = path.join(__dirname, 'lib/vessnd_types.yml'),
        contents = fs.readFileSync(filename, 'utf8'),
        data     = yaml.load(contents, { schema: vessnd.ROOT_SCHEMA });

    console.log(util.inspect(data, false, 10, true));
  } catch (err) {
    console.log(err.stack || String(err));
  }
}
