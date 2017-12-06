(ns adventofcode2017.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input []
  (slurp (io/resource "day2.data")))


(defn split-rows [input]
  (str/split input #"\n"))


(defn parse-row [row]
  (->> row
       (#(str/split % #"\s+"))
       (map #(Integer/parseInt %))))


(defn get-amplitude [row]
  (let [min (apply min row)
        max (apply max row)]
    (- max min)))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> input
        (split-rows)
        (map parse-row)
        (map get-amplitude)
        (apply +))))


(defn is-good [i]
  (and (not= i 1)
       (int? i)))


(defn get-evenly-division [row]
  (some
   (fn [cell]
     (let [result (some #(when (is-good (/ cell %)) %) row)]
       (when (some? result) (/ cell result))))
   row))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> input
        (split-rows)
        (map parse-row)
        (map get-evenly-division)
        (apply +))))
