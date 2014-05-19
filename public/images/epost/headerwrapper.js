var zxSmartHeaderWrapper = { 
		
	id : null,

	getParameters : function () {
		var searchString = window.location.search.substring(1);
		var params = searchString.split("&");
		var hash = {};
	
		for (var i = 0; i < params.length; i++) {
			var val = params[i].split("=");
			hash[unescape(val[0])] = unescape(val[1]);
		}
		return hash;
	},
	
	writeResource : function (resourceUrl) {
		var scriptTag = document.createElement("script");
		scriptTag.type = "text/javascript";
		scriptTag.src = resourceUrl;
		document.getElementsByTagName("head")[0].appendChild(scriptTag);
	},
	
	injectSmartMenu : function() {
	
		if ( !headerData || !headerData.domain || !headerData.locale ) {
			return;
		}
	
		var resource = "//" + headerData.domain + "/uiresources/menu";
		var TYPE_PUBLISHER = "publisher";
		var TYPE_PROGRAM = "program";
		
		var urlParams = this.getParameters();
		
		if ( urlParams[TYPE_PUBLISHER] ||  headerData.publisherId ) {
			this.id = urlParams[TYPE_PUBLISHER] || headerData.publisherId ;
			resource += "/publisher";
		} else if ( urlParams[TYPE_PROGRAM] ||  ( headerData.programs && headerData.programs.length > 0 && headerData.programs[0].id )  ) {
			
			this.id = urlParams[TYPE_PROGRAM] || headerData.programs[0].id ;
			resource += "/advertiser";
		} else {
			resource += "/publisher";
		}
		
		resource += "/" + this.id + "/" + headerData.locale + ".js";
		this.writeResource(resource);
	}
};

zxSmartHeaderWrapper.injectSmartMenu();
