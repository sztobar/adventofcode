(ns adventofcode2016.day3
  (:require [clojure.string :as str]
            [clojure.zip :as zip]
            [clojure.java.io :as io]))

(defn is-trangle [[_1 _2 _3]]
  (and
   (> (+ _1 _2) _3)
   (> (+ _1 _3) _2)
   (> (+ _2 _3) _1)))

(defn count-triangles [inputs]
  (reduce (fn [acc edges]
            (if (is-trangle edges)
              (inc acc)
              acc)) 0 inputs))

(defn parse-input [input]
  (->> (str/split input #"[\r\n]+")
       (map str/trim)
       (map #(str/split % #"\s+"))
       (map (fn [coll]
              (map #(Integer/parseInt %) coll)))))

(defn ex1 []
  (count-triangles (parse-input (slurp (io/resource "day3.data")))))

;; PART TWO

(defn three-next [z]
  (-> z zip/right zip/right zip/right))

(defn get-first [z]
  [(-> z zip/next zip/node)
   (-> z zip/right zip/next zip/node)
   (-> z zip/right zip/right zip/next zip/node)])

(defn get-second [z]
  [(-> z zip/next zip/next zip/node)
   (-> z zip/right zip/next zip/next zip/node)
   (-> z zip/right zip/right zip/next zip/next zip/node)])

(defn get-third [z]
  [(-> z zip/next zip/next zip/next zip/node)
   (-> z zip/right zip/next zip/next zip/next zip/node)
   (-> z zip/right zip/right zip/next zip/next zip/next zip/node)])

(defn parse-input-two [inputs]
  (loop [z (-> inputs zip/seq-zip zip/next)
         acc []]
    (if (nil? z)
      acc
      (recur
       (three-next z)
       (conj acc
             (get-first z)
             (get-second z)
             (get-third z))))))


(defn ex2 []
  (let [inputs (parse-input (slurp (io/resource "day3.data")))]
    (count-triangles (parse-input-two inputs))))
