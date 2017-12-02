(ns adventofcode.file
  (:use [clojure.java.io]))

(defn file-reduce [s cb r]
  (with-open [rdr (reader s)]
    (reduce cb r (line-seq rdr))))
