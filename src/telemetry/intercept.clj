(ns telemetry.intercept
  (:import (telemetry.interceptor GeneralInterceptor))
  (:gen-class
    :name telemetry.intercept.GenInt
    :extends telemetry.interceptor.GeneralInterceptor
    :exposes-methods [onException onEnter onExit]))

(defn -onException [this method args exc]
  (println "onException - " method))

(defn -onEnter [this method args]
  (println "onEnter " method args))

(defn -onExit [this method args returnVal]
  (println "onExit " method))



