/**
 * Created by Sanjeev on 05-03-2017.
 */

var types = require('./lib/vessnd/vessnd_types');
var result = { version: '0.1',
    configuration:
        { 'target-language': 'java',
            'terminate-on-first-failure': false,
            'data-binding': 'path-to-file',
            'load-testing': false,
            'number-of-threads': 1,
            browsers: [ 'htmlunit', 'firefox', 'ie', 'chrome' ] },
    start:
        [ { page:
            { browsers: [ 'firefox', 'chrome' ],
                url: 'http://google.com',
                wait: 10,
                change:
                    { element:
                        [ { input: { 'by-name': 'q', 'enter-data': 'Cheese!', wait: 10000 } },
                            { input: { 'by-name': 'l', 'enter-data': 'Say!' } },
                            { input: { 'by-tag': '@//sgra/srta/', submit: true } } ] },
                check: { element: [ { input: { 'find-text': '@title', compare: 'cheese!', print: true } } ] },
                page: { url: 'github.io' } } },
            { page: { url: 'http://gmail.com' } } ] };

var obj = types.RootYamlType.construct(result);
//obj.name = obj.klass;
//console.log(obj.name);
console.log(JSON.stringify(obj, undefined, 4));