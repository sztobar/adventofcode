(ns adventofcode2017.day22
  (:require [clojure.java.io :as io]))


(defn get-input
  ([] (get-input "2017/day22.data"))
  ([filename]
   (-> (io/resource filename)
       io/reader
       line-seq
       vec)))


(defn parse-input [coll]
  (let [size (count coll)
        center (int (/ size 2))]
    (->> (for [x (range size)
               y (range size)
               :when (= (get-in coll [y x]) \#)]
           [(- x center) (- y center)])
         set)))


(def dir-u [ 0 -1])
(def dir-r [ 1  0])
(def dir-d [ 0  1])
(def dir-l [-1  0])


(defn turn-left [{:keys [pos dir]}]
  (let [new-dir ({dir-u dir-l
                  dir-l dir-d
                  dir-d dir-r
                  dir-r dir-u} dir)]
    {:dir new-dir
     :pos (mapv + pos new-dir)}))


(defn turn-right [{:keys [pos dir]}]
  (let [new-dir ({dir-u dir-r
                  dir-r dir-d
                  dir-d dir-l
                  dir-l dir-u} dir)]
    {:dir new-dir
     :pos (mapv + pos new-dir)}))


(defn next-burst [{:keys [pos dir] :as v} coll]
  (if (contains? coll pos)
    [(turn-right v)
     (disj coll pos)
     0]
    [(turn-left v)
     (conj coll pos)
     1]))


(defn start-bursts [n coll]
  (loop [v {:pos [0 0] :dir [0 -1]}
         coll coll
         i 0
         infected 0]
    (if (= i n)
      infected
      (let [[v coll infected-flag] (next-burst v coll)]
        (recur v coll (inc i) (+ infected infected-flag))))))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        (start-bursts 10000))))
;; 5266


(defn convert-to-adv-map [coll]
  (reduce #(assoc %1 %2 :i)
          {}
          coll))


(defn turn-back [{:keys [pos dir]}]
  (let [new-dir (mapv #(* % -1) dir)]
    {:dir new-dir
     :pos (mapv + pos new-dir)}))

(defn go-forward [{:keys [pos dir]}]
  {:dir dir
   :pos (mapv + pos dir)})


(defn next-adv-burst [{:keys [pos dir] :as v} coll]
  (case (coll pos)
    :f [(turn-back v) (dissoc coll pos) 0]
    :i [(turn-right v) (assoc coll pos :f) 0]
    :w [(go-forward v) (assoc coll pos :i) 1]
    [(turn-left v) (assoc coll pos :w) 0]))


(defn start-adv-bursts [n coll]
  (loop [v {:pos [0 0] :dir [0 -1]}
         coll coll
         i 0
         infected 0]
    (if (= i n)
      infected
      (let [[v coll infected-flag] (next-adv-burst v coll)]
        (recur v coll (inc i) (+ infected infected-flag))))))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (parse-input input)
        (convert-to-adv-map)
        (start-adv-bursts 10000000))))
;; 2511895
