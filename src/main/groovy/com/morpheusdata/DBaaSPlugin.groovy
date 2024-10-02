/*
* Copyright 2022 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.morpheusdata

import com.morpheusdata.model.Permission
import com.morpheusdata.core.Plugin
import com.morpheusdata.web.Dispatcher
import com.morpheusdata.views.HandlebarsRenderer

class DBaaSPlugin extends Plugin {

    @Override
    String getCode() {
        return 'dbaas'
    }

    @Override
    void initialize() {
        this.setName("Database as a Service")
        this.registerProvider(new DBaaSAlarmTaskProvider(this,this.morpheus))
        this.registerProvider(new DBaaSGuidanceProvider(this,this.morpheus))
        this.registerProvider(new DBaaSConnectionDetailsTabProvider(this,this.morpheus))        
        this.registerProvider(new DBaaSInsightsTabProvider(this,this.morpheus))        
        this.registerProvider(new DBaaSDedicatedInstanceTabProvider(this,this.morpheus))        
        //this.registerProvider(new DBaaSDatabaseManagementTabProvider(this,this.morpheus))        
        //this.registerProvider(new DBaaSUserManagementTabProvider(this,this.morpheus))        
        //this.registerProvider(new DBaaSBackupsTabProvider(this,this.morpheus))        
        this.registerProvider(new DBaaSCatalogLayoutProvider(this,this.morpheus))
        this.setRenderer(new HandlebarsRenderer(this.classLoader))        
        this.controllers.add(new DBaaSUserManagementController(this,morpheus));
        this.setPermissions([Permission.build('DBaaS Admin','dbaas-admin', [Permission.AccessType.none, Permission.AccessType.full])])
    }

    /**
     * Called when a plugin is being removed from the plugin manager (aka Uninstalled)
     */
    @Override
    void onDestroy() {
        //nothing to do for now
    }

	@Override
	public List<Permission> getPermissions() {
		Permission permission = new Permission('DBaaS Integration', 'dbaasPlugin', [Permission.AccessType.full])
		return [permission];
	}
}
