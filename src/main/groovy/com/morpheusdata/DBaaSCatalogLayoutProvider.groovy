package com.morpheusdata

import com.morpheusdata.core.AbstractCatalogItemLayoutProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Account
import com.morpheusdata.model.CatalogItemType
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.ContentSecurityPolicy
import com.morpheusdata.model.User
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class DBaaSCatalogLayoutProvider extends AbstractCatalogItemLayoutProvider {
	Plugin plugin
	MorpheusContext morpheus

	String code = 'dbaas-layout'
	String name = 'DBaaS Layout'

	DBaaSCatalogLayoutProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
	}

	@Override
	HTMLResponse renderTemplate(CatalogItemType catalogItemType, User user) {
		ViewModel<CatalogItemType> model = new ViewModel<>()

        // Create a payload to pass to the HTML template		
		def HashMap<String, String> catalogItemPayload = new HashMap<String, String>();

        // Add the catalog item to the payload object
		catalogItemPayload.put("catalogItem", catalogItemType)

        // Fetch the web nonce to align with the content security policy (CSP)
		// to enable the execution of the JavaScript script and add
		// it to the payload object used by the HTML template
		def webnonce = morpheus.getWebRequest().getNonceToken()
		catalogItemPayload.put("nonce",webnonce)
		
		model.object = catalogItemPayload
		getRenderer().renderTemplate("hbs/dbaasCatalogLayout", model)
	}

	@Override
	ContentSecurityPolicy getContentSecurityPolicy() {
		def csp = new ContentSecurityPolicy()
		csp.styleSrc = '*.jsdelivr.net'
		csp
	}
}