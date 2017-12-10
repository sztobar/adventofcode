(ns adventofcode2017.day9
  (:require [clojure.java.io :as io]))


(defn read-stream [input]
  (let [coll (vec input)
        len (count input)]
    (loop [pos 0
           group-level 0
           score 0
           state :group
           garbage 0]
      (if (= pos len)
        {:score score :garbage garbage}
        (let [char (get input pos)]
          (cond
            (= char \!) (recur (+ pos 2) group-level score state garbage)
            (and (= state :garbage)
                 (= char \>)) (recur (inc pos) group-level score :group garbage)
            (= state :garbage) (recur (inc pos) group-level score state (inc garbage))
            (= char \{) (recur (inc pos) (inc group-level) (+ score group-level 1) :group garbage)
            (= char \}) (recur (inc pos) (dec group-level) score :group garbage)
            (= char \<) (recur (inc pos) group-level score :garbage garbage)
            :else (recur (inc pos) group-level score state garbage)))))))


(defn get-score [input]
  (:score (read-stream input)))



(def filename "2017/day9.data")


(defn get-input [filename]
  (->> (io/resource filename)
      slurp))


(defn ex1
  ([]
   (ex1 filename))
  ([filename]
   (->> (get-input filename)
        get-score)))


;; PART TWO


(defn get-garbage-len [input]
  (:garbage (read-stream input)))


(defn ex2
  ([]
   (ex2 filename))
  ([filename]
   (->> (get-input filename)
        get-garbage-len)))
