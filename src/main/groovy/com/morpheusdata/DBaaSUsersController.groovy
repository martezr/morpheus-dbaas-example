package com.morpheusdata

import com.morpheusdata.views.JsonResponse
import com.morpheusdata.web.PluginController
import com.morpheusdata.model.Permission
import com.morpheusdata.model.Account
import com.morpheusdata.model.ReferenceData
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import com.morpheusdata.web.PluginController
import com.morpheusdata.web.Route
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.cypher.CypherAccess
import com.morpheusdata.model.projection.ReferenceDataSyncProjection
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Slf4j
class DBaaSUserManagementController implements PluginController {

  MorpheusContext morpheusContext
  Plugin plugin

  public DBaaSUserManagementController(Plugin plugin, MorpheusContext morpheusContext) {
    this.plugin = plugin
    this.morpheusContext = morpheusContext
  }

  @Override
  public String getCode() {
    return 'dbaas-users-controller'
  }

  @Override
  String getName() {
    return 'DBaaS Users Controller'
  }

  @Override
  MorpheusContext getMorpheus() {
    return morpheusContext
  }

  List<Route> getRoutes() {
		[
			Route.build("/dbaas/createUser", "createUser", [Permission.build("dbaasPlugin", "full")]),
			Route.build("/dbaas/listUsers", "listUsers", [Permission.build("dbaasPlugin", "full")]),
			Route.build("/dbaas/deleteUser", "deleteUser", [Permission.build("dbaasPlugin", "full")]),
		]
  }

  public getPermissions() {
    return [
            new Permission('DBaaS User', 'dbaas-user', [Permission.AccessType.none, Permission.AccessType.read, Permission.AccessType.full]),
    ]
  }


	// listUsers
	def listUsers(ViewModel <Map> model){
		println model
		model.object.foo = "fizz"
		return JsonResponse.of(model.object)
	}

	// createUser
	def createUser(ViewModel <Map> model){
        model.object.opentfvariables = payload
		return JsonResponse.of(model.object)
	}

	// deleteUser
	def deleteUser(ViewModel <Map> model){
        model.object.opentfvariables = payload
		return JsonResponse.of(model.object)
	}
}