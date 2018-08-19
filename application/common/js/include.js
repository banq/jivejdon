function IncludeJavaScript(jsFile)
{
  document.write('<script type="text/javascript" src="'
    + jsFile + '"></script>'); 
}

IncludeJavaScript('prototype.js');
IncludeJavaScript('others.js');
IncludeJavaScript('window.js');
IncludeJavaScript('window_effects.js');



