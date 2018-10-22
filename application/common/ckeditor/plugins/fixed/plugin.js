/*
*   Plugin developed by Netbroad, C.B.
*
*   LICENCE: GPL, LGPL, MPL
*   NON-COMMERCIAL PLUGIN.
*
*   Website: netbroad.eu
*   Twitter: @netbroadcb
*   Facebook: Netbroad
*   LinkedIn: Netbroad
*
*/

CKEDITOR.plugins.add( 'fixed', {
    init: function( editor ) {
        window.addEventListener('scroll', function(){
            var content             = document.getElementsByClassName('cke_contents').item(0);
            var toolbar             = document.getElementsByClassName('cke_top').item(0);
            var editor              = document.getElementsByClassName('cke').item(0);
            var inner               = document.getElementsByClassName('cke_inner').item(0);
            var scrollvalue         = document.documentElement.scrollTop > document.body.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop;

            toolbar.style.width     = content.offsetWidth + "px";
            toolbar.style.top       = "0px";
            toolbar.style.left      = "0px";
            toolbar.style.right     = "0px";
            toolbar.style.margin    = "0 auto";
            toolbar.style.boxSizing = "border-box";

            if(toolbar.offsetTop <= scrollvalue){
                toolbar.style.position   = "fixed";
                content.style.paddingTop = toolbar.offsetHeight + "px";
            }

            if(editor.offsetTop > scrollvalue && (editor.offsetTop + editor.offsetHeight) >= (scrollvalue + toolbar.offsetHeight)){
                toolbar.style.position   = "relative";
                content.style.paddingTop = "0px";
            }

            if((editor.offsetTop + editor.offsetHeight) < (scrollvalue + toolbar.offsetHeight)){
                toolbar.style.position = "absolute";
                toolbar.style.top      = "calc(100% - " + toolbar.offsetHeight + "px)";
                inner.style.position   = "relative";
            }
        }, false);
    }
});