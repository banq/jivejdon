TooltipManager = {
  options: {cssClassName: 'tooltip', delayOver: 200, delayOut: 1000, shiftX: 2, shiftY: 2,
            className: 'alphacube', width: 200, height: null, 
            draggable: false, minimizable: false, maximizable: false, showEffect: Element.show, hideEffect: Element.hide},
  ajaxInfo: null,
  elements: null,
  showTimer: null,
  hideTimer: null,

  init: function(cssClassName, ajaxInfo, tooltipOptions) {
    TooltipManager.options = Object.extend(TooltipManager.options, tooltipOptions || {});
    //dele by banq
    //cssClassName = TooltipManager.options.cssClassName || "tooltip";
    TooltipManager.ajaxInfo = ajaxInfo;
    TooltipManager.elements = $$("." + cssClassName);
    TooltipManager.elements.each(function(element) {
      element = $(element)
      var info = TooltipManager._getInfo(element);
      if (info.ajax) {
        element.ajaxId = info.id;
        element.ajaxInfo = ajaxInfo;
        element.frameWidth = tooltipOptions.width;
        element.frameHeight = tooltipOptions.height;;
      }
      else {
        element.tooltipElement = $(info.id);        
      }
      element.observe("mouseover", TooltipManager._mouseOver);
      element.observe("mouseout", TooltipManager._mouseOut);
    });
    Windows.addObserver(this);
  },
  
  addHTML: function(element, tooltipElement) {
    element = $(element);
    tooltipElement = $(tooltipElement);
    element.tooltipElement = tooltipElement;
    
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);
  },
  
  addAjax: function(element, ajaxInfo) {
    element = $(element);
    element.ajaxInfo = ajaxInfo;
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);    
  },
//add by banq    
  addAjax: function(element, ajaxInfo, width, height) {  	
  
    element = $(element);
    element.ajaxInfo = ajaxInfo;    
    element.frameWidth = width;
    element.frameHeight = height;
   
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);

  },    
        
    
  addURL: function(element, url, width, height) {
    element = $(element);
    element.url = url;
    element.frameWidth = width;
    element.frameHeight = height;
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);    
  },
    
  close: function() {
    if (TooltipManager.tooltipWindow)
      TooltipManager.tooltipWindow.hide();
  },
  
  preloadImages: function(path, images, extension) {
    if (!extension)
      extension = ".gif";
      
    //preload images
    $A(images).each(function(i) {
      var image = new Image(); 
      image.src= path + "/" + i + extension; 
    });
  },
  
  _showTooltip: function(element) {
    if (this.element == element)
      return;
    // Get original element
    while (element && (!element.tooltipElement && !element.ajaxInfo && !element.url)) 
      element = element.parentNode;
    this.element = element;
    
    TooltipManager.showTimer = null;
    if (TooltipManager.hideTimer)
      clearTimeout(TooltipManager.hideTimer);
    
    var position = Position.cumulativeOffset(element);
    var dimension = element.getDimensions();

    if (! this.tooltipWindow)
      this.tooltipWindow = new Window("__tooltip__", TooltipManager.options);
      
    this.tooltipWindow.hide();
    this.tooltipWindow.setLocation(position[1] + dimension.height + TooltipManager.options.shiftY, position[0] + TooltipManager.options.shiftX);

    Event.observe(this.tooltipWindow.element, "mouseover", function(event) {TooltipManager._tooltipOver(event, element)});
    Event.observe(this.tooltipWindow.element, "mouseout", function(event) {TooltipManager._tooltipOut(event, element)});
    
    
    // Reset width/height for computation
    this.tooltipWindow.height = TooltipManager.options.height;
    this.tooltipWindow.width = TooltipManager.options.width;

    // Ajax content
    if (element.ajaxInfo) {
      //add by banq
      if (element.frameWidth){    	 	  
    	    this.tooltipWindow.height = element.frameHeight;
            this.tooltipWindow.width = element.frameWidth;
      }
      
      var p = element.ajaxInfo.options.parameters;
      var saveParam = p;
      
      // Set by CSS
      if (element.ajaxId) {
        if (p)
          p += "&" + element.ajaxId;
        else
          p =   element.ajaxId;
      }
      element.ajaxInfo.options.parameters = p || "";
      this.tooltipWindow.setHTMLContent("");
      //this.tooltipWindow.setAjaxContent(element.ajaxInfo.url, element.ajaxInfo.options);
      //add by banq
      this.tooltipWindow.setAjaxContent(element.ajaxInfo.url, element.ajaxInfo.options, false, false);      
      element.ajaxInfo.options.parameters = saveParam;    
    } 
    // URL content
    else if (element.url) {
      this.tooltipWindow.setURL(element.url);
      this.tooltipWindow.setSize(element.frameWidth, element.frameHeight);

      // Set tooltip size
      this.tooltipWindow.height = element.frameHeight;
      this.tooltipWindow.width = element.frameWidth;
      //add by banq
      this.tooltipWindow.setLocation(element.offsetTop + TooltipManager.options.shiftY, element.offsetLeft + TooltipManager.options.shiftX);
 
    }
    // HTML content
    else
      this.tooltipWindow.setHTMLContent(element.tooltipElement.innerHTML);

    if (!element.ajaxInfo) {      
      this.tooltipWindow.show();
      this.tooltipWindow.toFront();
      this.tooltipWindow.updateHeight();
    }
  },
  
   _refreshheight: function(element) {
   alert("hello");
    if (this.tooltipWindow) {
      this.tooltipWindow.updateHeight();
  
    }
  },
  
  _hideTooltip: function(element) {
    if (this.tooltipWindow) {
      this.tooltipWindow.hide();
      this.element = null;
    }
  },
  
  _mouseOver: function (event) {
    var element = Event.element(event);
    if (TooltipManager.showTimer) 
      clearTimeout(TooltipManager.showTimer);
    
    TooltipManager.showTimer = setTimeout(function() {TooltipManager._showTooltip(element)}, TooltipManager.options.delayOver)
  },
  
  _mouseOut: function(event) {
    var element = Event.element(event);
    if (TooltipManager.showTimer) {
      clearTimeout(TooltipManager.showTimer);
      TooltipManager.showTimer = null;
      return;
    }
    if (TooltipManager.tooltipWindow)
      TooltipManager.hideTimer = setTimeout(function() {TooltipManager._hideTooltip(element)}, TooltipManager.options.delayOut)
  },
  
  _tooltipOver: function(event, element) {
    if (TooltipManager.hideTimer) {
      clearTimeout(TooltipManager.hideTimer);
      TooltipManager.hideTimer = null;
    }
  },
  
  _tooltipOut: function(event, element) {
    if (TooltipManager.hideTimer == null)
      TooltipManager.hideTimer = setTimeout(function() {TooltipManager._hideTooltip(element)}, TooltipManager.options.delayOut)
  },
  
  _getInfo: function(element) {
    // Find html_ for static content
    var id = element.className.split(' ').detect(function(name) {return name.indexOf("html_") == 0});
    var ajax = true;
    if (id)
      ajax = false;
    else 
      // Find ajax_ for ajax content
      id = element.className.split(' ').detect(function(name) {return name.indexOf("ajax_") == 0});
    
    id = id.substr(id.indexOf('_')+1, id.length)
    return id ? {ajax: ajax, id: id} : null;
  }
  
  showNow: function(element, ajaxInfo, tooltipOptions) {
    TooltipManager.options = Object.extend(TooltipManager.options, tooltipOptions || {});    
    TooltipManager.ajaxInfo = ajaxInfo;
      var info = TooltipManager._getInfo(element);
      if (info.ajax) {
        element.ajaxId = info.id;
        element.ajaxInfo = ajaxInfo;
      }
      else {
        element.tooltipElement = $(info.id);
      }
   if (TooltipManager.showTimer) 
      clearTimeout(TooltipManager.showTimer);
    
    TooltipManager.showTimer = setTimeout(function() {TooltipManager._showTooltip(element)}, TooltipManager.options.delayOver);
       
    Windows.addObserver(this);
  },
};
