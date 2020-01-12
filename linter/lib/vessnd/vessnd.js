var vessnd = ({

        // objectArray: ["start:", "configuration:"],
        objectArray: ["configuration", "start"],
        colonObjectArray: ["configuration:", "start:"],
        bangColonObjectArray: ["!configuration:", "!start:"],
        tagArray: {
            "configuration:": "!configuration",
            "start:": "!start"
        },
        parentTag: null,
        fixIndentation: false,
        _line: null,
        messageArray: [],

        getTag: function (vessndTagName) {
            return this.tagArray[vessndTagName];
        },

        is_EOL: function (c) {
            return (c === 0x0A/* LF */) || (c === 0x0D/* CR */);
        },

        is_WHITE_SPACE: function (c) {
            return (c === 0x09/* Tab */) || (c === 0x20/* Space */);
        },

        is_WS_OR_EOL: function (c) {
            return (c === 0x09/* Tab */) ||
                (c === 0x20/* Space */) ||
                (c === 0x0A/* LF */) ||
                (c === 0x0D/* CR */);
        },

        findInArray: function (name) {
            return this.objectArray.includes(name);
        },

        findInColonArray: function (name) {
            return this.colonObjectArray.includes(name);
        },

        findInBangColonArray: function (name) {
            return this.bangColonObjectArray.includes(name);
        },

        printCurrentString: function (state) {
            if(state === undefined) {
                return;
            }
            var pos = state.position;
            var ch = state.input.charCodeAt(pos);
            var returnString = String.fromCharCode(ch);
            while (ch != 0 && !this.is_WS_OR_EOL(ch)) {
                ch = state.input.charCodeAt(++pos);
                returnString += String.fromCharCode(ch);
            }
            // console.log(returnString.trim());
            /*
            if (!this.is_EOL(ch)) {
                var valueString = "";
                if (returnString === "-") {
                    valueString = "-";
                    ++pos;
                    ch = state.input.charCodeAt(++pos);
                }
                while (ch != 0 && !this.is_WS_OR_EOL(ch)) {
                    ch = state.input.charCodeAt(++pos);
                    valueString += String.fromCharCode(ch);
                }
                console.log("Val: " + valueString.trim());
            }
            */
            return returnString.trim();
        },

        printPreviousString: function (state) {
            if(state === undefined) {
                return;
            }
            var pos = state.position;
            var ch = state.input.charCodeAt(pos);
            var returnString = String.fromCharCode(ch);
            var lineIndent = state.lineIndent;
            while (ch != 0 && !this.is_WS_OR_EOL(ch) && lineIndent > 0) {
                --lineIndent;
                ch = state.input.charCodeAt(--pos);
                returnString = String.fromCharCode(ch) + returnString;
            }
            return returnString.trim();
        },

        checkTags: function (state) {
            this.printCurrentString(state);
        },

        printState: function (state, message, init) {
            if(init !== undefined) {
                console.log(init);
                this.messageArray.push(init);
            }
            var appendString = "";
            if(state !== undefined) {
                appendString = ". CURSOR(" + this.printCurrentString(state) + "), " +
                    "line("+state.line+"), lineIndent("+state.lineIndent+")," +
                    " lineStart("+state.lineStart+"), tag("+state.tag+"), anchor("+state.anchor+")";
            }
            this.messageArray.push(message + appendString + "[" + this.getStackTrace() + "]");
            console.log(message);
            if(state !== undefined) {
                ch = state.input.charCodeAt(state.position);
                /*console.log("listener: " + JSON.stringify(state.listener, undefined, 4) + " - typeMap: " +
                 state.typeMap + " - length: " + state.length + " - position " +
                 state.position + " - line " + state.line + " - lineStart " + state.lineStart + " - lineIndent: " +
                 state.lineIndent + " - documents: " + JSON.stringify(state.docments, undefined, 4) + " - tag: " + state.tag +
                 " - anchor: " + state.anchor + " - result: " + state.result);*/
                console.log(" - position(" + state.position + "), line(" + state.line + "), lineStart(" + state.lineStart + ") lineIndent(" +
                    state.lineIndent + ") tag(" + state.tag + ") anchor(" + state.anchor + ") result(" + JSON.stringify(state.result, undefined, 4) +
                    "), char(" + ch + ", cursor(" + this.printCurrentString(state) + ")");
            }
        },

        parseIndents: function () {
            var currentIndentation = 0;
            var previousIndentation = 0;

            this.messageArray.forEach((message) => {
                if(message == null) {
                    return;
                }
                if (!message.startsWith("end")) {
                    if (message.startsWith("start")) {
                        currentIndentation++;
                    }
                    var numSpaces = "";
                    for (var i = 0; i < currentIndentation; i++) {
                        numSpaces += "  ";
                    }
                    if (message.startsWith("start")) {
                        console.log(numSpaces + ">> " + message);
                    } else if (message.startsWith("  mid")) {
                        console.log(numSpaces + ". " + message);
                    } else {
                        console.log(numSpaces + "  // " + message);
                    }
                }
                if (message.startsWith("end")) {
                    var numSpaces = "";
                    for (var i = 0; i < currentIndentation; i++) {
                        numSpaces += "  ";
                    }
                    console.log(numSpaces + "<< " + message);
                    currentIndentation--;
                }
            });
        },

        getStackTrace: function() {
            var obj = {};
            Error.captureStackTrace(obj, this.getStackTrace);

            var pattern = /.*at ([^(]*)\([^:]*:[^:]*:([^:]*):[^\n]*\n/g,
                matches,
                locations = "";

            var methods = {

                get: function(name) {
                    var short = name.trim();
                    switch(name.trim()) {
                        case "readDocument":
                            short = "rd";
                            break;
                        case "readBlockMapping":
                            short = "rbm";
                            break;
                        case "readTagProperty":
                            short = "rtp";
                             break;
                        case "composeNode":
                            short = "cn";
                            break;
                        case "readBlockSequence":
                            short = "rbs";
                            break;
                        case "readBlockScalar":
                            short = "rbsc";
                            break;
                        case "readAlias":
                            short = "ra";
                            break;
                        case "readPlainScalar":
                            short = "rps";
                            break;
                        case "readDoubleQuotedScalar":
                            short = "rdqs";
                            break;
                        case "readFlowCollection":
                            short = "rfc";
                            break;
                        case "readSingleQuotedScalar":
                            short = "rsqs";
                            break;
                        case "readAnchorProperty":
                            short = "rap";
                            break;
                    }
                    return short;
                }
            };

            while (matches = pattern.exec(obj.stack)) {
                var match = matches[1];
                if(match.startsWith("Object")) {
                    continue;
                }
                if(match.startsWith("loadDocuments")) {
                    break;
                }

                match = methods.get(match);
                locations += match + "("+matches[2]+") ";
            }

            return locations;
        },

        finalOutput: function() {
            console.log(JSON.stringify(this.messageArray, undefined, 4));
            console.log(this.parseIndents());
        },
});

module.exports = vessnd;