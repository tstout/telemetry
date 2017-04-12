(ns telemetry.options
  (:require [clojure.string :as str]))

(defn split-opts
  "The java.lang.insturment agent interface provides arguments as a single string rather than an
  array of strings. This function converts the single string into a vector of arguments.
  Arguments with spaces should be quoted."
  [opt-str]
  (->>
    #" (?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))"
    (str/split opt-str)
    (map #(str/replace % "\"" ""))
    vec))


