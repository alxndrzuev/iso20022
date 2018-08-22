import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import java.nio.charset.Charset

def appenderList = ["CONSOLE"]
def appName = System.getenv("SERVICE_NAME") ?: "iso20022"
def appHost = System.getenv("HOST")
def profile = System.getenv("SPRING_PROFILES_ACTIVE")

println "=" * 80
println """
    APP NAME          : $appName
    APP HOST          : $appHost
    APP PROFILE       : $profile
"""
println "=" * 80

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = "%d %-5level [ %t ] %class{36}.%M:%L | %m %n"
    }
}
appenderList << "CONSOLE"

appender("ROLLING", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = "%d %-5level [ %t ] %class{36}.%M:%L | %m %n"
    }

    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "logs/application_%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
}
appenderList << "ROLLING"

root(Level.DEBUG, appenderList)
