(ns adventofcode2017.day21
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [adventofcode2017.day19 :refer [transpose]]))


(defn get-input
  ([] (get-input "2017/day21.data"))
  ([filename]
   (-> (io/resource filename)
       io/reader
       line-seq
       vec)))


(defn parse-input [input]
  (->> input
       (map #(str/split % #" => "))
       (map (fn [rule] (map #(str/split % #"/") rule)))
       (reduce (fn [agg [in out]]
                 (let [group (count in)]
                   (update agg group assoc in out)))
               {2 {} 3 {}})))


(def init-pattern
  [".#."
   "..#"
   "###"])


(defn split-art [art]
  (let [size (count art)
        part (+ 2 (mod size 2))]
    (->> art
         (map #(partition part %))
         (map #(mapv vec %))
         #_(map #(mapv str/join))
         (partition part)
         (map vec)
         (map transpose)
         #_(apply concat)
         vec
         #_(mapv str/join))))


(defn reverse-rows [coll]
  (mapv (comp vec reverse) coll))


(defn rotate-90 [coll]
  (reverse-rows (transpose coll)))


(defn rotate-180 [coll]
  (rotate-90 (rotate-90 coll)))


(defn rotate-270 [coll]
  (transpose (reverse-rows coll)))


;; horizontal flip
(defn h-flip [coll]
  (reverse-rows coll))


(defn variations [art]
  (let [flipped (h-flip art)]
    (->> [art
          (rotate-90 art)
          (rotate-180 art)
          (rotate-270 art)
          flipped
          (rotate-90 flipped)
          (rotate-180 flipped)
          (rotate-270 flipped)]
         set
         (mapv #(mapv str/join %)))))


(defn apply-rules [rules chunk]
  (let [chunk-keys (variations chunk)
        len (count chunk)
        rules (rules len)]
    (->> (map #(rules %) chunk-keys)
         (filter identity)
         first)))


(defn enhance [art rules]
  (let [grouped-chunks (split-art art)
        groups-size (count grouped-chunks)
        chunks (apply concat grouped-chunks)
        enhanced (mapv #(apply-rules rules %) chunks)
        enhanced-size (count (enhanced 0))]
    (->> (mapv
          (fn [i]
            (mapv
             (fn [k]
               (mapv (fn [j]
                       (get-in enhanced [(+ (* i groups-size) j) k]))
                     (range groups-size)))
             (range enhanced-size)))
          (range groups-size))
         (mapv (fn [group] (mapv str/join group)))
         flatten
         vec)))


(defn generate-art [n rules]
  (loop [art init-pattern
         i 0]
    (if (= i n)
      art
      (recur (enhance art rules) (inc i)))))


(defn count-on [art]
  (->> (map vec art)
       flatten
       sort
       (#(.indexOf % \.))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        (generate-art 5)
        count-on)))
;; 152


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (parse-input input)
        (generate-art 18)
        count-on)))
;; 1956174
