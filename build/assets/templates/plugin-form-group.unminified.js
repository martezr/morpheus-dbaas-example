
	  	 (function(){
			var template = HandlebarsCustom.template, templates = HandlebarsCustom.templates = HandlebarsCustom.templates || {};
				templates['plugin-form-group'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "collapsed";
},"3":function(container,depth0,helpers,partials,data) {
    return " collapsible=\"\" ";
},"compiler":[8,">= 4.3.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, lookupProperty = container.lookupProperty || function(parent, propertyName) {
        if (Object.prototype.hasOwnProperty.call(parent, propertyName)) {
          return parent[propertyName];
        }
        return undefined
    };

  return "<fieldset>\n	<legend class=\""
    + ((stack1 = lookupProperty(helpers,"if").call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? lookupProperty(depth0,"isCollapsed") : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data,"loc":{"start":{"line":2,"column":16},"end":{"line":2,"column":51}}})) != null ? stack1 : "")
    + "\" "
    + ((stack1 = lookupProperty(helpers,"if").call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? lookupProperty(depth0,"isCollapsible") : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data,"loc":{"start":{"line":2,"column":53},"end":{"line":2,"column":97}}})) != null ? stack1 : "")
    + ">"
    + ((stack1 = ((helper = (helper = lookupProperty(helpers,"name") || (depth0 != null ? lookupProperty(depth0,"name") : depth0)) != null ? helper : container.hooks.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"name","hash":{},"data":data,"loc":{"start":{"line":2,"column":98},"end":{"line":2,"column":108}}}) : helper))) != null ? stack1 : "")
    + "</legend>\n	<div group-code=\""
    + ((stack1 = ((helper = (helper = lookupProperty(helpers,"code") || (depth0 != null ? lookupProperty(depth0,"code") : depth0)) != null ? helper : container.hooks.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"code","hash":{},"data":data,"loc":{"start":{"line":3,"column":18},"end":{"line":3,"column":28}}}) : helper))) != null ? stack1 : "")
    + "\"></div>\n</fieldset>";
},"useData":true});
		}());
	  	
