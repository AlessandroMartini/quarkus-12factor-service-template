pipeline {
    agent any
 	environment {
 		branchName = "${BRANCH_NAME}"
 		VERSION = readMavenPom().getVersion()
        PROJECT_VERSION = readMavenPom().getVersion()
        PROJECT_ARTIFACT_ID = readMavenPom().getArtifactId()
        PROJECT_GROUP_ID = readMavenPom().getGroupId()
 	}
    tools {
        maven 'maven'
        jdk 'jdk8'
    }
    triggers {
        pollSCM('H */4 * * 1-5')
    }
    stages {
        stage ('Build'){
            steps {
                echo '+-----------------------------------+'
                echo '| Building binary                   |'
                echo '+-----------------------------------+'
                sh 'mvn clean package -Dcheckstyle.skip=true -DskipTests'
            }
        }
        stage ('Verify code') {
            steps {
                echo '+-----------------------------------+'
                echo '| Check code style                  |'
                echo '+-----------------------------------+'
                sh 'mvn verify -DskipTests'
            }
        }
        stage ('Docker image build'){
            steps {
                echo '+-----------------------------------+'
                echo '| Building docker image             |'
                echo '+-----------------------------------+'
                sh 'docker build -t com.v2com/$PROJECT_ARTIFACT_ID -f src/main/docker/Dockerfile.jvm .'
            }
        }
        stage ('Perform Tests'){
            steps {
                echo '+-----------------------------------+'
                echo '| Running tests                     |'
                echo '+-----------------------------------+'
                sh 'mvn test -Dcheckstyle.skip=true'
            }
        }
        stage ("Deploy docker images") {
            when {
                expression { return env.branchName ==~ /develop/ || env.branchName ==~ /v(.*)/ || env.branchName ==~ /master/ }
            }
            steps {
                echo '+-----------------------------------+'
                echo '| Deploing docker images            |'
                echo '+-----------------------------------+'
                sh '$(aws ecr get-login --no-include-email --region sa-east-1)'
                script {
                    docker.withRegistry('https://315312718870.dkr.ecr.sa-east-1.amazonaws.com') {
                       docker.image('com.v2com/$PROJECT_ARTIFACT_ID').push('$PROJECT_VERSION')
                       docker.image('com.v2com/$PROJECT_ARTIFACT_ID').push('latest')
                    }
                }
            }
        }
        stage ('Sonar') {
            steps {
                echo '+-----------------------------------+'
                echo '| Running Sonar code checks         |'
                echo '+-----------------------------------+'
                sh 'mvn sonar:sonar -Dsonar.branch.name=${BRANCH_NAME} -Dsonar.login=$SONAR_AUTH_TOKEN -Dmaven.test.skip=true -Dmaven.test.skip=true -Dcheckstyle.skip=true -Dsonar.host.url=http://sonar.infra.v2com.mobi'
            }
        }
    }
}
