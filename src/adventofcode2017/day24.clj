(ns adventofcode2017.day24
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn get-input
  ([] (get-input "2017/day24.data"))
  ([filename]
   (->> (io/resource filename)
        io/reader
        line-seq
        (map #(str/split % #"/"))
        (map (fn [pipe]
               (mapv #(Integer/parseInt %) pipe))))))


(defn get-connections [coll [bridge end]]
  (->> coll
       (filter #(= (.indexOf bridge %)
                   -1))
       (filter #(or (= end (first %))
                    (= end (second %))))
       (map (fn [[l r :as pipe]]
              [(conj bridge pipe)
               (if (= end l) r l)]))))


(defn build-bridges [coll]
  (let [get-connections-fn (partial get-connections coll)]
    (loop [bridges []
           parts [[[] 0]]]
      (if (= 0 (count parts))
        bridges
        (recur (apply conj bridges (map first parts))
               (apply concat
                      (map get-connections-fn parts)))))))


(defn sum-str [coll]
  (reduce (fn [sum [l r]] (+ sum l r)) 0 coll))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (build-bridges input)
        (map sum-str)
        (sort >)
        first)))
;; 1511


(defn pick-largest [coll]
  (coll
   (apply max (keys coll))))



(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (build-bridges input)
        (group-by count)
        pick-largest
        (map sum-str)
        (sort >)
        first)))
;; 1511
