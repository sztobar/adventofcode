(ns adventofcode2017.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input []
  (slurp (io/resource "day5.data")))


(defn parse-input [input]
  (->> (str/split input #"\s\n")
       (map #(Integer/parseInt %))))


(defn start-jumps [coll]
  (let [len (count coll)]
    (loop [coll coll
           pos 0
           steps 0]
      (if (and (>= pos 0)
               (< pos len))
        (let [val (get coll pos)
              new-pos (+ pos val)]
          (recur (assoc coll pos (inc val)) new-pos (inc steps)))
        steps))))


(defn ex1 []
  (->> (get-input)
       parse-input
       vec
       start-jumps))



(defn start-strange-jumps [coll]
  (let [len (count coll)]
    (loop [coll coll
           pos 0
           steps 0]
      (if (and (>= pos 0)
               (< pos len))
        (let [val (get coll pos)
              new-pos (+ pos val)
              new-val (if (>= val 3) (dec val) (inc val))]
          (recur (assoc coll pos new-val) new-pos (inc steps)))
        steps))))


(defn ex2 []
  (->> (get-input)
       parse-input
       vec
       start-strange-jumps))
