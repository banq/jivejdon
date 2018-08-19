/*
Copyright (c) 2009 Jonathan Holland

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
*/

var JSManager = new function ()
{
	// Registered File List
	var fileList = [];
	
	// "Private" functions
	// Loads a JS file into the DOM
	function loadJSInclude(scriptPath, callback) {
	    var scriptNode = document.createElement('SCRIPT');
	    scriptNode.type = 'text/javascript';
	    scriptNode.src = scriptPath;
	    
	    var headNode = document.getElementsByTagName('HEAD');
	    if (headNode[0] != null)
	        headNode[0].appendChild(scriptNode);
	        
	    if (callback != null) {
	        // IE
	        scriptNode.onreadystatechange = 
	            function() {    
					if (scriptNode.readyState == 'loaded') {						
	                    callback();
	                }
	            };
	                
	        // Firefox
	        scriptNode.onload = function () {				
				callback();
			}					
	    }
	};

	// Used to register the callback chain
	function jsLoaderCallback(index, callback) {		
		return function () { 											
			if ((index + 1) < fileList.length)
				callback = fileList[index + 1].Callback;
			
			if (fileList[index].Loaded == null) {
				fileList[index].Loaded = true;
				loadJSInclude(fileList[index].FileName, callback); 				
			}
			else {
				callback();
			}
		};	
	}
	
	// Checks to see if a file is already registered
	function checkFile(fileName) {
		var foundFile = false;
		for (f in fileList) {
			if (fileList[f].FileName == fileName) {
				foundFile = true;
				break;
			}
		}
		
		return foundFile;
	}
	
	// "Public" functions
	return {
		/* Registers a JS File for loading with Init()  */
		Register : function(fileName) {						
			if (!checkFile(fileName)) {
				fileList.push ( { FileName : fileName} );
			}			
		},
		/* Loads all registered JS Files, then runs the passed callback */
		Init : function (callback) {
			// Setup callback chain
			for (f in fileList) {																	
				fileList[f].Callback = jsLoaderCallback(parseInt(f));	
				
				if (f == fileList.length - 1 && callback != null) {
					fileList[f].Callback = jsLoaderCallback(parseInt(f), callback);
				}
			}	
										
			// Run the Chain
			fileList[0].Callback();											
		},
		/*Loads a single JS File and executes a passed callback */
		Load : function(fileName, callback) {
			if (!checkFile(fileName)) {			
				fileList.push( { FileName : fileName, Loaded : true } );
				loadJSInclude(fileName, callback);
			}
			else if (callback != null) {			
				callback();			
			}
		}
	};
}();



