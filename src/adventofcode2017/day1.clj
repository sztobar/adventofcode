(ns adventofcode2017.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input []
  (slurp (io/resource "day1.data")))


(defn input-to-list [input]
  (->> (str/split input #"")
       (map #(Integer/parseInt %))))


(defn add-first-to-end [coll]
  (conj coll (first coll)))


(defn traverse-list [coll]
  (let [len (count coll)]
    (loop [sum 0
           i 1]
      (if (>= i len)
        sum
        (let [curr (nth coll i)
              prev (nth coll (dec i))
              add (if (= curr prev) curr 0)]
            (recur (+ sum add) (inc i)))))))


(defn ex1
  ([]
   (ex1 (get-input)))
  ([input]
   (->> input
        (input-to-list)
        (add-first-to-end)
        (traverse-list))))


(defn sum-half-dups [coll]
  (let [len (count coll)
        hlen (/ len 2)]
    (loop [sum 0
           i 0]
      (if (= i len)
        sum
        (let [curr (nth coll i)
              next (nth coll (mod (+ i hlen) len))
              add (if (= curr next) curr 0)]
          (recur (+ sum add) (inc i)))))))


(defn ex2
  ([]
   (ex2 (get-input)))
  ([input]
   (->> input
        (input-to-list)
        (sum-half-dups))))
