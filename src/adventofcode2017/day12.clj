(ns adventofcode2017.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))


(defn get-input
  ([] (get-input "2017/day12.data"))
  ([filename]
   (-> (io/resource filename)
       io/reader
       line-seq)))


(defn parse-line [line]
  (let [[node connections] (str/split line #"\s<->\s")
        connections (str/split connections #", ")]
    [node connections]))


(defn parse-input [input]
  (map parse-line input))


(defn create-connection [coll [node connections]]
  (assoc coll node connections))


(defn create-all-connections [coll]
  (reduce create-connection {} coll))


(defn add-connections [agg node conns]
  (let [agg (conj agg node)]
    (reduce (fn [agg node]
              (add-connections agg node conns))
            agg
            (filter #(nil? (agg %)) (conns node)))))


(defn get-connections [node conns]
  (add-connections #{} node conns))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        create-all-connections
        (get-connections "0")
        count)))
;; 306


;; PART TWO


(defn flatten-connections [coll]
  (map (fn [[node conns]] (into [] (concat [node] conns))) coll))


(defn get-matching-groups [coll items]
  (let [result (set/select #(some % items)
                       coll)]
    (if (not= 0 (count result))
      result
      nil)))


(defn add-group [groups nodes]
  (if-let [existing-groups-set (get-matching-groups groups nodes)]
    (let [cleared-groups (set/difference groups existing-groups-set)
          group-to-add (set (apply set/union existing-groups-set)); flatten set
          group-to-add (apply conj group-to-add nodes)]; add nodes
      (conj cleared-groups group-to-add))
      (conj groups (set nodes))))


(defn count-groups [coll]
  (count
   (reduce add-group #{} coll)))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (parse-input input)
        flatten-connections
        count-groups)))
