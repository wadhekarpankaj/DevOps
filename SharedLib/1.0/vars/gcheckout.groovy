
def call(args) {
	
		echo "${args}"
      
	  checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'SVN', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: "${args}"]], workspaceUpdater: [$class: 'UpdateUpdater']])
	
}