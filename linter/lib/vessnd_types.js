'use strict';

/*eslint-disable no-console*/

var fs              = require('fs');
var util            = require('util');
var yaml            = require('js-yaml');
var YAMLException   = require('js-yaml').YAMLException;
var Mark            = require('./js-yaml/mark');

var state = {};

function State(file, input) {
  this.file = file;
  this.input = input;
};

function Input(name, data, id, byClass, xpath, find, compare, select, submit, click, print, wait, clear, script, tag) {
  this.klass   = 'Input';
  this.name    = name;
  this.data    = data;
  this.id      = id;
  this.byClass = byClass;
  this.xpath   = xpath;
  this.find    = find;
  this.compare = compare;
  this.select  = select;
  this.submit  = submit;
  this.click   = click;
  this.print   = print;
  this.wait    = wait;
  this.clear   = clear;
  this.script  = script;
  this.tag     = tag;
}

function Element(input) {
  this.klass = 'Element';
  this.input = input;
}

function Change(element) {
  if(element) {
    if(!element instanceof Element) {
      throw new Error('invalid Element passed to Change');
    }
  }

  this.klass   = 'Change';
  this.element = element;
}

function Check(element) {
  if(element) {
    if(!element instanceof Element) {
      throw new Error('invalid Element passed to Change');
    }
  }

  this.klass   = 'Check';
  this.element = element;
}

function Page(browsers, url, wait, change, check, page) {
   this.klass    = 'Page';
   this.browsers = browsers;
   this.url      = url;
   this.wait     = wait;
   this.change   = change;
   this.check    = check;
   this.page     = page;
}

function Start(pages) {
  this.klass  = 'Start';
  this.page  = pages;
}

function Configuration(targetLanguage, terminateOnFirstFailure, dataBinding, loadTesting, numberOfThreads, browsers) {
  this.klass = 'Configuration';
  this.targetLanguage = targetLanguage;
  this.terminateOnFirstFailure = terminateOnFirstFailure;
  this.dataBinding = dataBinding;
  this.loadTesting = loadTesting;
  this.numberOfThreads = numberOfThreads;
  this.browsers = browsers;
}

function Root(version, configuration, start) {
    this.klass  = 'Root';
    this.version  = version;
    this.configuration = configuration;
    this.start = start;
}

var InputYamlType = new yaml.Type('input', {
    kind: 'mapping',

    resolve: function (data) {
        checkGrammar(["by-class", "byClass", "xpath", "data", "enter-data", "id", "name", "by-name",
                    "find", "find-text", "compare", "select", "submit",
                        "click", "print", "wait", "clear", "script", "tag", "by-tag"],
            data,
            "input");

    },

    construct: function (data) {
        this.resolve(data);
        data = data || {}; // in case of empty node
        return new Input(
            data["by-name"] || data.name || null,
            data["enter-data"] || data.data || null,
            data["by-id"] || data.id || null,
            data["by-class"] || data.byClass || null, data.xpath || null,
            data["find-text"] || data.find || null,
            data.compare || null, data.select || null, data.submit || false,
            data.click || false, data.print || false, data.wait || 0,
            data.clear || false, data.script || null,
            data["by-tag"] || data.tag || null);
    },
    instanceOf: Input
});

var ElementYamlType = new yaml.Type('element', {
    kind: 'mapping',
    resolve: function (data) {
        data.forEach(function(page) {
            checkGrammar(["input"],
                page,
                "element");
        });
    },
    construct: function (data) {
        this.resolve(data);
        data = data || {};
        var inputs = [];
        var keys = Object.keys(data);
        keys.every(function (input) {
            inputs.push(InputYamlType.construct(data[input].input));
        });
        return new Element(inputs);
    },
    instanceOf: Element
});

var ChangeYamlType = new yaml.Type('change', {
    kind: 'mapping',
    resolve: function (data) {
        checkGrammar(["element"],
            data,
            "change");
    },
    construct: function (data) {
        this.resolve(data);
        data = data || {};
        var element = null;
        if (data.element) {
            element = ElementYamlType.construct(data.element);
        }
        return new Change(element);
    },
    instanceOf: Change
});

var CheckYamlType = new yaml.Type('check', {
    kind: 'mapping',
    resolve: function (data) {
        checkGrammar(["element"],
            data,
            "check");
    },
    construct: function (data) {
        this.resolve(data);
        data = data || {};
        var element = null;
        if (data.element) {
            element = ElementYamlType.construct(data.element);
        }
        return new Check(element);
    },
    instanceOf: Check
});

var PageYamlType = new yaml.Type('page', {
    kind: 'mapping',
    resolve: function (data) {
        checkGrammar(["change", "check", "browsers", "url", "wait", "page"],
            data,
            "page");
    },
    construct: function (data) {
        this.resolve(data);
        data = data || {};
        var change = {};
        var check = {};
        var page = {};
        if (data.change) {
            change = ChangeYamlType.construct(data.change);
        }
        if (data.check) {
            check = CheckYamlType.construct(data.check);
        }
        if (data.page) {
            page = PageYamlType.construct(data.page);
        }
        return new Page(data.browsers || [], data.url || null,
            data.wait || 0, change, check,
            page);
    },
    instanceOf: Page
});

var StartYamlType = new yaml.Type('start', {
    kind: 'mapping',

    resolve: function (data) {
        data.forEach(function(page) {
            checkGrammar(["page"],
                page,
                "start");
        });
    },
    construct: function (data) {
        this.resolve(data);
        data = data || {};
        var pages = [];
        var keys = Object.keys(data);
        keys.every(function (page) {
            pages.push(PageYamlType.construct(data[page]));
        });
        return new Start(pages);
    },
    instanceOf: Start
});

var ConfigurationYamlType = new yaml.Type('configuration', {
    kind: 'mapping',
    resolve: function (data) {
        checkGrammar(["target-language", "targetLanguage", "terminate-on-first-failure", "terminateOnFirstFailure",
                "data-binding", "dataBinding", "load-testing", "loadTesting", "number-of-threads", "numberOfThreads", "browsers"],
            data,
            "configuration");
    },

    construct: function (data) {
        this.resolve(data);
        data = data || {};
        return new Configuration(data["target-language"] || data["targetLanguage"],
            data["terminate-on-first-failure"] || data.terminateOnFirstFailure || false,
            data["data-binding"] || data.dataBinding || null,
            data["load-testing"] || data.loadTesting || false,
            data["number-of-threads"] || data.numberOfThreads || 1, data.browsers || []);
    },
    instanceOf: Configuration
});

var RootYamlType = new yaml.Type('root', {
    kind: 'mapping',

    resolve: function (data) {
        checkGrammar(["version", "configuration", "start"],
            data,
            "VESSND Root");
    },

    construct: function (data, filename, input) {
        state = new State(filename, input);
        this.resolve(data);
        data = data || {};
        var configuration = null;
        var start = null;
        if (data.configuration) {
            configuration = ConfigurationYamlType.construct(data.configuration);
        }
        if (data.start) {
            start = StartYamlType.construct(data.start);
        }
        return new Root(data.version || null, configuration, start);
    },

    instanceOf: Root
});

var checkGrammar = function(map, data, parent_key) {
    var keys = Object.keys(data);
    keys.every(function(key){
        if(!map.includes(key)) {
            var lineNumbers = checkPositionAndLineNumbers(parent_key, key);
            console.log(lineNumbers);
            throw new YAMLException("The given element '" + key + "', is not expected under '" +
                parent_key + "' element. Expected keys are (" + map + ")" ,
                new Mark(state.filename, state.input, lineNumbers[1], lineNumbers[0], (lineNumbers[1] - lineNumbers[2])));
        } else {
            return true;
        }
    });
};

var checkPositionAndLineNumbers = function(parentElement, currentElement, countIfArray) {
    var parentChars         = 0,
        elementChars        = 0,
        currentLineNumber   = 0,
        arrayCount          = 0,
        lineStart           = 0,
        parentFound         = false;

    console.log("given elements: parent/current/count:" + parentElement + "=" + currentElement + "-" + countIfArray);
    if(countIfArray === undefined) {
        countIfArray = 0;
    }

    for (var i = 0; i < state.input.length; i++) {
        if(!parentFound) {
            state.input[i] === parentElement[parentChars] ? parentChars++ : parentChars = 0;
            lineStart++;
        }
        if (parentChars === parentElement.length) {
            parentFound = true;
        }
        if(parentFound) {
            state.input[i] === currentElement[elementChars] ? elementChars++ : elementChars = 0;
            lineStart++;
            if (elementChars === currentElement.length) {
                if(countIfArray === arrayCount) {
                    return [currentLineNumber, i, lineStart];
                } else {
                    arrayCount++;
                }
            }
        }
        if (state.input[i] === '\n') {
            currentLineNumber++;
            lineStart = 0;
        }
    }

    return [0, 0, 0];
};


module.exports.Input                  = Input;
module.exports.Element                = Element;
module.exports.Change                 = Change;
module.exports.Check                  = Check;
module.exports.Page                   = Page;
module.exports.Start                  = Start;
module.exports.Configuration          = Configuration;
module.exports.Root                   = Root;
module.exports.InputYamlType          = InputYamlType;
module.exports.ElementYamlType        = ElementYamlType;
module.exports.ChangeYamlType         = ChangeYamlType;
module.exports.CheckYamlType          = CheckYamlType;
module.exports.PageYamlType           = PageYamlType;
module.exports.StartYamlType          = StartYamlType;
module.exports.ConfigurationYamlType  = ConfigurationYamlType;
module.exports.RootYamlType           = RootYamlType;