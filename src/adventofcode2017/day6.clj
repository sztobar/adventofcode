(ns adventofcode2017.day6
  (:import (java.lang.Math))
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn find-max-idx [coll]
  (reduce
   (fn [i-max i]
     (let [v (coll i)
           max-v (coll i-max)]
       (if (> v max-v)
         i
         i-max)))
   0
   (range (count coll))))


(defn next-step [coll]
  (let [idx (find-max-idx coll)
        v (coll idx)
        len (count coll)
        part (int (Math/ceil (/ v len)))]
    (->
     (reduce
      (fn [[coll rem] idx]
        (let [new-rem (- rem part)
              part (if (> new-rem 0) part rem)]
          [(update coll idx + part) new-rem]))
      [(assoc coll idx 0) v]
      (take (int (Math/ceil (/ v part)))
            (map #(-> % inc (+ idx) (mod len)) (range len))))
     (nth 0))))


(defn get-input []
  (slurp (io/resource "day6.data")))


(defn parse-input [input]
  (->> (str/split input #"\s")
       (mapv read-string)))


(defn solve [init-mem]
  (loop [mem init-mem
         coll #{}]
    (if (coll mem)
      (count coll)
      (recur (next-step mem) (conj coll mem)))))


(defn ex1
  ([]
   (ex1 (parse-input (get-input))))
  ([input]
   (solve input)))



(defn solve-ex2 [init-mem]
  (loop [mem init-mem
         coll {}
         step 0]
    (if-let [start (coll mem)]
      (- step start)
      (recur (next-step mem) (assoc coll mem step) (inc step)))))


(defn ex2
  ([]
   (ex2 (parse-input (get-input))))
  ([input]
   (solve-ex2 input)))
