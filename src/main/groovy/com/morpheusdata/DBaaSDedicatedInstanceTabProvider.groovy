package com.morpheusdata

import com.morpheusdata.core.AbstractInstanceTabProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.cypher.CypherAccess
import com.morpheusdata.model.Account
import com.morpheusdata.model.Instance
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.ContentSecurityPolicy
import com.morpheusdata.model.User
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import com.morpheusdata.core.util.RestApiUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
//import io.reactivex.Single
import groovy.util.logging.Slf4j
import org.yaml.snakeyaml.*

@Slf4j
class DBaaSDedicatedInstanceTabProvider extends AbstractInstanceTabProvider {
	Plugin plugin
	MorpheusContext morpheus
	RestApiUtil dataDogAPI

	String code = 'dbaas-dedicated-tab'
	String name = 'DBaaS Dedicated Instance'

	DBaaSDedicatedInstanceTabProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
		//this.dbaasAPI = new RestApiUtil()
	}

	DBaaSDedicatedInstanceTabProvider(Plugin plugin, MorpheusContext morpheusContext, RestApiUtil api, Account account) {
		this.morpheusContext = morpheusContext
		this.plugin = plugin
		//this.dbaasAPI = api
	}

	@Override
	HTMLResponse renderTemplate(Instance instance) {

		// Instantiate an object for storing data passed to the html template
		ViewModel<Instance> model = new ViewModel<>()
		
		// Retrieve additional details about the instance
        // https://developer.morpheusdata.com/api/com/morpheusdata/model/TaskConfig.InstanceConfig.html
		TaskConfig instanceDetails = morpheus.buildInstanceConfig(instance, [:], null, [], [:]).blockingGet()
        log.info "Instance Code ${instance.instanceTypeCode}"

		// Define an object for storing the data retrieved from the DataDog REST API
		def HashMap<String, String> dbaasPayload = new HashMap<String, String>();

		//def results = dataDogAPI.callApi(apiURL, "api/v1/hosts", "", "", new RestApiUtil.RestOptions(queryParams:['filter':normalizedInstanceName],headers:['Content-Type':'application/json','DD-API-KEY':settingsJson.ddApiKey,'DD-APPLICATION-KEY':settingsJson.ddAppKey], ignoreSSL: false), 'GET')

		//if (results.success == false){
		//	log.info("DataDog Plugin: Error making the REST API call ${results.errorCode}")
		//	return getRenderer().renderTemplate("hbs/instanceNotFoundTab", model)
		//}

		// Parse the JSON response payload returned from the REST API call.
        JsonSlurper slurper = new JsonSlurper()
		//def json = slurper.parseText(results.content)

        // Set the value of the model object to the HashMap object
        model.object = dbaasPayload

        // Render the HTML template located in 
        // resources/renderer/hbs/instanceTab.hbs
        getRenderer().renderTemplate("hbs/dbaasDedicatedTab", model)
	}


	// This method contains the logic for when the tab
	// should be displayed in the UI
	@Override
	Boolean show(Instance instance, User user, Account account) {
		// Set the tab to not be shown be default
		def show = false
		def dbaasPerms = user.permissions["dbaas-admin"]
		log.info "User Perms: ${dbaasPerms}"

		// If user is a DBaaS admin
		if (dbaasPerms == "full"){
			
		}
		// Only show the tab on the matching instance type code
        if (instance.instanceTypeCode == "mongodb"){
            show = true
        }
		return show
	}

	@Override
	ContentSecurityPolicy getContentSecurityPolicy() {
		def csp = new ContentSecurityPolicy()
		csp
	}
}