document.addEventListener("DOMContentLoaded", function(event) { 

$('.lazyload').lazyload();

$(function() { 
    var elm = $('.scrolldiv'); 
    var startPos = $(elm).offset().top; 
    if (window.matchMedia('(min-width: 992px)').matches) { 
    $.event.add(window, "scroll", function() { 
        var p = $(window).scrollTop(); 
        $(elm).css('position',((p) > startPos) ? 'fixed' : 'static'); 
        $(elm).css('top',((p) > startPos) ? '20px' : ''); 
    }); 
    };
}); 

});