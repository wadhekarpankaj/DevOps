def call(body) {

        def config = [:]
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = config
        body()

        node {
            // Clean workspace before doing anything
            deleteDir()

            try {
		
		         stage ('Checkout'){
			        bat "echo 'Checkout Started'"
		            checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'SVN', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: "${config.SVNurl}"]], workspaceUpdater: [$class: 'UpdateUpdater']])
	                bat "echo 'Checkout Completed'"
                }

                stage ('Build') {
                    bat "echo 'build Started'"
                    bat "mvn ${config.command}"
                    bat "echo 'build completed'"
                }
                
                stage ('JUnit'){
                    bat "echo '*************JUnit Analysis Started*************'"
                    junit '**/target/surefire-reports/*.xml'
                    archive 'target/*.war'
                    bat "echo '*************JUnit Analysis completed*************'"
                }
                
                stage ('JaCoCo'){
                    bat "echo '*************JaCoCo Analysis Started*************'"
                    jacoco classPattern: '**/target/classes ', execPattern: '**/target/**.exec'
                    bat "echo '*************JaCoCo Analysis completed************'"
                }
                
                stage ('PMD') {
                    bat "echo '*************PMD Analysis Started*************'"
                    pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
                    bat "echo '*************PMD Analysis completed************'"
                }
                
                stage ('FindBugs') {
                   bat "echo '*************FindBug Analysis Started*************'"
                    findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '', unHealthy: ''
                    bat "echo '*************FindBug Analysis completed************'"
                }
                
                stage ('Checkstyle') {
                    bat "echo '*************checkstyle Analysis Started*************'"
                    checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
                    bat "echo '*************checkstyle Analysis completed************'"
                }
                
                stage ('Sonar') {
                   bat "echo '*************Sonar Analysis Started*************'"
                    withSonarQubeEnv('My Sonar Server'){
                    bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                    bat "echo '*************Sonar Analysis completed************'"
                    }
                }
                
                stage ('Tests') {
                    parallel 'static': {
                        bat "echo 'shell scripts to run static tests...'"
                    },
                    'unit': {
                        bat "echo 'shell scripts to run unit tests...'"
                    },
                    'integration': {
                        bat "echo 'shell scripts to run integration tests...'"
                    }
                }
                stage ('Deploy') {
                    bat "echo 'deploying to server ${config.serverDomain}...'"
                }
            } catch (err) {
                currentBuild.result = 'FAILED'
                throw err
            }
        }
    }
