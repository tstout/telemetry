(ns telemetry.core
  (:gen-class :prefix "-"
              :methods [^{:static true}
                        [premain [String java.lang.instrument.Instrumentation] void]]
              :name telemetry.core.TelemetryAgent)
  (:import (java.lang.instrument Instrumentation ClassFileTransformer)))

(defn foo[]
  (println "foo invoked"))

(defn class-transformer []
  (reify ClassFileTransformer
    (transform [this loader className classBeingRedefined protectionDomain classfileBuffer]
      (println (str "Class: " className))
      classfileBuffer)))

(defn -premain
  "Invoked by JVM instrumentation agent machinery"
  [^String args ^Instrumentation instr]
  (println "premain invoked")
  (.addTransformer instr (class-transformer))
  (foo))


(defn -main
  ""
  [& args]
  (println "main invoked"))
