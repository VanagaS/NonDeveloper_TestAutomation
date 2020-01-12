// http://www.yaml.org/spec/1.2/spec.html#id2804923

'use strict';

var Schema = require('../schema');

module.exports = new Schema({
  include: [
    require('./json')
  ]
});
