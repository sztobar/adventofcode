(ns adventofcode2017.dat16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn spin [n coll]
  (str/join
   (concat (take-last n coll)
           (take (- (count coll) n) coll))))


(defn exchange [pos1 pos2 coll]
  (let [coll (vec coll)]
    (str/join
     (assoc coll
            pos1 (coll pos2)
            pos2 (coll pos1)))))


(defn partner [v1 v2 coll]
  (let [pos1 (.indexOf coll v1)
        pos2 (.indexOf coll v2)]
    (exchange pos1 pos2 coll)))


(defn get-input
  ([] (get-input "2017/day16.data"))
  ([filename] (slurp (io/resource filename))))


(defn parse-spin [param]
  {:action :spin
   :n (Integer/parseInt param)})


(defn parse-exchange [params]
  (let [[p1 p2] (str/split params #"/")]
    {:action :exchange
     :pos1 (Integer/parseInt p1)
     :pos2 (Integer/parseInt p2)}))


(defn parse-partner [params]
  (let [[v1 v2] (str/split params #"/")]
    {:action :partner
     :v1 v1
     :v2 v2}))


(defn parse-input [input]
  (map #(let [params (.substring % 1)]
          (case (get % 0)
            \s (parse-spin params)
            \x (parse-exchange params)
            \p (parse-partner params)))
       (str/split input #",")))


(defn apply-move [coll move]
  (case (:action move)
    :spin (spin (:n move) coll)
    :exchange (exchange (:pos1 move) (:pos2 move) coll)
    :partner (partner (:v1 move) (:v2 move) coll)))


(def init-programs "abcdefghijklmnop")


(defn apply-dance
  ([coll] (apply-dance coll init-programs))
  ([coll init]
   (reduce apply-move
           init
           coll)))


(defn ex1
  ([] (ex1 (get-input)))
  ([input]
   (->> (parse-input input)
        apply-dance)))
;; pkgnhomelfdibjac


;; PART TWO


;; after 60 iterations programs is same as init value
;; 40 is result of (mod bilion 60)
(defn apply-billion-dances [coll]
  (let [apply-dance-fn (partial apply-dance coll)]
    (loop [i 40
           programs init-programs]
      (if (= i 0)
        programs
        (recur (dec i) (apply-dance-fn programs))))))


(defn ex2
  ([] (ex2 (get-input)))
  ([input]
   (->> (parse-input input)
        apply-billion-dances)))
;; pogbjfihclkemadn
