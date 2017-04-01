(ns telemetry.options
  (:require [clojure.string :as str]))

(defn split-opts
  [opt-str]
  (->>
    #" (?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))"
    (str/split opt-str)
    (map #(str/replace % "\"" ""))
    vec))


