'use strict';

/*eslint-disable max-len,no-use-before-define*/

var fs   = require('fs');
var path = require('path');
var util = require('util');
var yaml = require('js-yaml');

function load(contents, options, schema) {

    var data = yaml.safeLoadAll(contents, options, schema);
    Type.RootYamlType.construct(data, schema.filename, contents);

    // do not execute the following if file is required
    if (require.main === module) {
        try {
            var filename = path.join(__dirname, './vessnd_types.yml'),
                contents = fs.readFileSync(filename, 'utf8'),
                data = yaml.load(contents);

            var data = yaml.safeLoadAll(contents, options, schema);
            var vessndObject = Type.RootYamlType.construct(data, filename, contents);
            console.log(JSON.stringify(vessndObject, undefined, 4));
            console.log(util.inspect(data, false, 10, true));
        } catch (err) {
            console.log(err.stack || String(err));
        }
    }
}

module.exports.load = load;