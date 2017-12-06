(ns adventofcode2016.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn count-letters [[groups id checksum]]
  (->> (seq groups)
       (filter #(not= \- %))
       (reduce (fn [agg c]
                 (assoc agg c (inc (get agg c -1))))
               {})
       (group-by val)
       (sort-by key)
       reverse
       (reduce (fn [acc [_ groups]]
                 (apply conj acc (->> groups
                                      (map first)
                                      (sort-by int))))
               [])
       str/join
       (#(vector % id checksum))))

(defn ex1 []
  (->> (slurp (io/resource "day4.data"))
       (#(str/split % #"[\r\n]+"))
       (map #(re-matches #"([a-z\-]+)(\d+)\[([a-z]+)\]" %))
       (map rest)
       (map count-letters)
       (filter (fn [[chars id checksum]] (str/starts-with? chars checksum)))
       (reduce (fn [acc [_ id _]] (+ acc (Integer/parseInt id))) 0)))

;; PART TWO

(def a-byte (int \a))
(def alphabet-bytes (inc (- (int \z) a-byte)))

(defn decipher [[groups id checksum]]
  (let [code (Integer/parseInt id)]
    (->> (str/split groups #"-")
         (map (fn [group]
                (map #(-> (int %)
                          (+ code)
                          (- a-byte)
                          (mod alphabet-bytes)
                          (+ a-byte)
                          char)
                     (seq group))))
         (map #(apply str %))
         (str/join " ")
         (#(vector id %)))))

(defn ex2 []
  (->> (slurp (io/resource "day4.data"))
       (#(str/split % #"[\r\n]+"))
       (map #(re-matches #"([a-z\-]+)(\d+)\[([a-z]+)\]" %))
       (map rest)
       (map decipher)
       (filter (fn [[_ name]] (= "northpole object storage" name)))
       first
       ((fn [[id _]] (println id)))))
