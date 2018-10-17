/**
 * Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

/* exported initSample */

if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 )
	CKEDITOR.tools.enableHtml5Elements( document );

// The trick to keep the editor in the sample quite small
// unless user specified own height.
CKEDITOR.config.height = 250;
CKEDITOR.config.width = 'auto';

var initSample = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'formBody' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				''
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
			CKEDITOR.replace( 'formBody', {
               language: 'zh-cn',
            extraPlugins: 'bbcode,base64image,autosave',  
 	// Remove unused plugins.
					removePlugins: 'filebrowser,format,horizontalrule,pastetext,pastefromword,scayt,showborderstable,tabletools,tableselection,wsc',
					// Remove unused buttons.
					removeButtons: 'Table,paragraph,List,Anchor,BGColor,Font,Strike,Subscript,Superscript',
					// Width and height are not supported in the BBCode format, so object resizing is disabled.
					disableObjectResizing: true,
                
            toolbar :
            [
                 [ 'Bold', 'Italic', 'Underline', 'Image','base64image','Styles','Link','Unlink','Source' ]
            ]
            } );
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'formBody' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();

