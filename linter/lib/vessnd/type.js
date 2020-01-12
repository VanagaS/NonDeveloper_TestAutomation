'use strict';

var VESSNDException = require('./exception');

var TYPE_CONSTRUCTOR_OPTIONS = [
    'kind',
    'resolve',
    'construct',
    'instanceOf',
    'predicate',
    'represent',
    'defaultStyle',
    'styleAliases'
];

var VESSND_NODE_KINDS = [
    'scalar',
    'sequence',
    'mapping'
];

function compileStyleAliases(map) {
    var result = {};

    if (map !== null) {
        Object.keys(map).forEach(function (style) {
            map[style].forEach(function (alias) {
                result[String(alias)] = style;
            });
        });
    }

    return result;
}

function Type(tag, options) {
    options = options || {};

    Object.keys(options).forEach(function (name) {
        if (TYPE_CONSTRUCTOR_OPTIONS.indexOf(name) === -1) {
            throw new VESSNDException('Unknown option "' + name + '" is met in definition of "' + tag + '" VESSND type.');
        }
    });

    // TODO: Add tag format check.
    this.tag          = tag;
    this.kind         = options['kind']         || null;
    this.resolve      = options['resolve']      || function () { return true; };
    this.construct    = options['construct']    || function (data) { return data; };
    this.instanceOf   = options['instanceOf']   || null;
    this.predicate    = options['predicate']    || null;
    this.represent    = options['represent']    || null;
    this.defaultStyle = options['defaultStyle'] || null;
    this.styleAliases = compileStyleAliases(options['styleAliases'] || null);

    if (VESSND_NODE_KINDS.indexOf(this.kind) === -1) {
        throw new VESSNDException('Unknown kind "' + this.kind + '" is specified for "' + tag + '" VESSND type.');
    }
}

module.exports = Type;
