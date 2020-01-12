/**
 * Created by VanagaS on 06-03-2017.
 */
'use babel';

// eslint-disable-next-line import/extensions, import/no-extraneous-dependencies
var {CompositeDisposable} = require('atom');
var path = require('path');
var helper = require('atom-linter');
var vessnd = require('./lib/vessnd');
var yaml = require('js-yaml');

export default {
    config: {},

    activate() {
        require('atom-package-deps').install('linter-vessnd');
    },

    deactivate() {
        // this.subscriptions.dispose();
    },

    provideLinter() {
        return {
                grammarScopes: ['source.yaml', 'source.yml'],
                scope: 'file',
                name: 'Js-YAML',
                lintOnFly: true,
                lint: (TextEditor) => {
                    const filePath = TextEditor.getPath();
                    const fileText = TextEditor.getText();

                    const messages = [];
                    const processMessage = (type, message) => {
                        let line = message.mark.line;
                        const maxLine = TextEditor.getLineCount() - 1;
                        if (line > maxLine) {
                            line = maxLine;
                        }
                        const column = message.mark.column;
                        return {
                            type,
                            text: message.reason,
                            filePath,
                            range: helper.generateRange(TextEditor, line, column),
                        };
                    };

                    try {
                        vessnd.load(fileText, () = > ({}), {
                            filename: path.basename(filePath),
                            schema: yaml.DEFAULT_SAFE_SCHEMA,
                            onWarning: (warning) = > {
                                messages.push(processMessage('Warning', warning));
                            }
                        });
                    } catch (error) {
                        messages.push(processMessage('Error', error));
                    }

                    return messages;
                }
        };
    },
};
