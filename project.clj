(defproject telemetry "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.442"]
                 [net.bytebuddy/byte-buddy "1.6.12"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]]
  :profiles {:uberjar {:aot :all}
             :dev     {:source-paths ["dev"]
                       :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                      [org.clojure/java.classpath "0.2.3"]]}}
  :manifest {"Premain-Class" "telemetry.core.TelemetryAgent"}
  :java-source-paths ["java"]
  :main ^:skip-aot telemetry.core.TelemetryAgent
  :repl-options {:init-ns user})
