'use strict';

/*eslint-disable no-console*/

var fs   = require('fs');
var path = require('path');
var util = require('util');
var vessnd = require('./vessnd_config');

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

function Configuration(targetLanguage, terminateOnFirstFailure, dataBinding, loadTesting, numberOfThreads, browsers, start) {
  this.klass = 'Configuration';
  this.targetLanguage = targetLanguage;
  this.terminateOnFirstFailure = terminateOnFirstFailure;
  this.dataBinding = dataBinding;
  this.loadTesting = loadTesting;
  this.numberOfThreads = numberOfThreads;
  this.browsers = browsers;
  this.start = start;
}

function Root(version, configuration, start) {
    this.klass  = 'Root';
    this.version  = version;
    this.configuration = configuration;
    this.start = start;
}

var InputYamlType = new vessnd.Type('input', {
  kind: 'mapping',
  resolve: function(data) {
    console.log("Resolve invoked during construction!");
    return true;
  },
  /* resolve: function(data) {
    function where(collection, source) {
      var keys = Object.keys(source);
      return collection.filter(function (item) {
        return keys.every(function (key) {
          return source[key] == item[key];
        });
      });
    }
  },*/

  construct: function (data) {
    data = data || {}; // in case of empty node
    return new Input(data.name || null, data.data || null, data.id || null,
                      data.byClass || null, data.xpath || null, data.find || null,
                      data.compare || null, data.select || null, data.submit || false,
                      data.click || false, data.print || false, data.wait || 0,
                      data.clear || false, data.script || null, data.tag || null);
  },
  instanceOf: Input
});

var ElementYamlType = new vessnd.Type('element', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    var input = null;
    if(data.input) {
        input = InputYamlType.construct(data.input);
    }
    return new Element(input);
  },
  instanceOf: Element
});

var ChangeYamlType = new vessnd.Type('change', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    var element = null;
    if(data.element) {
        element = ElementYamlType.construct(data.element);
    }
    return new Change(element);
  },
  instanceOf: Change
});

var CheckYamlType = new vessnd.Type('check', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    var element = null;
    if(data.element) {
      element = ElementYamlType.construct(data.element);
    }
    return new Check(element);
  },
  instanceOf: Check
});

var PageYamlType = new vessnd.Type('page', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    var change = {};
    var check  = {};
    var page    = {};
    if(data.change) {
      change = ChangeYamlType.construct(data.change);
    }
    if(data.check) {
      check = CheckYamlType.construct(data.check);
    }
    if(data.page) {
      page = PageYamlType.construct(data.page);
    }
    return new Page(data.browsers || [], data.url || null,
                    data.wait || 0, change, check,
                    page);
  },
  instanceOf: Page
});

var StartYamlType = new vessnd.Type('start', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    var pages = [];
    var keys = Object.keys(data);
    keys.every(function(page){
      pages.push(PageYamlType.construct(data[page]));
    });
    return new Start(pages);
  },
  instanceOf: Start
});

var ConfigurationYamlType = new vessnd.Type('configuration', {
  kind: 'mapping',
  construct: function (data) {
    data = data || {};
    return new Configuration(data.targetLanguage || null, data.terminateOnFirstFailure || true,
                             data.dataBinding || null, data.loadTesting || false,
                             data.numberOfThreads || 1, data.browsers || []);
  },
  instanceOf: Configuration
});

var RootYamlType = new vessnd.Type('root', {
  kind: 'mapping',
  construct: function(data) {
    data = data || {};
    var configuration = null;
    var start = null;
    if(data.configuration) {
      configuration = ConfigurationYamlType.construct(data.configuration);
    }
    if(data.start) {
      start = StartYamlType.construct(data.start);
    }
    return new Root(data.version || null, configuration, start);
  },

  instanceOf: Root
});

// var ROOT_SCHEMA = vessnd.Schema.create([ InputYamlType, ElementYamlType, ChangeYamlType,
//                                       CheckYamlType, PageYamlType, StartYamlType, ConfigurationYamlType ]);

// var ROOT_SCHEMA = yaml.Schema.create([StartYamlType, PageYamlType, ConfigurationYamlType]);

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
// module.exports.ROOT_SCHEMA            = ROOT_SCHEMA;
