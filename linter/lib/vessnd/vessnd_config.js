'use strict';


var parser = require('./parser');


function deprecated(name) {
    return function () {
        throw new Error('Function ' + name + ' is deprecated and cannot be used.');
    };
}


module.exports.Type                = require('./type');
module.exports.Schema              = require('./schema');
module.exports.FAILSAFE_SCHEMA     = require('./schema/failsafe');
module.exports.JSON_SCHEMA         = require('./schema/json');
module.exports.CORE_SCHEMA         = require('./schema/core');
module.exports.DEFAULT_SAFE_SCHEMA = require('./schema/default_safe');
module.exports.DEFAULT_FULL_SCHEMA = require('./schema/default_full');
module.exports.load                = parser.load;
module.exports.VESSNDException     = require('./exception');
