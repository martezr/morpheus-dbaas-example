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
class DBaaSConnectionDetailsTabProvider extends AbstractInstanceTabProvider {
	Plugin plugin
	MorpheusContext morpheus
	//RestApiUtil dataDogAPI

	String code = 'connection-details'
	String name = 'Connection Details'

	DBaaSConnectionDetailsTabProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
		//this.dbaasAPI = new RestApiUtil()
	}

	DBaaSConnectionDetailsTabProvider(Plugin plugin, MorpheusContext morpheusContext, RestApiUtil api, Account account) {
		this.morpheusContext = morpheusContext
		this.plugin = plugin
		//this.dbaasAPI = api
	}

	@Override
	HTMLResponse renderTemplate(Instance instance) {

		//executeServerTask(ComputeServer server, Task task)

		// Instantiate an object for storing data passed to the html template
		ViewModel<Instance> model = new ViewModel<>()
		
		// Retrieve additional details about the instance
        // https://developer.morpheusdata.com/api/com/morpheusdata/model/TaskConfig.InstanceConfig.html
		TaskConfig instanceDetails = morpheus.buildInstanceConfig(instance, [:], null, [], [:]).blockingGet()
        log.info "Instance Code ${instance.instanceTypeCode}"
        log.info "Instance Domain ${instanceDetails.instance.domainName}"

		// Define an object for storing the data retrieved from the DataDog REST API
		def HashMap<String, String> dbaasPayload = new HashMap<String, String>();
		dbaasPayload.put("host", instance.hostName + "." + instanceDetails.instance.domainName)

		def webnonce = morpheus.getWebRequest().getNonceToken()
		dbaasPayload.put("webnonce",webnonce)

		// Parse the JSON response payload returned from the REST API call.
        JsonSlurper slurper = new JsonSlurper()
		//def json = slurper.parseText(results.content)

        // Set the value of the model object to the HashMap object
        model.object = dbaasPayload

        // Render the HTML template located in 
        // resources/renderer/hbs/instanceTab.hbs
        getRenderer().renderTemplate("hbs/dbaasConnectionDetails", model)
	}


	// This method contains the logic for when the tab
	// should be displayed in the UI
	@Override
	Boolean show(Instance instance, User user, Account account) {
		// Set the tab to not be shown be default
		def show = false
		def dbaasPerms = user.permissions["dbaas-admin"]

		// Only show the tab on the matching instance type code
        if (instance.instanceTypeCode == "dbaas"){
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