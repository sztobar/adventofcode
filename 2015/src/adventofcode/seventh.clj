(ns adventofcode.seventh
  (:require [adventofcode.file :refer :all]))

(def regex #"(NOT\s(\w+)|(\w+)\s(\w+)\s(\w+)|(\w+))\s->\s(\w+)")

(defn negate? [s]
  (re-find #"NOT" s))

(defn new-val? [s]
  (re-find #"\d+\s->" s))

(defn read-signal [s coll]
  (if-let [n-match (read-negate s)]
    (make-negate n-match coll)
    (if-let [a-match (read-assoc s)]
      (make-assoc a-match coll)
      (if-let [b-match (read-bit s)]
        (make-bit b-match coll))))
  (cond
    (negate? s) (make-negate s coll)
    (new-val? s) (make-new-val s coll)
    (bit-comb? s) (make-bit-comb s coll)))
