var str = {
    "tags": [
        "version",
        "configuration",
        "start",
        "page",
        "element",
        "input",
        "change",
        "check"
    ],
    "properties": {
        "browsers:": {
            "values": [
                "- firefox",
                "- chrome",
                "- htmlunit"
            ],
            "description": "Browsers that should be used to run this script on."
        },
        "submit:": {
            "values": [
                "true",
                "false"
            ],
            "description": "Submit the active form."
        },
        "clear:": {
            "values": [
                "true",
                "false"
            ],
            "description": "Whether to clear values from the selected element."
        },
        "print:": {
            "values": [
                "true",
                "false"
            ],
            "description": "Whether to print the findings."
        },
        "load-testing:": {
            "values": [
                "true",
                "false"
            ],
            "description": "Whether to perform load testing."
        },
        "terminate-on-first-failure:": {
            "values": [
                "true",
                "false"
            ],
            "description": "Whether to terminate the scripts upon very first failure."
        },
        "version:": {
            "values": [],
            "description": "Version of VESSND"
        },
        "configuration:": {
            "values": [],
            "description": "Global configuration settings."
        },
        "start:": {
            "values": [],
            "description": "Initiate the commands."
        },
        "- page:": {
            "values": [],
            "description": "Page on which the commands must be run."
        },
        "url:": {
            "values": [],
            "description": "URL of the page to be fetched."
        },
        "wait:": {
            "values": [],
            "description": "Number of seconds to wait before termination or proceed with next command"
        },
        "change:": {
            "values": [],
            "description": "The values of text/elements to be changed."
        },
        "check:": {
            "values": [],
            "description": "The values of text/elements to be cross checked."
        },
        "- input:": {
            "values": [],
            "description": "The values of form elements that should be changed."
        },
        "name:": {
            "values": [],
            "description": "Select a form element by its name."
        },
        "by-name:": {
            "values": [],
            "description": "Select a form element by its name."
        },
        "enter:": {
            "values": [],
            "description": "The value to enter in the selected element."
        },
        "enter-data:": {
            "values": [],
            "description": "The value to enter in the selected element."
        },
        "tag:": {
            "values": [],
            "description": "Select a form element by its tag."
        },
        "xpath:": {
            "values": [],
            "description": "Select a form element by its xpath."
        },
        "css:": {
            "values": [],
            "description": "Select a form element by its CSS."
        },
        "find:": {
            "values": [],
            "description": "Find text on the page."
        },
        "find-text:": {
            "values": [],
            "description": "Find text on the page."
        },
        "compare:": {
            "values": [],
            "description": "Compare found text with give text."
        },
        "element:": {
            "values": [],
            "description": "Starting block for the form elements to be selected."
        },
        "number-of-threads:": {
            "values": [],
            "description": "Number of threads, load testing needs to be performed with."
        },
        "target-language:": {
            "values": [],
            "description": "Target language this script is defined to be executed on."
        },
        "data-binding:": {
            "values": [],
            "description": "Path to file(s) that feed data to this script."
        },
    },
    "sequences": {
        "configuration:": {
            "values": [
                "target-language",
                "terminate-on-first-failure",
                "data-binding",
                "load-testing",
                "number-of-threads",
                "browsers"
            ]
        },
        "root": {
            "values": [
                "version:",
                "configuration:",
                "start:"
            ]
        },
        "start": {
            "values": [
                "- page:"
            ]
        },
        "- page:": {
            "values": [
                "browsers",
                "url",
                "wait",
                "change",
                "check",
                "page"
            ]
        },
        "change:": {
            "values": [
                "element"
            ]
        },
        "check:": {
            "values": [
                "element"
            ]
        },
        "element:": {
            "values": [
                "input"
            ]
        },
        "- input:": {
            "values": [
                "by-name",
                "name",
                "by-tag",
                "by-xpath",
                "tag",
                "xpath",
                "id",
                "by-id",
                "css",
                "by-css"
            ]
        }
    }
};

var t = JSON.parse(str);
console.log(t.tags);
