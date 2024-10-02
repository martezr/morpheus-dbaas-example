package com.morpheusdata

import com.morpheusdata.core.providers.AbstractGuidanceRecommendationProvider
import com.morpheusdata.model.Account
import com.morpheusdata.core.*
import com.morpheusdata.core.providers.TaskProvider
import com.morpheusdata.model.Icon
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.TaskType
import com.morpheusdata.model.TaskResult
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.AccountDiscovery
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import io.reactivex.rxjava3.core.*;
import groovy.util.logging.Slf4j
import com.morpheusdata.core.AbstractTaskService
import com.morpheusdata.core.ExecutableTaskInterface
import com.morpheusdata.core.data.DataQuery
import com.morpheusdata.core.data.DataFilter
import com.morpheusdata.model.OperationNotification
import com.morpheusdata.model.projection.OperationNotificationIdentityProjection
import com.morpheusdata.core.util.HttpApiClient

//@Slf4j
class DBaaSGuidanceProvider extends AbstractGuidanceRecommendationProvider {
	Plugin plugin
	MorpheusContext morpheus

	String code = 'dbaas-guidance'
	String name = 'DBaaS Guidance'

	DBaaSGuidanceProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
	}

//	DBaaSGuidanceProvider(Plugin plugin, MorpheusContext morpheusContext, RestApiUtil api, Account account) {
//		this.morpheusContext = morpheusContext
//		this.plugin = plugin
//	}

	@Override
	Icon getIcon() {
		return new Icon(path:"reverseText.png", darkPath: "reverseText.png")
	}

	@Override
	String getDescription() {
		return code
	}

	@Override
	String getTitle() {
		return name
	}

	@Override
	void calculateRecommendations(){
		println "Testing Suttff"
	}

	@Override
	void execute(AccountDiscovery discovery){

	}

	@Override
	HTMLResponse renderTemplate(AccountDiscovery discovery) {

		// Instantiate an object for storing data passed to the html template
		ViewModel<AccountDiscovery> model = new ViewModel<>()
		
		// Define an object for storing the data retrieved from the DataDog REST API
		def HashMap<String, String> dbaasPayload = new HashMap<String, String>();

        // Set the value of the model object to the HashMap object
        model.object = dbaasPayload

        // Render the HTML template located in 
        // resources/renderer/hbs/instanceTab.hbs
        getRenderer().renderTemplate("hbs/dbaasDedicatedTab", model)
	}


}