/**
	com.venkykowshik.startapp
	https://github.com/venkykowshik/com.venkykowshik.startapp
	
	Phonegap 3 plugin for check or launch other application in android device (iOS support).
	bug tracker: https://github.com/venkykowshik/org.apache.cordova.startapp/issues
	
	!! THIS SCRIPT FILE TO CORDOVA 3.5.*
	If you are using a version lower than 3.5, read this theme: https://github.com/venkykowshik/org.apache.cordova.startapp/issues/5#issuecomment-49974214
*/
var exec = require('cordova/exec');

module.exports = {
	/** 
	 * Check application for installed on device
	 *
	 * @param {String} message              app name
	 * @param {Function} completeCallback   The callback that is called when open app
	 * @param {Function} errorCallback		The callback that is called when application is not installed
	 */
	check: function(message, completeCallback, errorCallback) {
		exec(completeCallback, errorCallback, "startAppmx", "check", [message]);
	},
	startwaze: function(message, completeCallback, errorCallback) {
		exec(completeCallback, errorCallback, "startAppmx", "startwaze", [message]);
	},
	/** 
	 * Start application on device
	 *
	 * @param {Mixed} message				params, view documentation https://github.com/venkykowshik/com.venkykowshik.startapp
	 * @param {Function} completeCallback   The callback that is called when open app
	 * @param {Function} errorCallback		The callback that is called when an error occurred when the program starts.
	 */
	start: function(message, completeCallback, errorCallback) {
		exec(completeCallback, errorCallback, "startAppmx", "start", (typeof message === 'string') ? [message] : message);
	},
	mxplay: function(message, completeCallback, errorCallback) {
		exec(completeCallback, errorCallback, "startAppmx", "mxplay", (typeof message === 'string') ? [message] : message);
	}	
}
