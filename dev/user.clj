(ns user)

(prn "---REPL Customizations Initialized---")

(defn load-vars []
  (require
    '[clojure.string :as str]
    '[telemetry.options :as opts]))

(load-vars)