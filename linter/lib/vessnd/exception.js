'use strict';

function VESSNDException(reason, mark) {
    // Super constructor
    Error.call(this);

    // Include stack trace in error object
    if (Error.captureStackTrace) {
        // Chrome and NodeJS
        Error.captureStackTrace(this, this.constructor);
    } else {
        // FF, IE 10+ and Safari 6+. Fallback for others
        this.stack = (new Error()).stack || '';
    }

    this.name = 'VESSNDException';
    this.reason = reason;
    this.mark = mark;
    this.message = (this.reason || '(unknown reason)') + (this.mark ? ' ' + this.mark.toString() : '');
}


// Inherit from Error
VESSNDException.prototype = Object.create(Error.prototype);
VESSNDException.prototype.constructor = VESSNDException;


VESSNDException.prototype.toString = function toString(compact) {
    var result = this.name + ': ';

    result += this.reason || '(unknown reason)';

    if (!compact && this.mark) {
        result += ' ' + this.mark.toString();
    }

    return result;
};


module.exports = VESSNDException;