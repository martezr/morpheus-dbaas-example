package com.morpheusdata

import com.morpheusdata.model.Account
import com.morpheusdata.core.*
import com.morpheusdata.core.providers.TaskProvider
import com.morpheusdata.model.Icon
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.TaskType
import com.morpheusdata.model.TaskResult
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.Task
import com.morpheusdata.model.Workload
import com.morpheusdata.model.Instance
import com.morpheusdata.model.ComputeServer
import io.reactivex.rxjava3.core.*;
import groovy.util.logging.Slf4j
import com.morpheusdata.core.AbstractTaskService
import com.morpheusdata.core.ExecutableTaskInterface
import com.morpheusdata.core.data.DataQuery
import com.morpheusdata.core.data.DataFilter
import com.morpheusdata.model.OperationNotification
import com.morpheusdata.model.projection.OperationNotificationIdentityProjection
import com.morpheusdata.core.util.HttpApiClient

/**
 * Example TaskProvider
 */
@Slf4j
class DBaaSAlarmTaskProvider implements TaskProvider {
	MorpheusContext morpheusContext
	Plugin plugin
	AbstractTaskService service

	DBaaSAlarmTaskProvider(Plugin plugin, MorpheusContext morpheusContext) {
		this.plugin = plugin
		this.morpheusContext = morpheusContext
	}

	@Override
	MorpheusContext getMorpheus() {
		return morpheusContext
	}

	@Override
	Plugin getPlugin() {
		return plugin
	}

	@Override
	String getCode() {
		return "dbaasAlarms"
	}

	@Override
	TaskType.TaskScope getScope() {
		return TaskType.TaskScope.all
	}

	@Override
	String getName() {
		return 'DBaaS Alarms'
	}

	@Override
	String getDescription() {
		return 'A custom task that sends notification upon Terraform drift'
	}

	@Override
	TaskResult executeLocalTask(Task task, Map opts, Workload workload, ComputeServer server, Instance instance) {
		//TaskConfig config = buildLocalTaskConfig([:], task, [], opts).blockingGet()
		//TaskConfig config = buildLocalTaskConfig([:], task, [], [:]).blockingGet()

		println "Task Config ${opts}"
		if(instance) {
			config = buildInstanceTaskConfig(instance, [:], task, [], opts).blockingGet()
		}
		if(workload) {
			config = buildContainerTaskConfig(workload, [:], task, [], opts).blockingGet()
		}
		String jenkinsUrl = task.taskOptions.find { it.optionType.code == 'terraformNotifyWebhookURL' }?.value
		log.info "Webhook ${jenkinsUrl}"


		//def discoveryType = [
		//	name: "testing", title: "demo",
		//	code: "test", category: "test",
		//	canExecute: true, enabled: true,
		//	description: "testing123"
		//]

		def disoutput = morpheusContext.getInstance()
		println "DISICVOERY OUT: ${disoutput}"
		//def test = new AccountDiscoveryType(discoveryType)
		//morpheusContext.async.operationNotification.create(adds).blockingGet()


		def adds = []
		String zoneCategory = "dbaas.monitoring.alarm"
		def account = new Account(id: 1)
		Date date = new Date(); 
			def alarmConfig = [account:account, category:zoneCategory, name:"Database not backed up in 7 days",
			   eventKey:"TF Drift", externalId:2222,
			   acknowledged:false,
			   acknowledgedDate:null, acknowledgedByUser:null,
			   status:"critical", statusMessage:"Backup",
			   resourceName:"DB13", refType:'app', refId: 7,
			   uniqueId:2222,startDate:date]
			def add = new OperationNotification(alarmConfig)
			adds << add
		if(adds) {
			morpheusContext.async.operationNotification.create(adds).blockingGet()
		}

/*
        HttpApiClient client = new HttpApiClient()
        try {
            String path = "/job/${jobName}/${jenkinsParameters ? 'buildWithParameters' : 'build'}"
            HttpApiClient.RequestOptions requestOptions = new HttpApiClient.RequestOptions()
            requestOptions.headers = ['Accept':'application/json']
            def parametersMap = [:]
            if (jenkinsParameters) {
              parametersMap = new JsonSlurper().parseText(jenkinsParameters)
            }
            requestOptions.queryParams = [:]
            parametersMap?.each { key,value ->
                requestOptions.queryParams.put(key.toString(),value?.toString())
            }


            def results = client.callApi(jenkinsUrl,path,jenkinsUser,jenkinsToken,requestOptions,'POST')
            if(results.success) {}
			return TaskResult(
				success: true,
				data   : "test",
				output : "test"
		)
		        } finally {
            client.shutdownClient()
        }
*/
		new TaskResult(
				success: true,
				data   : "data",
				output : "data"
		)
	}

	TaskResult executeTask(Task task, TaskConfig config) {
		println config.accountId
		def taskOption = task.taskOptions.find { it.optionType.code == 'terraformDriftAlert' }
		String data = taskOption?.value?.reverse()
		new TaskResult(
				success: true,
				data   : data,
				output : data
		)
	}

	@Override
	TaskResult executeServerTask(ComputeServer server, Task task, Map opts) {
		TaskConfig config = buildComputeServerTaskConfig(server, [:], task, [], opts).blockingGet()
		context.executeCommandOnServer(server, 'echo $JAVA_HOME')
		context.executeCommandOnServer(server, 'echo $JAVA_HOME', false, 'user', 'password', null, null, null, false, false)
		executeTask(task, config)
	}

	@Override
	TaskResult executeServerTask(ComputeServer server, Task task) {
		TaskConfig config = buildComputeServerTaskConfig(server, [:], task, [], [:]).blockingGet()
		context.executeCommandOnServer(server, 'echo $JAVA_HOME')
		executeTask(task, config)
	}

	@Override
	TaskResult executeContainerTask(Workload workload, Task task, Map opts) {
		TaskConfig config = buildContainerTaskConfig(workload, [:], task, [], opts).blockingGet()
		context.executeCommandOnWorkload(workload, 'echo $JAVA_HOME')
		context.executeCommandOnWorkload(workload, 'echo $JAVA_HOME', 'user', 'password', null, null, null, false, null, false)
		executeTask(task, config)
	}

	@Override
	TaskResult executeContainerTask(Workload workload, Task task) {
		TaskConfig config = buildContainerTaskConfig(workload, [:], task, [], [:]).blockingGet()
		executeTask(task, config)
	}

	@Override
	TaskResult executeRemoteTask(Task task, Map opts, Workload workload, ComputeServer server, Instance instance) {
		TaskConfig config = buildRemoteTaskConfig([:], task, [], opts).blockingGet()
		context.executeCommandOnWorkload(container, 'echo $JAVA_HOME')
		executeTask(task, config)
	}

	@Override
	TaskResult executeRemoteTask(Task task, Workload workload, ComputeServer server, Instance instance) {
		TaskConfig config = buildRemoteTaskConfig([:], task, [], [:]).blockingGet()
		context.executeSshCommand('localhost', 8080, 'bob', 'password', 'echo $JAVA_HOME', null, null, null, false, null, LogLevel.debug, false, null, true)
		executeTask(task, config)
	}

	@Override
	Boolean isAllowExecuteLocal() {
		return true
	}

	@Override
	Boolean isAllowExecuteRemote() {
		return false
	}

	@Override
	Boolean isAllowExecuteResource() {
		return false
	}

	@Override
	Boolean isAllowLocalRepo() {
		return false
	}

	@Override
	Boolean isAllowRemoteKeyAuth() {
		return false
	}

	@Override
	Boolean hasResults() {
		return true
	}

	/**
	 * Builds an OptionType to take some text
	 * @return list of OptionType
	 */
	@Override
	List<OptionType> getOptionTypes() {
        /*
		OptionType notificationTypeOptionType = new OptionType(
				name: 'terraformDriftNotificationType',
				code: 'terraformDriftNotificationType',
				fieldName: 'terraformDriftNotificationType',
				displayOrder: 0,
				fieldLabel: 'Notification Type',
				optionSource: 'terraformInsightsNotificationTypeOptions',
				required: true,
				inputType: OptionType.InputType.SELECT
		)
*/
		return []
	}

	/**
	 * Returns the Task Type Icon for display when a user is browsing tasks
	 * @since 0.12.7
	 * @return Icon representation of assets stored in the src/assets of the project.
	 */
	@Override
	Icon getIcon() {
		return new Icon(path:"reverseText.png", darkPath: "reverseText.png")
	}
}